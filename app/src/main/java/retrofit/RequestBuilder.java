package retrofit;

import java.io.OutputStream;
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
    private List headers;
    private final boolean isObservable;
    private final boolean isSynchronous;
    private final MultipartTypedOutput multipartBody;
    private final Annotation[] paramAnnotations;
    private StringBuilder queryParams;
    private String relativeUrl;
    private final String requestMethod;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$retrofit$RestMethodInfo$RequestType = null;

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: retrofit.RequestBuilder.1.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:370)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:360)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
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

    class MimeOverridingTypedOutput implements TypedOutput {
        private final TypedOutput delegate;
        private final String mimeType;

        MimeOverridingTypedOutput(TypedOutput typedOutput, String str) {
            this.delegate = typedOutput;
            this.mimeType = str;
        }

        public String fileName() {
            return this.delegate.fileName();
        }

        public long length() {
            return this.delegate.length();
        }

        public String mimeType() {
            return this.mimeType;
        }

        public void writeTo(OutputStream outputStream) {
            this.delegate.writeTo(outputStream);
        }
    }

    RequestBuilder(String str, RestMethodInfo restMethodInfo, Converter converter) {
        this.apiUrl = str;
        this.converter = converter;
        this.paramAnnotations = restMethodInfo.requestParamAnnotations;
        this.requestMethod = restMethodInfo.requestMethod;
        this.isSynchronous = restMethodInfo.isSynchronous;
        this.isObservable = restMethodInfo.isObservable;
        if (restMethodInfo.headers != null) {
            this.headers = new ArrayList(restMethodInfo.headers);
        }
        this.contentTypeHeader = restMethodInfo.contentTypeHeader;
        this.relativeUrl = restMethodInfo.requestUrl;
        String str2 = restMethodInfo.requestQuery;
        if (str2 != null) {
            this.queryParams = new StringBuilder().append('?').append(str2);
        }
        switch (AnonymousClass1.$SwitchMap$retrofit$RestMethodInfo$RequestType[restMethodInfo.requestType.ordinal()]) {
            case 1:
                this.formBody = new FormUrlEncodedTypedOutput();
                this.multipartBody = null;
                this.body = this.formBody;
                return;
            case 2:
                this.formBody = null;
                this.multipartBody = new MultipartTypedOutput();
                this.body = this.multipartBody;
                return;
            case 3:
                this.formBody = null;
                this.multipartBody = null;
                return;
            default:
                throw new IllegalArgumentException("Unknown request type: " + restMethodInfo.requestType);
        }
    }

    private void addPathParam(String str, String str2, boolean z) {
        if (str == null) {
            throw new IllegalArgumentException("Path replacement name must not be null.");
        } else if (str2 == null) {
            throw new IllegalArgumentException("Path replacement \"" + str + "\" value must not be null.");
        } else if (z) {
            this.relativeUrl = this.relativeUrl.replace("{" + str + "}", URLEncoder.encode(String.valueOf(str2), "UTF-8").replace("+", "%20"));
        } else {
            try {
                this.relativeUrl = this.relativeUrl.replace("{" + str + "}", String.valueOf(str2));
            } catch (Throwable e) {
                throw new RuntimeException("Unable to convert path parameter \"" + str + "\" value to UTF-8:" + str2, e);
            }
        }
    }

    private void addQueryParam(String str, Object obj, boolean z, boolean z2) {
        if (obj instanceof Iterable) {
            for (Object next : (Iterable) obj) {
                if (next != null) {
                    addQueryParam(str, next.toString(), z, z2);
                }
            }
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object obj2 = Array.get(obj, i);
                if (obj2 != null) {
                    addQueryParam(str, obj2.toString(), z, z2);
                }
            }
        } else {
            addQueryParam(str, obj.toString(), z, z2);
        }
    }

    private void addQueryParam(String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            throw new IllegalArgumentException("Query param name must not be null.");
        } else if (str2 != null) {
            try {
                StringBuilder stringBuilder;
                StringBuilder stringBuilder2 = this.queryParams;
                if (stringBuilder2 != null) {
                    stringBuilder = stringBuilder2;
                } else {
                    stringBuilder2 = new StringBuilder();
                    this.queryParams = stringBuilder2;
                    stringBuilder = stringBuilder2;
                }
                stringBuilder.append(stringBuilder.length() <= 0 ? '?' : '&');
                if (z) {
                    str = URLEncoder.encode(str, "UTF-8");
                }
                if (z2) {
                    str2 = URLEncoder.encode(str2, "UTF-8");
                }
                stringBuilder.append(str).append('=').append(str2);
            } catch (Throwable e) {
                throw new RuntimeException("Unable to convert query parameter \"" + str + "\" value to UTF-8: " + str2, e);
            }
        } else {
            throw new IllegalArgumentException("Query param \"" + str + "\" value must not be null.");
        }
    }

    private void addQueryParamMap(int i, Map map, boolean z, boolean z2) {
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key != null) {
                Object value = entry.getValue();
                if (value != null) {
                    addQueryParam(key.toString(), value.toString(), z, z2);
                }
            } else {
                throw new IllegalArgumentException("Parameter #" + (i + 1) + " query map contained null key.");
            }
        }
    }

    public void addEncodedPathParam(String str, String str2) {
        addPathParam(str, str2, false);
    }

    public void addEncodedQueryParam(String str, String str2) {
        addQueryParam(str, str2, false, false);
    }

    public void addHeader(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Header name must not be null.");
        } else if ("Content-Type".equalsIgnoreCase(str)) {
            this.contentTypeHeader = str2;
        } else {
            List list = this.headers;
            if (list == null) {
                list = new ArrayList(2);
                this.headers = list;
            }
            list.add(new Header(str, str2));
        }
    }

    public void addPathParam(String str, String str2) {
        addPathParam(str, str2, true);
    }

    public void addQueryParam(String str, String str2) {
        addQueryParam(str, str2, false, true);
    }

    Request build() {
        if (this.multipartBody != null && this.multipartBody.getPartCount() == 0) {
            throw new IllegalStateException("Multipart requests must contain at least one part.");
        }
        String str = this.apiUrl;
        StringBuilder stringBuilder = new StringBuilder(str);
        if (str.endsWith("/")) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append(this.relativeUrl);
        CharSequence charSequence = this.queryParams;
        if (charSequence != null) {
            stringBuilder.append(charSequence);
        }
        TypedOutput typedOutput = this.body;
        List list = this.headers;
        if (this.contentTypeHeader != null) {
            if (typedOutput == null) {
                Header header = new Header("Content-Type", this.contentTypeHeader);
                if (list != null) {
                    list.add(header);
                } else {
                    list = Collections.singletonList(header);
                }
            } else {
                Object mimeOverridingTypedOutput = new MimeOverridingTypedOutput(typedOutput, this.contentTypeHeader);
            }
        }
        return new Request(this.requestMethod, stringBuilder.toString(), list, typedOutput);
    }

    void setArguments(Object[] objArr) {
        if (objArr != null) {
            int length = objArr.length;
            int i = (this.isSynchronous || this.isObservable) ? length : length - 1;
            for (int i2 = 0; i2 < i; i2++) {
                Object obj = objArr[i2];
                Annotation annotation = this.paramAnnotations[i2];
                Class annotationType = annotation.annotationType();
                String value;
                if (annotationType == Path.class) {
                    Path path = (Path) annotation;
                    value = path.value();
                    if (obj != null) {
                        addPathParam(value, obj.toString(), path.encode());
                    } else {
                        throw new IllegalArgumentException("Path parameter \"" + value + "\" value must not be null.");
                    }
                } else if (annotationType == EncodedPath.class) {
                    r0 = ((EncodedPath) annotation).value();
                    if (obj != null) {
                        addPathParam(r0, obj.toString(), false);
                    } else {
                        throw new IllegalArgumentException("Path parameter \"" + r0 + "\" value must not be null.");
                    }
                } else if (annotationType != Query.class) {
                    if (annotationType != EncodedQuery.class) {
                        if (annotationType != QueryMap.class) {
                            if (annotationType != EncodedQueryMap.class) {
                                Object key;
                                if (annotationType != retrofit.http.Header.class) {
                                    boolean encodeValues;
                                    if (annotationType != Field.class) {
                                        Object value2;
                                        if (annotationType != FieldMap.class) {
                                            if (annotationType != Part.class) {
                                                if (annotationType != PartMap.class) {
                                                    if (annotationType != Body.class) {
                                                        throw new IllegalArgumentException("Unknown annotation: " + annotationType.getCanonicalName());
                                                    } else if (obj == null) {
                                                        throw new IllegalArgumentException("Body parameter value must not be null.");
                                                    } else if (obj instanceof TypedOutput) {
                                                        this.body = (TypedOutput) obj;
                                                    } else {
                                                        this.body = this.converter.toBody(obj);
                                                    }
                                                } else if (obj != null) {
                                                    value = ((PartMap) annotation).encoding();
                                                    for (Entry entry : ((Map) obj).entrySet()) {
                                                        Object key2 = entry.getKey();
                                                        if (key2 != null) {
                                                            String obj2 = key2.toString();
                                                            value2 = entry.getValue();
                                                            if (value2 != null) {
                                                                if (value2 instanceof TypedOutput) {
                                                                    this.multipartBody.addPart(obj2, value, (TypedOutput) value2);
                                                                } else if (value2 instanceof String) {
                                                                    this.multipartBody.addPart(obj2, value, new TypedString((String) value2));
                                                                } else {
                                                                    this.multipartBody.addPart(obj2, value, this.converter.toBody(value2));
                                                                }
                                                            }
                                                        } else {
                                                            throw new IllegalArgumentException("Parameter #" + (i2 + 1) + " part map contained null key.");
                                                        }
                                                    }
                                                    continue;
                                                } else {
                                                    continue;
                                                }
                                            } else if (obj != null) {
                                                value = ((Part) annotation).value();
                                                r0 = ((Part) annotation).encoding();
                                                if (obj instanceof TypedOutput) {
                                                    this.multipartBody.addPart(value, r0, (TypedOutput) obj);
                                                } else if (obj instanceof String) {
                                                    this.multipartBody.addPart(value, r0, new TypedString((String) obj));
                                                } else {
                                                    this.multipartBody.addPart(value, r0, this.converter.toBody(obj));
                                                }
                                            }
                                        } else if (obj != null) {
                                            FieldMap fieldMap = (FieldMap) annotation;
                                            boolean encodeNames = fieldMap.encodeNames();
                                            encodeValues = fieldMap.encodeValues();
                                            for (Entry entry2 : ((Map) obj).entrySet()) {
                                                key = entry2.getKey();
                                                if (key != null) {
                                                    value2 = entry2.getValue();
                                                    if (value2 != null) {
                                                        this.formBody.addField(key.toString(), encodeNames, value2.toString(), encodeValues);
                                                    }
                                                } else {
                                                    throw new IllegalArgumentException("Parameter #" + (i2 + 1) + " field map contained null key.");
                                                }
                                            }
                                            continue;
                                        } else {
                                            continue;
                                        }
                                    } else if (obj != null) {
                                        Field field = (Field) annotation;
                                        value = field.value();
                                        encodeValues = field.encodeName();
                                        boolean encodeValue = field.encodeValue();
                                        if (obj instanceof Iterable) {
                                            for (Object obj3 : (Iterable) obj3) {
                                                if (obj3 != null) {
                                                    this.formBody.addField(value, encodeValues, obj3.toString(), encodeValue);
                                                }
                                            }
                                        } else if (obj3.getClass().isArray()) {
                                            int length2 = Array.getLength(obj3);
                                            for (length = 0; length < length2; length++) {
                                                Object obj4 = Array.get(obj3, length);
                                                if (obj4 != null) {
                                                    this.formBody.addField(value, encodeValues, obj4.toString(), encodeValue);
                                                }
                                            }
                                        } else {
                                            this.formBody.addField(value, encodeValues, obj3.toString(), encodeValue);
                                        }
                                    }
                                } else if (obj3 != null) {
                                    value = ((retrofit.http.Header) annotation).value();
                                    if (obj3 instanceof Iterable) {
                                        for (Object obj32 : (Iterable) obj32) {
                                            if (obj32 != null) {
                                                addHeader(value, obj32.toString());
                                            }
                                        }
                                    } else if (obj32.getClass().isArray()) {
                                        int length3 = Array.getLength(obj32);
                                        for (length = 0; length < length3; length++) {
                                            key = Array.get(obj32, length);
                                            if (key != null) {
                                                addHeader(value, key.toString());
                                            }
                                        }
                                    } else {
                                        addHeader(value, obj32.toString());
                                    }
                                }
                            } else if (obj32 != null) {
                                addQueryParamMap(i2, (Map) obj32, false, false);
                            }
                        } else if (obj32 != null) {
                            QueryMap queryMap = (QueryMap) annotation;
                            addQueryParamMap(i2, (Map) obj32, queryMap.encodeNames(), queryMap.encodeValues());
                        }
                    } else if (obj32 != null) {
                        addQueryParam(((EncodedQuery) annotation).value(), obj32, false, false);
                    }
                } else if (obj32 != null) {
                    Query query = (Query) annotation;
                    addQueryParam(query.value(), obj32, query.encodeName(), query.encodeValue());
                }
            }
        }
    }
}
