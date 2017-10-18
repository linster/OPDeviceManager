package retrofit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import retrofit.Profiler.RequestInformation;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class RestAdapter {
    static final String IDLE_THREAD_NAME = "Retrofit-Idle";
    static final String THREAD_PREFIX = "Retrofit-";
    final Executor callbackExecutor;
    private final Provider clientProvider;
    final Converter converter;
    final ErrorHandler errorHandler;
    final Executor httpExecutor;
    final Log log;
    volatile LogLevel logLevel;
    private final Profiler profiler;
    final RequestInterceptor requestInterceptor;
    private RxSupport rxSupport;
    final Endpoint server;
    private final Map serviceMethodInfoCache;

    public interface Log {
        public static final Log NONE = new Log() {
            public void log(String str) {
            }
        };

        void log(String str);
    }

    public class Builder {
        private Executor callbackExecutor;
        private Provider clientProvider;
        private Converter converter;
        private Endpoint endpoint;
        private ErrorHandler errorHandler;
        private Executor httpExecutor;
        private Log log;
        private LogLevel logLevel = LogLevel.NONE;
        private Profiler profiler;
        private RequestInterceptor requestInterceptor;

        private void ensureSaneDefaults() {
            if (this.converter == null) {
                this.converter = Platform.get().defaultConverter();
            }
            if (this.clientProvider == null) {
                this.clientProvider = Platform.get().defaultClient();
            }
            if (this.httpExecutor == null) {
                this.httpExecutor = Platform.get().defaultHttpExecutor();
            }
            if (this.callbackExecutor == null) {
                this.callbackExecutor = Platform.get().defaultCallbackExecutor();
            }
            if (this.errorHandler == null) {
                this.errorHandler = ErrorHandler.DEFAULT;
            }
            if (this.log == null) {
                this.log = Platform.get().defaultLog();
            }
            if (this.requestInterceptor == null) {
                this.requestInterceptor = RequestInterceptor.NONE;
            }
        }

        public RestAdapter build() {
            if (this.endpoint != null) {
                ensureSaneDefaults();
                return new RestAdapter(this.endpoint, this.clientProvider, this.httpExecutor, this.callbackExecutor, this.requestInterceptor, this.converter, this.profiler, this.errorHandler, this.log, this.logLevel);
            }
            throw new IllegalArgumentException("Endpoint may not be null.");
        }

        public Builder setClient(Provider provider) {
            if (provider != null) {
                this.clientProvider = provider;
                return this;
            }
            throw new NullPointerException("Client provider may not be null.");
        }

        public Builder setClient(final Client client) {
            if (client != null) {
                return setClient(new Provider() {
                    public Client get() {
                        return client;
                    }
                });
            }
            throw new NullPointerException("Client may not be null.");
        }

        public Builder setConverter(Converter converter) {
            if (converter != null) {
                this.converter = converter;
                return this;
            }
            throw new NullPointerException("Converter may not be null.");
        }

        public Builder setEndpoint(String str) {
            if (str == null || str.trim().length() == 0) {
                throw new NullPointerException("Endpoint may not be blank.");
            }
            this.endpoint = Endpoints.newFixedEndpoint(str);
            return this;
        }

        public Builder setEndpoint(Endpoint endpoint) {
            if (endpoint != null) {
                this.endpoint = endpoint;
                return this;
            }
            throw new NullPointerException("Endpoint may not be null.");
        }

        public Builder setErrorHandler(ErrorHandler errorHandler) {
            if (errorHandler != null) {
                this.errorHandler = errorHandler;
                return this;
            }
            throw new NullPointerException("Error handler may not be null.");
        }

        public Builder setExecutors(Executor executor, Executor executor2) {
            if (executor != null) {
                if (executor2 == null) {
                    executor2 = new SynchronousExecutor();
                }
                this.httpExecutor = executor;
                this.callbackExecutor = executor2;
                return this;
            }
            throw new NullPointerException("HTTP executor may not be null.");
        }

        public Builder setLog(Log log) {
            if (log != null) {
                this.log = log;
                return this;
            }
            throw new NullPointerException("Log may not be null.");
        }

        public Builder setLogLevel(LogLevel logLevel) {
            if (logLevel != null) {
                this.logLevel = logLevel;
                return this;
            }
            throw new NullPointerException("Log level may not be null.");
        }

        public Builder setProfiler(Profiler profiler) {
            if (profiler != null) {
                this.profiler = profiler;
                return this;
            }
            throw new NullPointerException("Profiler may not be null.");
        }

        public Builder setRequestInterceptor(RequestInterceptor requestInterceptor) {
            if (requestInterceptor != null) {
                this.requestInterceptor = requestInterceptor;
                return this;
            }
            throw new NullPointerException("Request interceptor may not be null.");
        }
    }

    public enum LogLevel {
        NONE,
        BASIC,
        HEADERS,
        HEADERS_AND_ARGS,
        FULL;

        public boolean log() {
            return this != NONE;
        }
    }

    class RestHandler implements InvocationHandler {
        private final Map methodDetailsCache;

        RestHandler(Map map) {
            this.methodDetailsCache = map;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.lang.Object invokeRequest(retrofit.RequestInterceptor r13, retrofit.RestMethodInfo r14, java.lang.Object[] r15) {
            /*
            r12 = this;
            r7 = 0;
            r14.init();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0 = r0.server;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r9 = r0.getUrl();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0 = new retrofit.RequestBuilder;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r1 = r1.converter;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0.<init>(r9, r14, r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0.setArguments(r15);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r13.intercept(r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r0 = r0.build();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r6 = r0.getUrl();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x01f3, Throwable -> 0x01ef }
            r1 = r14.isSynchronous;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r1 == 0) goto L_0x008a;
        L_0x0027:
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.logLevel;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.log();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r1 != 0) goto L_0x00d3;
        L_0x0031:
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.profiler;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r1 != 0) goto L_0x00de;
        L_0x0039:
            r5 = r7;
        L_0x003a:
            r2 = java.lang.System.nanoTime();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.clientProvider;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.get();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r8 = r1.execute(r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r10 = java.lang.System.nanoTime();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r2 = r10 - r2;
            r2 = r1.toMillis(r2);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r4 = r8.getStatus();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.profiler;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r1 != 0) goto L_0x00ea;
        L_0x0064:
            r0 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = r0.logLevel;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = r0.log();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r0 != 0) goto L_0x0109;
        L_0x006e:
            r1 = r8;
        L_0x006f:
            r2 = r14.responseObjectType;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r4 >= r0) goto L_0x0111;
        L_0x0075:
            r0 = retrofit.Utils.readBodyToBytesIfNecessary(r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.converter;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = retrofit.RetrofitError.httpError(r6, r0, r1, r2);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            throw r0;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
        L_0x0082:
            r0 = move-exception;
            throw r0;	 Catch:{ all -> 0x0084 }
        L_0x0084:
            r0 = move-exception;
            r1 = r14.isSynchronous;
            if (r1 == 0) goto L_0x01e3;
        L_0x0089:
            throw r0;
        L_0x008a:
            r1 = "?";
            r2 = r9.length();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r6.indexOf(r1, r2);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r2 = -1;
            if (r1 == r2) goto L_0x00ce;
        L_0x0098:
            r2 = java.lang.Thread.currentThread();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r3 = new java.lang.StringBuilder;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r3.<init>();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r4 = "Retrofit-";
            r3 = r3.append(r4);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r4 = r9.length();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r6.substring(r4, r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r3.append(r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.toString();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r2.setName(r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x0027;
        L_0x00bd:
            r0 = move-exception;
            r1 = r6;
        L_0x00bf:
            r2 = retrofit.RestAdapter.this;	 Catch:{ all -> 0x0084 }
            r2 = r2.logLevel;	 Catch:{ all -> 0x0084 }
            r2 = r2.log();	 Catch:{ all -> 0x0084 }
            if (r2 != 0) goto L_0x01d5;
        L_0x00c9:
            r0 = retrofit.RetrofitError.networkError(r1, r0);	 Catch:{ all -> 0x0084 }
            throw r0;	 Catch:{ all -> 0x0084 }
        L_0x00ce:
            r1 = r6.length();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x0098;
        L_0x00d3:
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r2 = "HTTP";
            r0 = r1.logAndReplaceRequest(r2, r0, r15);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x0031;
        L_0x00de:
            r1 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r1.profiler;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r5 = r1.beforeCall();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x003a;
        L_0x00ea:
            r1 = retrofit.RestAdapter.getRequestInfo(r9, r14, r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = r0.profiler;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0.afterCall(r1, r2, r4, r5);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x0064;
        L_0x00f9:
            r0 = move-exception;
        L_0x00fa:
            r1 = retrofit.RestAdapter.this;	 Catch:{ all -> 0x0084 }
            r1 = r1.logLevel;	 Catch:{ all -> 0x0084 }
            r1 = r1.log();	 Catch:{ all -> 0x0084 }
            if (r1 != 0) goto L_0x01dc;
        L_0x0104:
            r0 = retrofit.RetrofitError.unexpectedError(r6, r0);	 Catch:{ all -> 0x0084 }
            throw r0;	 Catch:{ all -> 0x0084 }
        L_0x0109:
            r0 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r0.logAndReplaceResponse(r6, r8, r2);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x006f;
        L_0x0111:
            r0 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
            if (r4 >= r0) goto L_0x0075;
        L_0x0115:
            r0 = retrofit.client.Response.class;
            r0 = r2.equals(r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r0 != 0) goto L_0x0143;
        L_0x011d:
            r0 = r1.getBody();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r0 == 0) goto L_0x0176;
        L_0x0123:
            r3 = new retrofit.ExceptionCatchingTypedInput;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r3.<init>(r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r4 = retrofit.RestAdapter.this;	 Catch:{ ConversionException -> 0x01bb }
            r4 = r4.converter;	 Catch:{ ConversionException -> 0x01bb }
            r4 = r4.fromBody(r3, r2);	 Catch:{ ConversionException -> 0x01bb }
            r5 = retrofit.RestAdapter.this;	 Catch:{ ConversionException -> 0x01bb }
            r5.logResponseBody(r0, r4);	 Catch:{ ConversionException -> 0x01bb }
            r0 = r14.isSynchronous;	 Catch:{ ConversionException -> 0x01bb }
            if (r0 != 0) goto L_0x01a0;
        L_0x0139:
            r0 = new retrofit.ResponseWrapper;	 Catch:{ ConversionException -> 0x01bb }
            r0.<init>(r1, r4);	 Catch:{ ConversionException -> 0x01bb }
            r1 = r14.isSynchronous;
            if (r1 == 0) goto L_0x01b0;
        L_0x0142:
            return r0;
        L_0x0143:
            r0 = r14.isStreaming;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r0 == 0) goto L_0x0156;
        L_0x0147:
            r0 = r1;
        L_0x0148:
            r1 = r14.isSynchronous;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r1 != 0) goto L_0x015b;
        L_0x014c:
            r1 = new retrofit.ResponseWrapper;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1.<init>(r0, r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = r14.isSynchronous;
            if (r0 == 0) goto L_0x016b;
        L_0x0155:
            return r1;
        L_0x0156:
            r0 = retrofit.Utils.readBodyToBytesIfNecessary(r1);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            goto L_0x0148;
        L_0x015b:
            r1 = r14.isSynchronous;
            if (r1 == 0) goto L_0x0160;
        L_0x015f:
            return r0;
        L_0x0160:
            r1 = java.lang.Thread.currentThread();
            r2 = "Retrofit-Idle";
            r1.setName(r2);
            goto L_0x015f;
        L_0x016b:
            r0 = java.lang.Thread.currentThread();
            r2 = "Retrofit-Idle";
            r0.setName(r2);
            goto L_0x0155;
        L_0x0176:
            r0 = r14.isSynchronous;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r0 != 0) goto L_0x0185;
        L_0x017a:
            r0 = new retrofit.ResponseWrapper;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r2 = 0;
            r0.<init>(r1, r2);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r1 = r14.isSynchronous;
            if (r1 == 0) goto L_0x0195;
        L_0x0184:
            return r0;
        L_0x0185:
            r0 = r14.isSynchronous;
            if (r0 == 0) goto L_0x018a;
        L_0x0189:
            return r7;
        L_0x018a:
            r0 = java.lang.Thread.currentThread();
            r1 = "Retrofit-Idle";
            r0.setName(r1);
            goto L_0x0189;
        L_0x0195:
            r1 = java.lang.Thread.currentThread();
            r2 = "Retrofit-Idle";
            r1.setName(r2);
            goto L_0x0184;
        L_0x01a0:
            r0 = r14.isSynchronous;
            if (r0 == 0) goto L_0x01a5;
        L_0x01a4:
            return r4;
        L_0x01a5:
            r0 = java.lang.Thread.currentThread();
            r1 = "Retrofit-Idle";
            r0.setName(r1);
            goto L_0x01a4;
        L_0x01b0:
            r1 = java.lang.Thread.currentThread();
            r2 = "Retrofit-Idle";
            r1.setName(r2);
            goto L_0x0142;
        L_0x01bb:
            r0 = move-exception;
            r4 = r3.threwException();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            if (r4 != 0) goto L_0x01d0;
        L_0x01c2:
            r3 = 0;
            r1 = retrofit.Utils.replaceResponseBody(r1, r3);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r3 = retrofit.RestAdapter.this;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r3 = r3.converter;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            r0 = retrofit.RetrofitError.conversionError(r6, r1, r3, r2, r0);	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            throw r0;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
        L_0x01d0:
            r0 = r3.getThrownException();	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
            throw r0;	 Catch:{ RetrofitError -> 0x0082, IOException -> 0x00bd, Throwable -> 0x00f9 }
        L_0x01d5:
            r2 = retrofit.RestAdapter.this;	 Catch:{ all -> 0x0084 }
            r2.logException(r0, r1);	 Catch:{ all -> 0x0084 }
            goto L_0x00c9;
        L_0x01dc:
            r1 = retrofit.RestAdapter.this;	 Catch:{ all -> 0x0084 }
            r1.logException(r0, r6);	 Catch:{ all -> 0x0084 }
            goto L_0x0104;
        L_0x01e3:
            r1 = java.lang.Thread.currentThread();
            r2 = "Retrofit-Idle";
            r1.setName(r2);
            goto L_0x0089;
        L_0x01ef:
            r0 = move-exception;
            r6 = r7;
            goto L_0x00fa;
        L_0x01f3:
            r0 = move-exception;
            r1 = r7;
            goto L_0x00bf;
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit.RestAdapter.RestHandler.invokeRequest(retrofit.RequestInterceptor, retrofit.RestMethodInfo, java.lang.Object[]):java.lang.Object");
        }

        public Object invoke(Object obj, Method method, final Object[] objArr) {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, objArr);
            }
            final RestMethodInfo methodInfo = RestAdapter.getMethodInfo(this.methodDetailsCache, method);
            if (methodInfo.isSynchronous) {
                try {
                    return invokeRequest(RestAdapter.this.requestInterceptor, methodInfo, objArr);
                } catch (Throwable e) {
                    Throwable handleError = RestAdapter.this.errorHandler.handleError(e);
                    if (handleError != null) {
                        throw handleError;
                    }
                    throw new IllegalStateException("Error handler returned null for wrapped exception.", e);
                }
            } else if (RestAdapter.this.httpExecutor == null || RestAdapter.this.callbackExecutor == null) {
                throw new IllegalStateException("Asynchronous invocation requires calling setExecutors.");
            } else if (methodInfo.isObservable) {
                if (RestAdapter.this.rxSupport == null) {
                    if (Platform.HAS_RX_JAVA) {
                        RestAdapter.this.rxSupport = new RxSupport(RestAdapter.this.httpExecutor, RestAdapter.this.errorHandler, RestAdapter.this.requestInterceptor);
                    } else {
                        throw new IllegalStateException("Observable method found but no RxJava on classpath.");
                    }
                }
                return RestAdapter.this.rxSupport.createRequestObservable(new Invoker() {
                    public ResponseWrapper invoke(RequestInterceptor requestInterceptor) {
                        return (ResponseWrapper) RestHandler.this.invokeRequest(requestInterceptor, methodInfo, objArr);
                    }
                });
            } else {
                final Object requestInterceptorTape = new RequestInterceptorTape();
                RestAdapter.this.requestInterceptor.intercept(requestInterceptorTape);
                Callback callback = (Callback) objArr[objArr.length - 1];
                final Object[] objArr2 = objArr;
                RestAdapter.this.httpExecutor.execute(new CallbackRunnable(callback, RestAdapter.this.callbackExecutor, RestAdapter.this.errorHandler) {
                    public ResponseWrapper obtainResponse() {
                        return (ResponseWrapper) RestHandler.this.invokeRequest(requestInterceptorTape, methodInfo, objArr2);
                    }
                });
                return null;
            }
        }
    }

    private RestAdapter(Endpoint endpoint, Provider provider, Executor executor, Executor executor2, RequestInterceptor requestInterceptor, Converter converter, Profiler profiler, ErrorHandler errorHandler, Log log, LogLevel logLevel) {
        this.serviceMethodInfoCache = new LinkedHashMap();
        this.server = endpoint;
        this.clientProvider = provider;
        this.httpExecutor = executor;
        this.callbackExecutor = executor2;
        this.requestInterceptor = requestInterceptor;
        this.converter = converter;
        this.profiler = profiler;
        this.errorHandler = errorHandler;
        this.log = log;
        this.logLevel = logLevel;
    }

    static RestMethodInfo getMethodInfo(Map map, Method method) {
        RestMethodInfo restMethodInfo;
        synchronized (map) {
            restMethodInfo = (RestMethodInfo) map.get(method);
            if (restMethodInfo == null) {
                restMethodInfo = new RestMethodInfo(method);
                map.put(method, restMethodInfo);
            }
        }
        return restMethodInfo;
    }

    private static RequestInformation getRequestInfo(String str, RestMethodInfo restMethodInfo, Request request) {
        String str2 = null;
        long j = 0;
        TypedOutput body = request.getBody();
        if (body != null) {
            j = body.length();
            str2 = body.mimeType();
        }
        return new RequestInformation(restMethodInfo.requestMethod, str, restMethodInfo.requestUrl, j, str2);
    }

    private Response logAndReplaceResponse(String str, Response response, long j) {
        this.log.log(String.format("<--- HTTP %s %s (%sms)", new Object[]{Integer.valueOf(response.getStatus()), str, Long.valueOf(j)}));
        if (this.logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
            long j2;
            for (Header header : response.getHeaders()) {
                this.log.log(header.toString());
            }
            TypedInput body = response.getBody();
            if (body == null) {
                j2 = 0;
            } else {
                long length = body.length();
                if (this.logLevel.ordinal() < LogLevel.FULL.ordinal()) {
                    j2 = length;
                } else {
                    if (!response.getHeaders().isEmpty()) {
                        this.log.log("");
                    }
                    if (!(body instanceof TypedByteArray)) {
                        response = Utils.readBodyToBytesIfNecessary(response);
                        body = response.getBody();
                    }
                    byte[] bytes = ((TypedByteArray) body).getBytes();
                    length = (long) bytes.length;
                    this.log.log(new String(bytes, MimeUtil.parseCharset(body.mimeType(), "UTF-8")));
                    j2 = length;
                }
            }
            this.log.log(String.format("<--- END HTTP (%s-byte body)", new Object[]{Long.valueOf(j2)}));
        }
        return response;
    }

    private void logResponseBody(TypedInput typedInput, Object obj) {
        if (this.logLevel.ordinal() == LogLevel.HEADERS_AND_ARGS.ordinal()) {
            this.log.log("<--- BODY:");
            this.log.log(obj.toString());
        }
    }

    public Object create(Class cls) {
        Utils.validateServiceClass(cls);
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new RestHandler(getMethodInfoCache(cls)));
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    Map getMethodInfoCache(Class cls) {
        Map map;
        synchronized (this.serviceMethodInfoCache) {
            map = (Map) this.serviceMethodInfoCache.get(cls);
            if (map == null) {
                map = new LinkedHashMap();
                this.serviceMethodInfoCache.put(cls, map);
            }
        }
        return map;
    }

    Request logAndReplaceRequest(String str, Request request, Object[] objArr) {
        this.log.log(String.format("---> %s %s %s", new Object[]{str, request.getMethod(), request.getUrl()}));
        if (this.logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
            for (Header header : request.getHeaders()) {
                this.log.log(header.toString());
            }
            String str2 = "no";
            TypedOutput body = request.getBody();
            if (body != null) {
                str2 = body.mimeType();
                if (str2 != null) {
                    this.log.log("Content-Type: " + str2);
                }
                long length = body.length();
                String str3 = length + "-byte";
                if (length != -1) {
                    this.log.log("Content-Length: " + length);
                }
                if (this.logLevel.ordinal() >= LogLevel.FULL.ordinal()) {
                    if (!request.getHeaders().isEmpty()) {
                        this.log.log("");
                    }
                    if (!(body instanceof TypedByteArray)) {
                        request = Utils.readBodyToBytesIfNecessary(request);
                        body = request.getBody();
                    }
                    this.log.log(new String(((TypedByteArray) body).getBytes(), MimeUtil.parseCharset(body.mimeType(), "UTF-8")));
                } else if (this.logLevel.ordinal() >= LogLevel.HEADERS_AND_ARGS.ordinal()) {
                    if (!request.getHeaders().isEmpty()) {
                        this.log.log("---> REQUEST:");
                    }
                    for (int i = 0; i < objArr.length; i++) {
                        this.log.log("#" + i + ": " + objArr[i]);
                    }
                    str2 = str3;
                }
                str2 = str3;
            }
            this.log.log(String.format("---> END %s (%s body)", new Object[]{str, str2}));
        }
        return request;
    }

    void logException(Throwable th, String str) {
        Log log = this.log;
        String str2 = "---- ERROR %s";
        Object[] objArr = new Object[1];
        if (str == null) {
            str = "";
        }
        objArr[0] = str;
        log.log(String.format(str2, objArr));
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        this.log.log(stringWriter.toString());
        this.log.log("---- END ERROR");
    }

    public void setLogLevel(LogLevel logLevel) {
        if (this.logLevel != null) {
            this.logLevel = logLevel;
            return;
        }
        throw new NullPointerException("Log level may not be null.");
    }
}
