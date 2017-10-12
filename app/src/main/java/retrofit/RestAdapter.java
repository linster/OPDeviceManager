package retrofit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import retrofit.Profiler.RequestInformation;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
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
    private final Map<Class<?>, Map<Method, RestMethodInfo>> serviceMethodInfoCache;

    public interface Log {
        public static final Log NONE;

        void log(String str);

        static {
            NONE = new Log() {
                public void log(String message) {
                }
            };
        }
    }

    public static class Builder {
        private Executor callbackExecutor;
        private Provider clientProvider;
        private Converter converter;
        private Endpoint endpoint;
        private ErrorHandler errorHandler;
        private Executor httpExecutor;
        private Log log;
        private LogLevel logLevel;
        private Profiler profiler;
        private RequestInterceptor requestInterceptor;

        /* renamed from: retrofit.RestAdapter.Builder.1 */
        class AnonymousClass1 implements Provider {
            final /* synthetic */ Client val$client;

            AnonymousClass1(Client client) {
                this.val$client = client;
            }

            public Client get() {
                return this.val$client;
            }
        }

        public Builder() {
            this.logLevel = LogLevel.NONE;
        }

        public Builder setEndpoint(String endpoint) {
            if (endpoint == null || endpoint.trim().length() == 0) {
                throw new NullPointerException("Endpoint may not be blank.");
            }
            this.endpoint = Endpoints.newFixedEndpoint(endpoint);
            return this;
        }

        public Builder setEndpoint(Endpoint endpoint) {
            if (endpoint != null) {
                this.endpoint = endpoint;
                return this;
            }
            throw new NullPointerException("Endpoint may not be null.");
        }

        public Builder setClient(Client client) {
            if (client != null) {
                return setClient(new AnonymousClass1(client));
            }
            throw new NullPointerException("Client may not be null.");
        }

        public Builder setClient(Provider clientProvider) {
            if (clientProvider != null) {
                this.clientProvider = clientProvider;
                return this;
            }
            throw new NullPointerException("Client provider may not be null.");
        }

        public Builder setExecutors(Executor httpExecutor, Executor callbackExecutor) {
            if (httpExecutor != null) {
                if (callbackExecutor == null) {
                    callbackExecutor = new SynchronousExecutor();
                }
                this.httpExecutor = httpExecutor;
                this.callbackExecutor = callbackExecutor;
                return this;
            }
            throw new NullPointerException("HTTP executor may not be null.");
        }

        public Builder setRequestInterceptor(RequestInterceptor requestInterceptor) {
            if (requestInterceptor != null) {
                this.requestInterceptor = requestInterceptor;
                return this;
            }
            throw new NullPointerException("Request interceptor may not be null.");
        }

        public Builder setConverter(Converter converter) {
            if (converter != null) {
                this.converter = converter;
                return this;
            }
            throw new NullPointerException("Converter may not be null.");
        }

        public Builder setProfiler(Profiler profiler) {
            if (profiler != null) {
                this.profiler = profiler;
                return this;
            }
            throw new NullPointerException("Profiler may not be null.");
        }

        public Builder setErrorHandler(ErrorHandler errorHandler) {
            if (errorHandler != null) {
                this.errorHandler = errorHandler;
                return this;
            }
            throw new NullPointerException("Error handler may not be null.");
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

        public RestAdapter build() {
            if (this.endpoint != null) {
                ensureSaneDefaults();
                return new RestAdapter(this.clientProvider, this.httpExecutor, this.callbackExecutor, this.requestInterceptor, this.converter, this.profiler, this.errorHandler, this.log, this.logLevel, null);
            }
            throw new IllegalArgumentException("Endpoint may not be null.");
        }

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

    private class RestHandler implements InvocationHandler {
        private final Map<Method, RestMethodInfo> methodDetailsCache;

        /* renamed from: retrofit.RestAdapter.RestHandler.1 */
        class AnonymousClass1 implements Invoker {
            final /* synthetic */ Object[] val$args;
            final /* synthetic */ RestMethodInfo val$methodInfo;

            AnonymousClass1(RestMethodInfo restMethodInfo, Object[] objArr) {
                this.val$methodInfo = restMethodInfo;
                this.val$args = objArr;
            }

            public ResponseWrapper invoke(RequestInterceptor requestInterceptor) {
                return (ResponseWrapper) RestHandler.this.invokeRequest(requestInterceptor, this.val$methodInfo, this.val$args);
            }
        }

        /* renamed from: retrofit.RestAdapter.RestHandler.2 */
        class AnonymousClass2 extends CallbackRunnable {
            final /* synthetic */ Object[] val$args;
            final /* synthetic */ RequestInterceptorTape val$interceptorTape;
            final /* synthetic */ RestMethodInfo val$methodInfo;

            AnonymousClass2(Callback callback, Executor callbackExecutor, ErrorHandler errorHandler, RequestInterceptorTape requestInterceptorTape, RestMethodInfo restMethodInfo, Object[] objArr) {
                this.val$interceptorTape = requestInterceptorTape;
                this.val$methodInfo = restMethodInfo;
                this.val$args = objArr;
                super(callback, callbackExecutor, errorHandler);
            }

            public ResponseWrapper obtainResponse() {
                return (ResponseWrapper) RestHandler.this.invokeRequest(this.val$interceptorTape, this.val$methodInfo, this.val$args);
            }
        }

        RestHandler(Map<Method, RestMethodInfo> methodDetailsCache) {
            this.methodDetailsCache = methodDetailsCache;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            RestMethodInfo methodInfo = RestAdapter.getMethodInfo(this.methodDetailsCache, method);
            if (methodInfo.isSynchronous) {
                try {
                    return invokeRequest(RestAdapter.this.requestInterceptor, methodInfo, args);
                } catch (RetrofitError error) {
                    Throwable newError = RestAdapter.this.errorHandler.handleError(error);
                    if (newError != null) {
                        throw newError;
                    }
                    throw new IllegalStateException("Error handler returned null for wrapped exception.", error);
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
                return RestAdapter.this.rxSupport.createRequestObservable(new AnonymousClass1(methodInfo, args));
            } else {
                RequestInterceptorTape interceptorTape = new RequestInterceptorTape();
                RestAdapter.this.requestInterceptor.intercept(interceptorTape);
                Callback<?> callback = args[args.length - 1];
                RestAdapter.this.httpExecutor.execute(new AnonymousClass2(callback, RestAdapter.this.callbackExecutor, RestAdapter.this.errorHandler, interceptorTape, methodInfo, args));
                return null;
            }
        }

        private Object invokeRequest(RequestInterceptor requestInterceptor, RestMethodInfo methodInfo, Object[] args) {
            ExceptionCatchingTypedInput exceptionCatchingTypedInput;
            String str = null;
            Response response;
            Type type;
            try {
                methodInfo.init();
                String serverUrl = RestAdapter.this.server.getUrl();
                RequestBuilder requestBuilder = new RequestBuilder(serverUrl, methodInfo, RestAdapter.this.converter);
                requestBuilder.setArguments(args);
                requestInterceptor.intercept(requestBuilder);
                Request request = requestBuilder.build();
                str = request.getUrl();
                if (!methodInfo.isSynchronous) {
                    int substrEnd = str.indexOf("?", serverUrl.length());
                    if (substrEnd == -1) {
                        substrEnd = str.length();
                    }
                    Thread.currentThread().setName(RestAdapter.THREAD_PREFIX + str.substring(serverUrl.length(), substrEnd));
                }
                if (RestAdapter.this.logLevel.log()) {
                    request = RestAdapter.this.logAndReplaceRequest("HTTP", request, args);
                }
                Object profilerObject = null;
                if (RestAdapter.this.profiler != null) {
                    profilerObject = RestAdapter.this.profiler.beforeCall();
                }
                long start = System.nanoTime();
                response = RestAdapter.this.clientProvider.get().execute(request);
                long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
                int statusCode = response.getStatus();
                if (RestAdapter.this.profiler != null) {
                    RestAdapter.this.profiler.afterCall(RestAdapter.getRequestInfo(serverUrl, methodInfo, request), elapsedTime, statusCode, profilerObject);
                }
                if (RestAdapter.this.logLevel.log()) {
                    response = RestAdapter.this.logAndReplaceResponse(str, response, elapsedTime);
                }
                type = methodInfo.responseObjectType;
                if (statusCode >= 200 && statusCode < 300) {
                    ResponseWrapper responseWrapper;
                    if (type.equals(Response.class)) {
                        if (!methodInfo.isStreaming) {
                            response = Utils.readBodyToBytesIfNecessary(response);
                        }
                        if (methodInfo.isSynchronous) {
                            if (!methodInfo.isSynchronous) {
                                Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                            }
                            return response;
                        }
                        responseWrapper = new ResponseWrapper(response, response);
                        if (!methodInfo.isSynchronous) {
                            Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                        }
                        return responseWrapper;
                    }
                    TypedInput body = response.getBody();
                    if (body != null) {
                        exceptionCatchingTypedInput = new ExceptionCatchingTypedInput(body);
                        Object convert = RestAdapter.this.converter.fromBody(exceptionCatchingTypedInput, type);
                        RestAdapter.this.logResponseBody(body, convert);
                        if (methodInfo.isSynchronous) {
                            if (!methodInfo.isSynchronous) {
                                Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                            }
                            return convert;
                        }
                        responseWrapper = new ResponseWrapper(response, convert);
                        if (!methodInfo.isSynchronous) {
                            Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                        }
                        return responseWrapper;
                    } else if (methodInfo.isSynchronous) {
                        if (!methodInfo.isSynchronous) {
                            Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                        }
                        return null;
                    } else {
                        responseWrapper = new ResponseWrapper(response, null);
                        if (!methodInfo.isSynchronous) {
                            Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                        }
                        return responseWrapper;
                    }
                }
                throw RetrofitError.httpError(str, Utils.readBodyToBytesIfNecessary(response), RestAdapter.this.converter, type);
            } catch (ConversionException e) {
                if (exceptionCatchingTypedInput.threwException()) {
                    throw exceptionCatchingTypedInput.getThrownException();
                }
                throw RetrofitError.conversionError(str, Utils.replaceResponseBody(response, null), RestAdapter.this.converter, type, e);
            } catch (RetrofitError e2) {
                throw e2;
            } catch (IOException e3) {
                if (RestAdapter.this.logLevel.log()) {
                    RestAdapter.this.logException(e3, str);
                }
                throw RetrofitError.networkError(str, e3);
            } catch (Throwable th) {
                if (!methodInfo.isSynchronous) {
                    Thread.currentThread().setName(RestAdapter.IDLE_THREAD_NAME);
                }
            }
        }
    }

    private RestAdapter(Endpoint server, Provider clientProvider, Executor httpExecutor, Executor callbackExecutor, RequestInterceptor requestInterceptor, Converter converter, Profiler profiler, ErrorHandler errorHandler, Log log, LogLevel logLevel) {
        this.serviceMethodInfoCache = new LinkedHashMap();
        this.server = server;
        this.clientProvider = clientProvider;
        this.httpExecutor = httpExecutor;
        this.callbackExecutor = callbackExecutor;
        this.requestInterceptor = requestInterceptor;
        this.converter = converter;
        this.profiler = profiler;
        this.errorHandler = errorHandler;
        this.log = log;
        this.logLevel = logLevel;
    }

    public void setLogLevel(LogLevel loglevel) {
        if (this.logLevel != null) {
            this.logLevel = loglevel;
            return;
        }
        throw new NullPointerException("Log level may not be null.");
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public <T> T create(Class<T> service) {
        Utils.validateServiceClass(service);
        return Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new RestHandler(getMethodInfoCache(service)));
    }

    Map<Method, RestMethodInfo> getMethodInfoCache(Class<?> service) {
        Map<Method, RestMethodInfo> methodInfoCache;
        synchronized (this.serviceMethodInfoCache) {
            methodInfoCache = (Map) this.serviceMethodInfoCache.get(service);
            if (methodInfoCache == null) {
                methodInfoCache = new LinkedHashMap();
                this.serviceMethodInfoCache.put(service, methodInfoCache);
            }
        }
        return methodInfoCache;
    }

    static RestMethodInfo getMethodInfo(Map<Method, RestMethodInfo> cache, Method method) {
        RestMethodInfo methodInfo;
        synchronized (cache) {
            methodInfo = (RestMethodInfo) cache.get(method);
            if (methodInfo == null) {
                methodInfo = new RestMethodInfo(method);
                cache.put(method, methodInfo);
            }
        }
        return methodInfo;
    }

    Request logAndReplaceRequest(String name, Request request, Object[] args) throws IOException {
        this.log.log(String.format("---> %s %s %s", new Object[]{name, request.getMethod(), request.getUrl()}));
        if (this.logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
            for (Header header : request.getHeaders()) {
                this.log.log(header.toString());
            }
            String bodySize = "no";
            TypedOutput body = request.getBody();
            if (body != null) {
                String bodyMime = body.mimeType();
                if (bodyMime != null) {
                    this.log.log("Content-Type: " + bodyMime);
                }
                long bodyLength = body.length();
                bodySize = bodyLength + "-byte";
                if (bodyLength != -1) {
                    this.log.log("Content-Length: " + bodyLength);
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
                    for (int i = 0; i < args.length; i++) {
                        this.log.log("#" + i + ": " + args[i]);
                    }
                }
            }
            this.log.log(String.format("---> END %s (%s body)", new Object[]{name, bodySize}));
        }
        return request;
    }

    private Response logAndReplaceResponse(String url, Response response, long elapsedTime) throws IOException {
        this.log.log(String.format("<--- HTTP %s %s (%sms)", new Object[]{Integer.valueOf(response.getStatus()), url, Long.valueOf(elapsedTime)}));
        if (this.logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
            for (Header header : response.getHeaders()) {
                this.log.log(header.toString());
            }
            long bodySize = 0;
            TypedInput body = response.getBody();
            if (body != null) {
                bodySize = body.length();
                if (this.logLevel.ordinal() >= LogLevel.FULL.ordinal()) {
                    if (!response.getHeaders().isEmpty()) {
                        this.log.log("");
                    }
                    if (!(body instanceof TypedByteArray)) {
                        response = Utils.readBodyToBytesIfNecessary(response);
                        body = response.getBody();
                    }
                    byte[] bodyBytes = ((TypedByteArray) body).getBytes();
                    bodySize = (long) bodyBytes.length;
                    this.log.log(new String(bodyBytes, MimeUtil.parseCharset(body.mimeType(), "UTF-8")));
                }
            }
            this.log.log(String.format("<--- END HTTP (%s-byte body)", new Object[]{Long.valueOf(bodySize)}));
        }
        return response;
    }

    private void logResponseBody(TypedInput body, Object convert) {
        if (this.logLevel.ordinal() == LogLevel.HEADERS_AND_ARGS.ordinal()) {
            this.log.log("<--- BODY:");
            this.log.log(convert.toString());
        }
    }

    void logException(Throwable t, String url) {
        Log log = this.log;
        String str = "---- ERROR %s";
        Object[] objArr = new Object[1];
        if (url == null) {
            url = "";
        }
        objArr[0] = url;
        log.log(String.format(str, objArr));
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        this.log.log(sw.toString());
        this.log.log("---- END ERROR");
    }

    private static RequestInformation getRequestInfo(String serverUrl, RestMethodInfo methodDetails, Request request) {
        long contentLength = 0;
        String contentType = null;
        TypedOutput body = request.getBody();
        if (body != null) {
            contentLength = body.length();
            contentType = body.mimeType();
        }
        return new RequestInformation(methodDetails.requestMethod, serverUrl, methodDetails.requestUrl, contentLength, contentType);
    }
}
