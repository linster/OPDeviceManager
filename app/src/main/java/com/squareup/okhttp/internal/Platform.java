package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import okio.k;

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    class Android extends Platform {
        private final OptionalMethod getAlpnSelectedProtocol;
        private final OptionalMethod setAlpnProtocols;
        private final OptionalMethod setHostname;
        private final OptionalMethod setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        public Android(OptionalMethod optionalMethod, OptionalMethod optionalMethod2, Method method, Method method2, OptionalMethod optionalMethod3, OptionalMethod optionalMethod4) {
            this.setUseSessionTickets = optionalMethod;
            this.setHostname = optionalMethod2;
            this.trafficStatsTagSocket = method;
            this.trafficStatsUntagSocket = method2;
            this.getAlpnSelectedProtocol = optionalMethod3;
            this.setAlpnProtocols = optionalMethod4;
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List list) {
            if (str != null) {
                this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sSLSocket, Boolean.valueOf(true));
                this.setHostname.invokeOptionalWithoutCheckedException(sSLSocket, str);
            }
            if (this.setAlpnProtocols != null && this.setAlpnProtocols.isSupported(sSLSocket)) {
                this.setAlpnProtocols.invokeWithoutCheckedException(sSLSocket, Platform.concatLengthPrefixed(list));
            }
        }

        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) {
            try {
                socket.connect(inetSocketAddress, i);
            } catch (Throwable e) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e);
                throw iOException;
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            if (this.getAlpnSelectedProtocol == null || !this.getAlpnSelectedProtocol.isSupported(sSLSocket)) {
                return null;
            }
            byte[] bArr = (byte[]) this.getAlpnSelectedProtocol.invokeWithoutCheckedException(sSLSocket, new Object[0]);
            return bArr == null ? null : new String(bArr, Util.UTF_8);
        }

        public void tagSocket(Socket socket) {
            if (this.trafficStatsTagSocket != null) {
                try {
                    this.trafficStatsTagSocket.invoke(null, new Object[]{socket});
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }

        public void untagSocket(Socket socket) {
            if (this.trafficStatsUntagSocket != null) {
                try {
                    this.trafficStatsUntagSocket.invoke(null, new Object[]{socket});
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }
    }

    class JdkWithJettyBootPlatform extends Platform {
        private final Class clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Method removeMethod;
        private final Class serverProviderClass;

        public JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class cls, Class cls2) {
            this.putMethod = method;
            this.getMethod = method2;
            this.removeMethod = method3;
            this.clientProviderClass = cls;
            this.serverProviderClass = cls2;
        }

        public void afterHandshake(SSLSocket sSLSocket) {
            try {
                this.removeMethod.invoke(null, new Object[]{sSLSocket});
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List list) {
            List arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Protocol protocol = (Protocol) list.get(i);
                if (protocol != Protocol.HTTP_1_0) {
                    arrayList.add(protocol.toString());
                }
            }
            try {
                Object newProxyInstance = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(arrayList));
                this.putMethod.invoke(null, new Object[]{sSLSocket, newProxyInstance});
            } catch (InvocationTargetException e) {
                throw new AssertionError(e);
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{sSLSocket}));
                if (!jettyNegoProvider.unsupported && jettyNegoProvider.selected == null) {
                    Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                    return null;
                }
                return !jettyNegoProvider.unsupported ? jettyNegoProvider.selected : null;
            } catch (InvocationTargetException e) {
                throw new AssertionError();
            }
        }
    }

    class JettyNegoProvider implements InvocationHandler {
        private final List protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List list) {
            this.protocols = list;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            String name = method.getName();
            Class returnType = method.getReturnType();
            if (objArr == null) {
                objArr = Util.EMPTY_STRING_ARRAY;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (name.equals("protocols") && objArr.length == 0) {
                return this.protocols;
            } else {
                if (name.equals("selectProtocol") || name.equals("select")) {
                    if (String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                        List list = (List) objArr[0];
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            if (this.protocols.contains(list.get(i))) {
                                name = (String) list.get(i);
                                this.selected = name;
                                return name;
                            }
                        }
                        name = (String) this.protocols.get(0);
                        this.selected = name;
                        return name;
                    }
                }
                if (name.equals("protocolSelected") || name.equals("selected")) {
                    if (objArr.length == 1) {
                        this.selected = (String) objArr[0];
                        return null;
                    }
                }
                return method.invoke(this, objArr);
            }
        }
    }

    static byte[] concatLengthPrefixed(List list) {
        k kVar = new k();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) list.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                kVar.Ad(protocol.toString().length());
                kVar.Ac(protocol.toString());
            }
        }
        return kVar.zU();
    }

    private static Platform findPlatform() {
        Method method;
        Method method2;
        OptionalMethod optionalMethod;
        OptionalMethod optionalMethod2;
        Method method3;
        OptionalMethod optionalMethod3;
        OptionalMethod optionalMethod4 = null;
        try {
            Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
        } catch (ClassNotFoundException e) {
            Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
        }
        try {
            OptionalMethod optionalMethod5 = new OptionalMethod(null, "setUseSessionTickets", Boolean.TYPE);
            OptionalMethod optionalMethod6 = new OptionalMethod(null, "setHostname", String.class);
            try {
                Class cls = Class.forName("android.net.TrafficStats");
                Method method4 = cls.getMethod("tagSocket", new Class[]{Socket.class});
                try {
                    method = cls.getMethod("untagSocket", new Class[]{Socket.class});
                    method2 = method;
                    optionalMethod = optionalMethod2;
                    OptionalMethod optionalMethod7 = optionalMethod4;
                    method3 = method4;
                    optionalMethod3 = optionalMethod7;
                } catch (ClassNotFoundException e2) {
                    optionalMethod2 = null;
                    method2 = null;
                    method = method4;
                    optionalMethod3 = null;
                    method3 = method;
                    optionalMethod = optionalMethod2;
                    return new Android(optionalMethod5, optionalMethod6, method3, method2, optionalMethod, optionalMethod3);
                }
                try {
                    Class.forName("android.net.Network");
                    optionalMethod2 = new OptionalMethod(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
                } catch (ClassNotFoundException e3) {
                    optionalMethod2 = null;
                } catch (NoSuchMethodException e4) {
                    optionalMethod2 = null;
                    method2 = method;
                    method = method4;
                    optionalMethod3 = null;
                    method3 = method;
                    optionalMethod = optionalMethod2;
                    return new Android(optionalMethod5, optionalMethod6, method3, method2, optionalMethod, optionalMethod3);
                }
                try {
                    optionalMethod4 = new OptionalMethod(null, "setAlpnProtocols", byte[].class);
                } catch (ClassNotFoundException e5) {
                } catch (NoSuchMethodException e6) {
                    method2 = method;
                    method = method4;
                    optionalMethod3 = null;
                    method3 = method;
                    optionalMethod = optionalMethod2;
                    return new Android(optionalMethod5, optionalMethod6, method3, method2, optionalMethod, optionalMethod3);
                }
            } catch (ClassNotFoundException e7) {
                optionalMethod2 = null;
                method2 = null;
                method = null;
                optionalMethod3 = null;
                method3 = method;
                optionalMethod = optionalMethod2;
                return new Android(optionalMethod5, optionalMethod6, method3, method2, optionalMethod, optionalMethod3);
            }
            return new Android(optionalMethod5, optionalMethod6, method3, method2, optionalMethod, optionalMethod3);
        } catch (ClassNotFoundException e8) {
            try {
                String str = "org.eclipse.jetty.alpn.ALPN";
                Class cls2 = Class.forName(str);
                Class cls3 = Class.forName(str + "$Provider");
                Class cls4 = Class.forName(str + "$ClientProvider");
                Class cls5 = Class.forName(str + "$ServerProvider");
                return new JdkWithJettyBootPlatform(cls2.getMethod("put", new Class[]{SSLSocket.class, cls3}), cls2.getMethod("get", new Class[]{SSLSocket.class}), cls2.getMethod("remove", new Class[]{SSLSocket.class}), cls4, cls5);
            } catch (ClassNotFoundException e9) {
                return new Platform();
            }
        }
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List list) {
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) {
        socket.connect(inetSocketAddress, i);
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public void tagSocket(Socket socket) {
    }

    public URI toUriLenient(URL url) {
        return url.toURI();
    }

    public void untagSocket(Socket socket) {
    }
}
