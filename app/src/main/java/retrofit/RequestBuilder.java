package retrofit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import retrofit.RequestInterceptor.RequestFacade;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.converter.Converter;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit.http.EncodedQueryMap;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

final class RequestBuilder implements RequestFacade {
    private final String apiUrl;
    private TypedOutput body;
    private String contentTypeHeader;
    private final Converter converter;
    private final FormUrlEncodedTypedOutput formBody;
    private List<Header> headers;
    private final boolean isObservable;
    private final boolean isSynchronous;
    private final MultipartTypedOutput multipartBody;
    private final Annotation[] paramAnnotations;
    private StringBuilder queryParams;
    private String relativeUrl;
    private final String requestMethod;

    /* renamed from: retrofit.RequestBuilder.1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$retrofit$RestMethodInfo$RequestType = null;

        static {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: retrofit.RequestBuilder.1.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:263)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:367)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:357)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:103)
	... 6 more
*/
            /*
            r0 = retrofit.RestMethodInfo.RequestType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$retrofit$RestMethodInfo$RequestType = r0;
            r0 = $SwitchMap$retrofit$RestMethodInfo$RequestType;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r1 = retrofit.RestMethodInfo.RequestType.FORM_URL_ENCODED;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0029 }
            r0 = $SwitchMap$retrofit$RestMethodInfo$RequestType;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r1 = retrofit.RestMethodInfo.RequestType.MULTIPART;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r0 = $SwitchMap$retrofit$RestMethodInfo$RequestType;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r1 = retrofit.RestMethodInfo.RequestType.SIMPLE;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x0025 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0025 }
        L_0x0025:
            r0 = move-exception;
            goto L_0x0024;
        L_0x0027:
            r0 = move-exception;
            goto L_0x001b;
        L_0x0029:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit.RequestBuilder.1.<clinit>():void");
        }
    }

    private static class MimeOverridingTypedOutput implements TypedOutput {
        private final TypedOutput delegate;
        private final String mimeType;

        MimeOverridingTypedOutput(TypedOutput delegate, String mimeType) {
            this.delegate = delegate;
            this.mimeType = mimeType;
        }

        public String fileName() {
            return this.delegate.fileName();
        }

        public String mimeType() {
            return this.mimeType;
        }

        public long length() {
            return this.delegate.length();
        }

        public void writeTo(OutputStream out) throws IOException {
            this.delegate.writeTo(out);
        }
    }

    RequestBuilder(String apiUrl, RestMethodInfo methodInfo, Converter converter) {
        this.apiUrl = apiUrl;
        this.converter = converter;
        this.paramAnnotations = methodInfo.requestParamAnnotations;
        this.requestMethod = methodInfo.requestMethod;
        this.isSynchronous = methodInfo.isSynchronous;
        this.isObservable = methodInfo.isObservable;
        if (methodInfo.headers != null) {
            this.headers = new ArrayList(methodInfo.headers);
        }
        this.contentTypeHeader = methodInfo.contentTypeHeader;
        this.relativeUrl = methodInfo.requestUrl;
        String requestQuery = methodInfo.requestQuery;
        if (requestQuery != null) {
            this.queryParams = new StringBuilder().append('?').append(requestQuery);
        }
        switch (AnonymousClass1.$SwitchMap$retrofit$RestMethodInfo$RequestType[methodInfo.requestType.ordinal()]) {
            case 1:
                this.formBody = new FormUrlEncodedTypedOutput();
                this.multipartBody = null;
                this.body = this.formBody;
            case 2:
                this.formBody = null;
                this.multipartBody = new MultipartTypedOutput();
                this.body = this.multipartBody;
            case 3:
                this.formBody = null;
                this.multipartBody = null;
            default:
                throw new IllegalArgumentException("Unknown request type: " + methodInfo.requestType);
        }
    }

    public void addHeader(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Header name must not be null.");
        } else if ("Content-Type".equalsIgnoreCase(name)) {
            this.contentTypeHeader = value;
        } else {
            List<Header> headers = this.headers;
            if (headers == null) {
                headers = new ArrayList(2);
                this.headers = headers;
            }
            headers.add(new Header(name, value));
        }
    }

    public void addPathParam(String name, String value) {
        addPathParam(name, value, true);
    }

    public void addEncodedPathParam(String name, String value) {
        addPathParam(name, value, false);
    }

    private void addPathParam(String name, String value, boolean urlEncodeValue) {
        if (name == null) {
            throw new IllegalArgumentException("Path replacement name must not be null.");
        } else if (value == null) {
            throw new IllegalArgumentException("Path replacement \"" + name + "\" value must not be null.");
        } else if (urlEncodeValue) {
            this.relativeUrl = this.relativeUrl.replace("{" + name + "}", URLEncoder.encode(String.valueOf(value), "UTF-8").replace("+", "%20"));
        } else {
            try {
                this.relativeUrl = this.relativeUrl.replace("{" + name + "}", String.valueOf(value));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unable to convert path parameter \"" + name + "\" value to UTF-8:" + value, e);
            }
        }
    }

    public void addQueryParam(String name, String value) {
        addQueryParam(name, value, false, true);
    }

    public void addEncodedQueryParam(String name, String value) {
        addQueryParam(name, value, false, false);
    }

    private void addQueryParam(String name, Object value, boolean encodeName, boolean encodeValue) {
        if (value instanceof Iterable) {
            for (Object iterableValue : (Iterable) value) {
                if (iterableValue != null) {
                    addQueryParam(name, iterableValue.toString(), encodeName, encodeValue);
                }
            }
        } else if (value.getClass().isArray()) {
            int arrayLength = Array.getLength(value);
            for (int x = 0; x < arrayLength; x++) {
                Object arrayValue = Array.get(value, x);
                if (arrayValue != null) {
                    addQueryParam(name, arrayValue.toString(), encodeName, encodeValue);
                }
            }
        } else {
            addQueryParam(name, value.toString(), encodeName, encodeValue);
        }
    }

    private void addQueryParam(String name, String value, boolean encodeName, boolean encodeValue) {
        if (name == null) {
            throw new IllegalArgumentException("Query param name must not be null.");
        } else if (value != null) {
            try {
                char c;
                StringBuilder queryParams = this.queryParams;
                if (queryParams == null) {
                    queryParams = new StringBuilder();
                    this.queryParams = queryParams;
                }
                if (queryParams.length() <= 0) {
                    c = '?';
                } else {
                    c = '&';
                }
                queryParams.append(c);
                if (encodeName) {
                    name = URLEncoder.encode(name, "UTF-8");
                }
                if (encodeValue) {
                    value = URLEncoder.encode(value, "UTF-8");
                }
                queryParams.append(name).append('=').append(value);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unable to convert query parameter \"" + name + "\" value to UTF-8: " + value, e);
            }
        } else {
            throw new IllegalArgumentException("Query param \"" + name + "\" value must not be null.");
        }
    }

    private void addQueryParamMap(int parameterNumber, Map<?, ?> map, boolean encodeNames, boolean encodeValues) {
        for (Entry<?, ?> entry : map.entrySet()) {
            Object entryKey = entry.getKey();
            if (entryKey != null) {
                Object entryValue = entry.getValue();
                if (entryValue != null) {
                    addQueryParam(entryKey.toString(), entryValue.toString(), encodeNames, encodeValues);
                }
            } else {
                throw new IllegalArgumentException("Parameter #" + (parameterNumber + 1) + " query map contained null key.");
            }
        }
    }

    void setArguments(Object[] args) {
        if (args != null) {
            int count = args.length;
            if (!(this.isSynchronous || this.isObservable)) {
                count--;
            }
            for (int i = 0; i < count; i++) {
                Object value = args[i];
                Annotation annotation = this.paramAnnotations[i];
                Class<? extends Annotation> annotationType = annotation.annotationType();
                String name;
                if (annotationType == Path.class) {
                    Path path = (Path) annotation;
                    name = path.value();
                    if (value != null) {
                        addPathParam(name, value.toString(), path.encode());
                    } else {
                        throw new IllegalArgumentException("Path parameter \"" + name + "\" value must not be null.");
                    }
                } else if (annotationType == EncodedPath.class) {
                    name = ((EncodedPath) annotation).value();
                    if (value != null) {
                        addPathParam(name, value.toString(), false);
                    } else {
                        throw new IllegalArgumentException("Path parameter \"" + name + "\" value must not be null.");
                    }
                } else if (annotationType != Query.class) {
                    if (annotationType != EncodedQuery.class) {
                        if (annotationType != QueryMap.class) {
                            if (annotationType != EncodedQueryMap.class) {
                                int arrayLength;
                                int x;
                                Object arrayValue;
                                if (annotationType != retrofit.http.Header.class) {
                                    if (annotationType != Field.class) {
                                        Object entryKey;
                                        Object entryValue;
                                        if (annotationType != FieldMap.class) {
                                            String transferEncoding;
                                            if (annotationType != Part.class) {
                                                if (annotationType != PartMap.class) {
                                                    if (annotationType != Body.class) {
                                                        throw new IllegalArgumentException("Unknown annotation: " + annotationType.getCanonicalName());
                                                    } else if (value == null) {
                                                        throw new IllegalArgumentException("Body parameter value must not be null.");
                                                    } else if (value instanceof TypedOutput) {
                                                        this.body = (TypedOutput) value;
                                                    } else {
                                                        this.body = this.converter.toBody(value);
                                                    }
                                                } else if (value != null) {
                                                    transferEncoding = ((PartMap) annotation).encoding();
                                                    for (Entry<?, ?> entry : ((Map) value).entrySet()) {
                                                        entryKey = entry.getKey();
                                                        if (entryKey != null) {
                                                            String entryName = entryKey.toString();
                                                            entryValue = entry.getValue();
                                                            if (entryValue != null) {
                                                                if (entryValue instanceof TypedOutput) {
                                                                    this.multipartBody.addPart(entryName, transferEncoding, (TypedOutput) entryValue);
                                                                } else if (entryValue instanceof String) {
                                                                    this.multipartBody.addPart(entryName, transferEncoding, new TypedString((String) entryValue));
                                                                } else {
                                                                    this.multipartBody.addPart(entryName, transferEncoding, this.converter.toBody(entryValue));
                                                                }
                                                            }
                                                        } else {
                                                            throw new IllegalArgumentException("Parameter #" + (i + 1) + " part map contained null key.");
                                                        }
                                                    }
                                                    continue;
                                                } else {
                                                    continue;
                                                }
                                            } else if (value != null) {
                                                name = ((Part) annotation).value();
                                                transferEncoding = ((Part) annotation).encoding();
                                                if (value instanceof TypedOutput) {
                                                    this.multipartBody.addPart(name, transferEncoding, (TypedOutput) value);
                                                } else if (value instanceof String) {
                                                    this.multipartBody.addPart(name, transferEncoding, new TypedString((String) value));
                                                } else {
                                                    this.multipartBody.addPart(name, transferEncoding, this.converter.toBody(value));
                                                }
                                            }
                                        } else if (value != null) {
                                            FieldMap fieldMap = (FieldMap) annotation;
                                            boolean encodeNames = fieldMap.encodeNames();
                                            boolean encodeValues = fieldMap.encodeValues();
                                            for (Entry<?, ?> entry2 : ((Map) value).entrySet()) {
                                                entryKey = entry2.getKey();
                                                if (entryKey != null) {
                                                    entryValue = entry2.getValue();
                                                    if (entryValue != null) {
                                                        this.formBody.addField(entryKey.toString(), encodeNames, entryValue.toString(), encodeValues);
                                                    }
                                                } else {
                                                    throw new IllegalArgumentException("Parameter #" + (i + 1) + " field map contained null key.");
                                                }
                                            }
                                            continue;
                                        } else {
                                            continue;
                                        }
                                    } else if (value != null) {
                                        Field field = (Field) annotation;
                                        name = field.value();
                                        boolean encodeName = field.encodeName();
                                        boolean encodeValue = field.encodeValue();
                                        if (value instanceof Iterable) {
                                            for (Object iterableValue : (Iterable) value) {
                                                if (iterableValue != null) {
                                                    this.formBody.addField(name, encodeName, iterableValue.toString(), encodeValue);
                                                }
                                            }
                                        } else if (value.getClass().isArray()) {
                                            arrayLength = Array.getLength(value);
                                            for (x = 0; x < arrayLength; x++) {
                                                arrayValue = Array.get(value, x);
                                                if (arrayValue != null) {
                                                    this.formBody.addField(name, encodeName, arrayValue.toString(), encodeValue);
                                                }
                                            }
                                        } else {
                                            this.formBody.addField(name, encodeName, value.toString(), encodeValue);
                                        }
                                    }
                                } else if (value != null) {
                                    name = ((retrofit.http.Header) annotation).value();
                                    if (value instanceof Iterable) {
                                        for (Object iterableValue2 : (Iterable) value) {
                                            if (iterableValue2 != null) {
                                                addHeader(name, iterableValue2.toString());
                                            }
                                        }
                                    } else if (value.getClass().isArray()) {
                                        arrayLength = Array.getLength(value);
                                        for (x = 0; x < arrayLength; x++) {
                                            arrayValue = Array.get(value, x);
                                            if (arrayValue != null) {
                                                addHeader(name, arrayValue.toString());
                                            }
                                        }
                                    } else {
                                        addHeader(name, value.toString());
                                    }
                                }
                            } else if (value != null) {
                                addQueryParamMap(i, (Map) value, false, false);
                            }
                        } else if (value != null) {
                            QueryMap queryMap = (QueryMap) annotation;
                            addQueryParamMap(i, (Map) value, queryMap.encodeNames(), queryMap.encodeValues());
                        }
                    } else if (value != null) {
                        addQueryParam(((EncodedQuery) annotation).value(), value, false, false);
                    }
                } else if (value != null) {
                    Query query = (Query) annotation;
                    addQueryParam(query.value(), value, query.encodeName(), query.encodeValue());
                }
            }
        }
    }

    Request build() throws UnsupportedEncodingException {
        if (this.multipartBody != null && this.multipartBody.getPartCount() == 0) {
            throw new IllegalStateException("Multipart requests must contain at least one part.");
        }
        String apiUrl = this.apiUrl;
        StringBuilder url = new StringBuilder(apiUrl);
        if (apiUrl.endsWith("/")) {
            url.deleteCharAt(url.length() - 1);
        }
        url.append(this.relativeUrl);
        StringBuilder queryParams = this.queryParams;
        if (queryParams != null) {
            url.append(queryParams);
        }
        TypedOutput body = this.body;
        List<Header> headers = this.headers;
        if (this.contentTypeHeader != null) {
            if (body == null) {
                Header header = new Header("Content-Type", this.contentTypeHeader);
                if (headers != null) {
                    headers.add(header);
                } else {
                    headers = Collections.singletonList(header);
                }
            } else {
                body = new MimeOverridingTypedOutput(body, this.contentTypeHeader);
            }
        }
        return new Request(this.requestMethod, url.toString(), headers, body);
    }
}
