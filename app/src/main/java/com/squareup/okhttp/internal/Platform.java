package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform {
    private static final Platform PLATFORM;

    private static class Android extends Platform {
        private final OptionalMethod<Socket> getAlpnSelectedProtocol;
        private final OptionalMethod<Socket> setAlpnProtocols;
        private final OptionalMethod<Socket> setHostname;
        private final OptionalMethod<Socket> setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        public Android(OptionalMethod<Socket> setUseSessionTickets, OptionalMethod<Socket> setHostname, Method trafficStatsTagSocket, Method trafficStatsUntagSocket, OptionalMethod<Socket> getAlpnSelectedProtocol, OptionalMethod<Socket> setAlpnProtocols) {
            this.setUseSessionTickets = setUseSessionTickets;
            this.setHostname = setHostname;
            this.trafficStatsTagSocket = trafficStatsTagSocket;
            this.trafficStatsUntagSocket = trafficStatsUntagSocket;
            this.getAlpnSelectedProtocol = getAlpnSelectedProtocol;
            this.setAlpnProtocols = setAlpnProtocols;
        }

        public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
            try {
                socket.connect(address, connectTimeout);
            } catch (SecurityException se) {
                IOException ioException = new IOException("Exception in connect");
                ioException.initCause(se);
                throw ioException;
            }
        }

        public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
            if (hostname != null) {
                this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sslSocket, Boolean.valueOf(true));
                this.setHostname.invokeOptionalWithoutCheckedException(sslSocket, hostname);
            }
            if (this.setAlpnProtocols != null && this.setAlpnProtocols.isSupported(sslSocket)) {
                this.setAlpnProtocols.invokeWithoutCheckedException(sslSocket, Platform.concatLengthPrefixed(protocols));
            }
        }

        public String getSelectedProtocol(SSLSocket socket) {
            if (this.getAlpnSelectedProtocol == null || !this.getAlpnSelectedProtocol.isSupported(socket)) {
                return null;
            }
            byte[] alpnResult = (byte[]) this.getAlpnSelectedProtocol.invokeWithoutCheckedException(socket, new Object[0]);
            return alpnResult == null ? null : new String(alpnResult, Util.UTF_8);
        }

        public void tagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsTagSocket != null) {
                try {
                    this.trafficStatsTagSocket.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }

        public void untagSocket(Socket socket) throws SocketException {
            if (this.trafficStatsUntagSocket != null) {
                try {
                    this.trafficStatsUntagSocket.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }
    }

    private static class JdkWithJettyBootPlatform extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Method removeMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Method removeMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
            this.putMethod = putMethod;
            this.getMethod = getMethod;
            this.removeMethod = removeMethod;
            this.clientProviderClass = clientProviderClass;
            this.serverProviderClass = serverProviderClass;
        }

        public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
            List<String> names = new ArrayList(protocols.size());
            int size = protocols.size();
            for (int i = 0; i < size; i++) {
                Protocol protocol = (Protocol) protocols.get(i);
                if (protocol != Protocol.HTTP_1_0) {
                    names.add(protocol.toString());
                }
            }
            try {
                Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
                this.putMethod.invoke(null, new Object[]{sslSocket, provider});
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(e);
            }
        }

        public void afterHandshake(SSLSocket sslSocket) {
            try {
                this.removeMethod.invoke(null, new Object[]{sslSocket});
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }

        public String getSelectedProtocol(SSLSocket socket) {
            String str = null;
            try {
                JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{socket}));
                if (!provider.unsupported && provider.selected == null) {
                    Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                    return null;
                }
                if (!provider.unsupported) {
                    str = provider.selected;
                }
                return str;
            } catch (InvocationTargetException e) {
                throw new AssertionError();
            }
        }
    }

    private static class JettyNegoProvider implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> protocols) {
            this.protocols = protocols;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (methodName.equals("protocols") && args.length == 0) {
                return this.protocols;
            } else {
                if (methodName.equals("selectProtocol") || methodName.equals("select")) {
                    if (String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                        String str;
                        List<String> peerProtocols = args[0];
                        int size = peerProtocols.size();
                        for (int i = 0; i < size; i++) {
                            if (this.protocols.contains(peerProtocols.get(i))) {
                                str = (String) peerProtocols.get(i);
                                this.selected = str;
                                return str;
                            }
                        }
                        str = (String) this.protocols.get(0);
                        this.selected = str;
                        return str;
                    }
                }
                if (methodName.equals("protocolSelected") || methodName.equals("selected")) {
                    if (args.length == 1) {
                        this.selected = (String) args[0];
                        return null;
                    }
                }
                return method.invoke(this, args);
            }
        }
    }

    static {
        PLATFORM = findPlatform();
    }

    public static Platform get() {
        return PLATFORM;
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public void logW(String warning) {
        System.out.println(warning);
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    public URI toUriLenient(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> list) {
    }

    public void afterHandshake(SSLSocket sslSocket) {
    }

    public String getSelectedProtocol(SSLSocket socket) {
        return null;
    }

    public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
        socket.connect(address, connectTimeout);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.squareup.okhttp.internal.Platform findPlatform() {
        /*
        r2 = "com.android.org.conscrypt.OpenSSLSocketImpl";
        java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x009c }
    L_0x0006:
        r3 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r2 = "setUseSessionTickets";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r24 = 0;
        r25 = java.lang.Boolean.TYPE;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r24 = 0;
        r0 = r24;
        r3.<init>(r0, r2, r9);	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r4 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r2 = "setHostname";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r24 = 0;
        r25 = java.lang.String.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r24 = 0;
        r0 = r24;
        r4.<init>(r0, r2, r9);	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r2 = "android.net.TrafficStats";
        r23 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r2 = "tagSocket";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r24 = 0;
        r25 = java.net.Socket.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r0 = r23;
        r5 = r0.getMethod(r2, r9);	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r2 = "untagSocket";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r24 = 0;
        r25 = java.net.Socket.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r0 = r23;
        r6 = r0.getMethod(r2, r9);	 Catch:{ ClassNotFoundException -> 0x013b, ClassNotFoundException -> 0x013b }
        r2 = "android.net.Network";
        java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x0149 }
        r16 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x0149 }
        r2 = byte[].class;
        r9 = "getAlpnSelectedProtocol";
        r24 = 0;
        r0 = r24;
        r0 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x0149 }
        r24 = r0;
        r0 = r16;
        r1 = r24;
        r0.<init>(r2, r9, r1);	 Catch:{ ClassNotFoundException -> 0x0149 }
        r22 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x014c, NoSuchMethodException -> 0x0145 }
        r2 = "setAlpnProtocols";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x014c, NoSuchMethodException -> 0x0145 }
        r24 = 0;
        r25 = byte[].class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x014c, NoSuchMethodException -> 0x0145 }
        r24 = 0;
        r0 = r22;
        r1 = r24;
        r0.<init>(r1, r2, r9);	 Catch:{ ClassNotFoundException -> 0x014c, NoSuchMethodException -> 0x0145 }
        r8 = r22;
        r7 = r16;
    L_0x0096:
        r2 = new com.squareup.okhttp.internal.Platform$Android;	 Catch:{ ClassNotFoundException -> 0x00a5 }
        r2.<init>(r3, r4, r5, r6, r7, r8);	 Catch:{ ClassNotFoundException -> 0x00a5 }
        return r2;
    L_0x009c:
        r15 = move-exception;
        r2 = "org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl";
        java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x00a5 }
        goto L_0x0006;
    L_0x00a5:
        r17 = move-exception;
        r20 = "org.eclipse.jetty.alpn.ALPN";
        r19 = java.lang.Class.forName(r20);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r20;
        r2 = r2.append(r0);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r9 = "$Provider";
        r2 = r2.append(r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r21 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r20;
        r2 = r2.append(r0);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r9 = "$ClientProvider";
        r2 = r2.append(r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r13 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2.<init>();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r20;
        r2 = r2.append(r0);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r9 = "$ServerProvider";
        r2 = r2.append(r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = r2.toString();	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r14 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = "put";
        r9 = 2;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r24 = 0;
        r25 = javax.net.ssl.SSLSocket.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r24 = 1;
        r9[r24] = r21;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r19;
        r10 = r0.getMethod(r2, r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = "get";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r24 = 0;
        r25 = javax.net.ssl.SSLSocket.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r19;
        r11 = r0.getMethod(r2, r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r2 = "remove";
        r9 = 1;
        r9 = new java.lang.Class[r9];	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r24 = 0;
        r25 = javax.net.ssl.SSLSocket.class;
        r9[r24] = r25;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r0 = r19;
        r12 = r0.getMethod(r2, r9);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r9 = new com.squareup.okhttp.internal.Platform$JdkWithJettyBootPlatform;	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        r9.<init>(r10, r11, r12, r13, r14);	 Catch:{ ClassNotFoundException -> 0x013e, ClassNotFoundException -> 0x013e }
        return r9;
    L_0x013b:
        r18 = move-exception;
    L_0x013c:
        goto L_0x0096;
    L_0x013e:
        r18 = move-exception;
        r2 = new com.squareup.okhttp.internal.Platform;
        r2.<init>();
        return r2;
    L_0x0145:
        r18 = move-exception;
        r7 = r16;
        goto L_0x013c;
    L_0x0149:
        r2 = move-exception;
        goto L_0x0096;
    L_0x014c:
        r2 = move-exception;
        r7 = r16;
        goto L_0x0096;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Platform.findPlatform():com.squareup.okhttp.internal.Platform");
    }

    static byte[] concatLengthPrefixed(List<Protocol> protocols) {
        Buffer result = new Buffer();
        int size = protocols.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) protocols.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                result.writeByte(protocol.toString().length());
                result.writeUtf8(protocol.toString());
            }
        }
        return result.readByteArray();
    }
}
