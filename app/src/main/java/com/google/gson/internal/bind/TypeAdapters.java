package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

public final class TypeAdapters {
    public static final TypeAdapter<BigDecimal> BIG_DECIMAL = null;
    public static final TypeAdapter<BigInteger> BIG_INTEGER = null;
    public static final TypeAdapter<BitSet> BIT_SET = null;
    public static final TypeAdapterFactory BIT_SET_FACTORY = null;
    public static final TypeAdapter<Boolean> BOOLEAN = null;
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = null;
    public static final TypeAdapterFactory BOOLEAN_FACTORY = null;
    public static final TypeAdapter<Number> BYTE = null;
    public static final TypeAdapterFactory BYTE_FACTORY = null;
    public static final TypeAdapter<Calendar> CALENDAR = null;
    public static final TypeAdapterFactory CALENDAR_FACTORY = null;
    public static final TypeAdapter<Character> CHARACTER = null;
    public static final TypeAdapterFactory CHARACTER_FACTORY = null;
    public static final TypeAdapter<Class> CLASS = null;
    public static final TypeAdapterFactory CLASS_FACTORY = null;
    public static final TypeAdapter<Number> DOUBLE = null;
    public static final TypeAdapterFactory ENUM_FACTORY = null;
    public static final TypeAdapter<Number> FLOAT = null;
    public static final TypeAdapter<InetAddress> INET_ADDRESS = null;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY = null;
    public static final TypeAdapter<Number> INTEGER = null;
    public static final TypeAdapterFactory INTEGER_FACTORY = null;
    public static final TypeAdapter<JsonElement> JSON_ELEMENT = null;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = null;
    public static final TypeAdapter<Locale> LOCALE = null;
    public static final TypeAdapterFactory LOCALE_FACTORY = null;
    public static final TypeAdapter<Number> LONG = null;
    public static final TypeAdapter<Number> NUMBER = null;
    public static final TypeAdapterFactory NUMBER_FACTORY = null;
    public static final TypeAdapter<Number> SHORT = null;
    public static final TypeAdapterFactory SHORT_FACTORY = null;
    public static final TypeAdapter<String> STRING = null;
    public static final TypeAdapter<StringBuffer> STRING_BUFFER = null;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY = null;
    public static final TypeAdapter<StringBuilder> STRING_BUILDER = null;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY = null;
    public static final TypeAdapterFactory STRING_FACTORY = null;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY = null;
    public static final TypeAdapter<URI> URI = null;
    public static final TypeAdapterFactory URI_FACTORY = null;
    public static final TypeAdapter<URL> URL = null;
    public static final TypeAdapterFactory URL_FACTORY = null;
    public static final TypeAdapter<UUID> UUID = null;
    public static final TypeAdapterFactory UUID_FACTORY = null;

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.27 */
    static class AnonymousClass27 implements TypeAdapterFactory {
        final /* synthetic */ TypeToken val$type;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        AnonymousClass27(TypeToken typeToken, TypeAdapter typeAdapter) {
            this.val$type = typeToken;
            this.val$typeAdapter = typeAdapter;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return !typeToken.equals(this.val$type) ? null : this.val$typeAdapter;
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.28 */
    static class AnonymousClass28 implements TypeAdapterFactory {
        final /* synthetic */ Class val$type;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        AnonymousClass28(Class cls, TypeAdapter typeAdapter) {
            this.val$type = cls;
            this.val$typeAdapter = typeAdapter;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() != this.val$type ? null : this.val$typeAdapter;
        }

        public String toString() {
            return "Factory[type=" + this.val$type.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.29 */
    static class AnonymousClass29 implements TypeAdapterFactory {
        final /* synthetic */ Class val$boxed;
        final /* synthetic */ TypeAdapter val$typeAdapter;
        final /* synthetic */ Class val$unboxed;

        AnonymousClass29(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$unboxed = cls;
            this.val$boxed = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Class<? super T> rawType = typeToken.getRawType();
            return (rawType == this.val$unboxed || rawType == this.val$boxed) ? this.val$typeAdapter : null;
        }

        public String toString() {
            return "Factory[type=" + this.val$boxed.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.30 */
    static class AnonymousClass30 implements TypeAdapterFactory {
        final /* synthetic */ Class val$base;
        final /* synthetic */ Class val$sub;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        AnonymousClass30(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$base = cls;
            this.val$sub = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Class<? super T> rawType = typeToken.getRawType();
            return (rawType == this.val$base || rawType == this.val$sub) ? this.val$typeAdapter : null;
        }

        public String toString() {
            return "Factory[type=" + this.val$base.getName() + "+" + this.val$sub.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.31 */
    static class AnonymousClass31 implements TypeAdapterFactory {
        final /* synthetic */ Class val$clazz;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        AnonymousClass31(Class cls, TypeAdapter typeAdapter) {
            this.val$clazz = cls;
            this.val$typeAdapter = typeAdapter;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return !this.val$clazz.isAssignableFrom(typeToken.getRawType()) ? null : this.val$typeAdapter;
        }

        public String toString() {
            return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.32 */
    static /* synthetic */ class AnonymousClass32 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken = null;

        static {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.gson.internal.bind.TypeAdapters.32.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:263)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:367)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:357)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:103)
	... 8 more
*/
            /*
            r0 = com.google.gson.stream.JsonToken.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$gson$stream$JsonToken = r0;
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r1 = com.google.gson.stream.JsonToken.NUMBER;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0077 }
            r1 = com.google.gson.stream.JsonToken.BOOLEAN;	 Catch:{ NoSuchFieldError -> 0x0077 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0077 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0077 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r1 = com.google.gson.stream.JsonToken.STRING;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0073 }
            r1 = com.google.gson.stream.JsonToken.NULL;	 Catch:{ NoSuchFieldError -> 0x0073 }
            r2 = 4;	 Catch:{ NoSuchFieldError -> 0x0073 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0073 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r1 = com.google.gson.stream.JsonToken.BEGIN_ARRAY;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r2 = 5;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x006f }
            r1 = com.google.gson.stream.JsonToken.BEGIN_OBJECT;	 Catch:{ NoSuchFieldError -> 0x006f }
            r2 = 6;	 Catch:{ NoSuchFieldError -> 0x006f }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x006f }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x006d }
            r1 = com.google.gson.stream.JsonToken.END_DOCUMENT;	 Catch:{ NoSuchFieldError -> 0x006d }
            r2 = 7;	 Catch:{ NoSuchFieldError -> 0x006d }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x006d }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x006b }
            r1 = com.google.gson.stream.JsonToken.NAME;	 Catch:{ NoSuchFieldError -> 0x006b }
            r2 = 8;	 Catch:{ NoSuchFieldError -> 0x006b }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x006b }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0069 }
            r1 = com.google.gson.stream.JsonToken.END_OBJECT;	 Catch:{ NoSuchFieldError -> 0x0069 }
            r2 = 9;	 Catch:{ NoSuchFieldError -> 0x0069 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0069 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0067 }
            r1 = com.google.gson.stream.JsonToken.END_ARRAY;	 Catch:{ NoSuchFieldError -> 0x0067 }
            r2 = 10;	 Catch:{ NoSuchFieldError -> 0x0067 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0067 }
        L_0x0067:
            r0 = move-exception;
            goto L_0x0066;
        L_0x0069:
            r0 = move-exception;
            goto L_0x005c;
        L_0x006b:
            r0 = move-exception;
            goto L_0x0052;
        L_0x006d:
            r0 = move-exception;
            goto L_0x0048;
        L_0x006f:
            r0 = move-exception;
            goto L_0x003f;
        L_0x0071:
            r0 = move-exception;
            goto L_0x0036;
        L_0x0073:
            r0 = move-exception;
            goto L_0x002d;
        L_0x0075:
            r0 = move-exception;
            goto L_0x0024;
        L_0x0077:
            r0 = move-exception;
            goto L_0x001b;
        L_0x0079:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.32.<clinit>():void");
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.3 */
    static class AnonymousClass3 extends TypeAdapter<Boolean> {
        AnonymousClass3() {
        }

        public /* bridge */ /* synthetic */ Object m0read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Boolean) obj);
        }

        public Boolean read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            } else if (in.peek() != JsonToken.STRING) {
                return Boolean.valueOf(in.nextBoolean());
            } else {
                return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
            }
        }

        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value != null) {
                out.value(value.booleanValue());
            } else {
                out.nullValue();
            }
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.4 */
    static class AnonymousClass4 extends TypeAdapter<Boolean> {
        AnonymousClass4() {
        }

        public /* bridge */ /* synthetic */ Object m1read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Boolean) obj);
        }

        public Boolean read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Boolean.valueOf(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Boolean value) throws IOException {
            String bool;
            if (value != null) {
                bool = value.toString();
            } else {
                bool = "null";
            }
            out.value(bool);
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.5 */
    static class AnonymousClass5 extends TypeAdapter<Number> {
        AnonymousClass5() {
        }

        public /* bridge */ /* synthetic */ Object m2read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Number) obj);
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                try {
                    return Byte.valueOf((byte) in.nextInt());
                } catch (Throwable e) {
                    throw new JsonSyntaxException(e);
                }
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.6 */
    static class AnonymousClass6 extends TypeAdapter<Number> {
        AnonymousClass6() {
        }

        public /* bridge */ /* synthetic */ Object m3read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Number) obj);
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                try {
                    return Short.valueOf((short) in.nextInt());
                } catch (Throwable e) {
                    throw new JsonSyntaxException(e);
                }
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.7 */
    static class AnonymousClass7 extends TypeAdapter<Number> {
        AnonymousClass7() {
        }

        public /* bridge */ /* synthetic */ Object m4read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Number) obj);
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                try {
                    return Integer.valueOf(in.nextInt());
                } catch (Throwable e) {
                    throw new JsonSyntaxException(e);
                }
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.8 */
    static class AnonymousClass8 extends TypeAdapter<Number> {
        AnonymousClass8() {
        }

        public /* bridge */ /* synthetic */ Object m5read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Number) obj);
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                try {
                    return Long.valueOf(in.nextLong());
                } catch (Throwable e) {
                    throw new JsonSyntaxException(e);
                }
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters.9 */
    static class AnonymousClass9 extends TypeAdapter<Number> {
        AnonymousClass9() {
        }

        public /* bridge */ /* synthetic */ Object m6read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Number) obj);
        }

        public Number read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return Float.valueOf((float) in.nextDouble());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    }

    private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
        private final Map<T, String> constantToName;
        private final Map<String, T> nameToConstant;

        public /* bridge */ /* synthetic */ Object m7read(JsonReader jsonReader) throws IOException {
            return read(jsonReader);
        }

        public /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) throws IOException {
            write(jsonWriter, (Enum) obj);
        }

        public EnumTypeAdapter(Class<T> classOfT) {
            this.nameToConstant = new HashMap();
            this.constantToName = new HashMap();
            try {
                for (T constant : (Enum[]) classOfT.getEnumConstants()) {
                    String name = constant.name();
                    SerializedName annotation = (SerializedName) classOfT.getField(name).getAnnotation(SerializedName.class);
                    if (annotation != null) {
                        name = annotation.value();
                    }
                    this.nameToConstant.put(name, constant);
                    this.constantToName.put(constant, name);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }

        public T read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                return (Enum) this.nameToConstant.get(in.nextString());
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, T value) throws IOException {
            String str = null;
            if (value != null) {
                str = (String) this.constantToName.get(value);
            }
            out.value(str);
        }
    }

    private TypeAdapters() {
    }

    static {
        CLASS = new TypeAdapter<Class>() {
            public void write(JsonWriter out, Class value) throws IOException {
                if (value != null) {
                    throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value.getName() + ". Forgot to register a type adapter?");
                }
                out.nullValue();
            }

            public Class read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
                }
                in.nextNull();
                return null;
            }
        };
        CLASS_FACTORY = newFactory(Class.class, CLASS);
        BIT_SET = new TypeAdapter<BitSet>() {
            public BitSet read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    BitSet bitset = new BitSet();
                    in.beginArray();
                    int i = 0;
                    JsonToken tokenType = in.peek();
                    while (tokenType != JsonToken.END_ARRAY) {
                        boolean set;
                        switch (AnonymousClass32.$SwitchMap$com$google$gson$stream$JsonToken[tokenType.ordinal()]) {
                            case 1:
                                if (in.nextInt() != 0) {
                                    set = true;
                                    break;
                                }
                                set = false;
                                break;
                            case 2:
                                set = in.nextBoolean();
                                break;
                            case 3:
                                String stringValue = in.nextString();
                                try {
                                    set = Integer.parseInt(stringValue) != 0;
                                    break;
                                } catch (NumberFormatException e) {
                                    throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + stringValue);
                                }
                            default:
                                throw new JsonSyntaxException("Invalid bitset value type: " + tokenType);
                        }
                        if (set) {
                            bitset.set(i);
                        }
                        i++;
                        tokenType = in.peek();
                    }
                    in.endArray();
                    return bitset;
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, BitSet src) throws IOException {
                if (src != null) {
                    out.beginArray();
                    for (int i = 0; i < src.length(); i++) {
                        int value;
                        if (src.get(i)) {
                            value = 1;
                        } else {
                            value = 0;
                        }
                        out.value((long) value);
                    }
                    out.endArray();
                    return;
                }
                out.nullValue();
            }
        };
        BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
        BOOLEAN = new AnonymousClass3();
        BOOLEAN_AS_STRING = new AnonymousClass4();
        BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
        BYTE = new AnonymousClass5();
        BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
        SHORT = new AnonymousClass6();
        SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
        INTEGER = new AnonymousClass7();
        INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
        LONG = new AnonymousClass8();
        FLOAT = new AnonymousClass9();
        DOUBLE = new TypeAdapter<Number>() {
            public Number read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return Double.valueOf(in.nextDouble());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Number value) throws IOException {
                out.value(value);
            }
        };
        NUMBER = new TypeAdapter<Number>() {
            public Number read(JsonReader in) throws IOException {
                JsonToken jsonToken = in.peek();
                switch (AnonymousClass32.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                    case 1:
                        return new LazilyParsedNumber(in.nextString());
                    case 4:
                        in.nextNull();
                        return null;
                    default:
                        throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
                }
            }

            public void write(JsonWriter out, Number value) throws IOException {
                out.value(value);
            }
        };
        NUMBER_FACTORY = newFactory(Number.class, NUMBER);
        CHARACTER = new TypeAdapter<Character>() {
            public Character read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    String str = in.nextString();
                    if (str.length() == 1) {
                        return Character.valueOf(str.charAt(0));
                    }
                    throw new JsonSyntaxException("Expecting character, got: " + str);
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Character value) throws IOException {
                String str = null;
                if (value != null) {
                    str = String.valueOf(value);
                }
                out.value(str);
            }
        };
        CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
        STRING = new TypeAdapter<String>() {
            public String read(JsonReader in) throws IOException {
                JsonToken peek = in.peek();
                if (peek == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                } else if (peek != JsonToken.BOOLEAN) {
                    return in.nextString();
                } else {
                    return Boolean.toString(in.nextBoolean());
                }
            }

            public void write(JsonWriter out, String value) throws IOException {
                out.value(value);
            }
        };
        BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
            public BigDecimal read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    try {
                        return new BigDecimal(in.nextString());
                    } catch (Throwable e) {
                        throw new JsonSyntaxException(e);
                    }
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, BigDecimal value) throws IOException {
                out.value((Number) value);
            }
        };
        BIG_INTEGER = new TypeAdapter<BigInteger>() {
            public BigInteger read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    try {
                        return new BigInteger(in.nextString());
                    } catch (Throwable e) {
                        throw new JsonSyntaxException(e);
                    }
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, BigInteger value) throws IOException {
                out.value((Number) value);
            }
        };
        STRING_FACTORY = newFactory(String.class, STRING);
        STRING_BUILDER = new TypeAdapter<StringBuilder>() {
            public StringBuilder read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return new StringBuilder(in.nextString());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, StringBuilder value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toString();
                }
                out.value(str);
            }
        };
        STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
        STRING_BUFFER = new TypeAdapter<StringBuffer>() {
            public StringBuffer read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return new StringBuffer(in.nextString());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, StringBuffer value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toString();
                }
                out.value(str);
            }
        };
        STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
        URL = new TypeAdapter<URL>() {
            public URL read(JsonReader in) throws IOException {
                URL url = null;
                if (in.peek() != JsonToken.NULL) {
                    String nextString = in.nextString();
                    if (!"null".equals(nextString)) {
                        url = new URL(nextString);
                    }
                    return url;
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, URL value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toExternalForm();
                }
                out.value(str);
            }
        };
        URL_FACTORY = newFactory(URL.class, URL);
        URI = new TypeAdapter<URI>() {
            public URI read(JsonReader in) throws IOException {
                URI uri = null;
                if (in.peek() != JsonToken.NULL) {
                    try {
                        String nextString = in.nextString();
                        if (!"null".equals(nextString)) {
                            uri = new URI(nextString);
                        }
                        return uri;
                    } catch (Throwable e) {
                        throw new JsonIOException(e);
                    }
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, URI value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toASCIIString();
                }
                out.value(str);
            }
        };
        URI_FACTORY = newFactory(URI.class, URI);
        INET_ADDRESS = new TypeAdapter<InetAddress>() {
            public InetAddress read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return InetAddress.getByName(in.nextString());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, InetAddress value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.getHostAddress();
                }
                out.value(str);
            }
        };
        INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
        UUID = new TypeAdapter<UUID>() {
            public UUID read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return UUID.fromString(in.nextString());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, UUID value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toString();
                }
                out.value(str);
            }
        };
        UUID_FACTORY = newFactory(UUID.class, UUID);
        TIMESTAMP_FACTORY = new TypeAdapterFactory() {

            /* renamed from: com.google.gson.internal.bind.TypeAdapters.22.1 */
            class AnonymousClass1 extends TypeAdapter<Timestamp> {
                final /* synthetic */ TypeAdapter val$dateTypeAdapter;

                AnonymousClass1(TypeAdapter typeAdapter) {
                    this.val$dateTypeAdapter = typeAdapter;
                }

                public Timestamp read(JsonReader in) throws IOException {
                    Date date = (Date) this.val$dateTypeAdapter.read(in);
                    if (date == null) {
                        return null;
                    }
                    return new Timestamp(date.getTime());
                }

                public void write(JsonWriter out, Timestamp value) throws IOException {
                    this.val$dateTypeAdapter.write(out, value);
                }
            }

            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                return typeToken.getRawType() == Timestamp.class ? new AnonymousClass1(gson.getAdapter(Date.class)) : null;
            }
        };
        CALENDAR = new TypeAdapter<Calendar>() {
            public Calendar read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    in.beginObject();
                    int year = 0;
                    int month = 0;
                    int dayOfMonth = 0;
                    int hourOfDay = 0;
                    int minute = 0;
                    int second = 0;
                    while (in.peek() != JsonToken.END_OBJECT) {
                        String name = in.nextName();
                        int value = in.nextInt();
                        if ("year".equals(name)) {
                            year = value;
                        } else if ("month".equals(name)) {
                            month = value;
                        } else if ("dayOfMonth".equals(name)) {
                            dayOfMonth = value;
                        } else if ("hourOfDay".equals(name)) {
                            hourOfDay = value;
                        } else if ("minute".equals(name)) {
                            minute = value;
                        } else if ("second".equals(name)) {
                            second = value;
                        }
                    }
                    in.endObject();
                    return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Calendar value) throws IOException {
                if (value != null) {
                    out.beginObject();
                    out.name("year");
                    out.value((long) value.get(1));
                    out.name("month");
                    out.value((long) value.get(2));
                    out.name("dayOfMonth");
                    out.value((long) value.get(5));
                    out.name("hourOfDay");
                    out.value((long) value.get(11));
                    out.name("minute");
                    out.value((long) value.get(12));
                    out.name("second");
                    out.value((long) value.get(13));
                    out.endObject();
                    return;
                }
                out.nullValue();
            }
        };
        CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
        LOCALE = new TypeAdapter<Locale>() {
            public Locale read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    StringTokenizer tokenizer = new StringTokenizer(in.nextString(), "_");
                    String language = null;
                    String country = null;
                    String variant = null;
                    if (tokenizer.hasMoreElements()) {
                        language = tokenizer.nextToken();
                    }
                    if (tokenizer.hasMoreElements()) {
                        country = tokenizer.nextToken();
                    }
                    if (tokenizer.hasMoreElements()) {
                        variant = tokenizer.nextToken();
                    }
                    if (country == null && variant == null) {
                        return new Locale(language);
                    }
                    if (variant != null) {
                        return new Locale(language, country, variant);
                    }
                    return new Locale(language, country);
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Locale value) throws IOException {
                String str = null;
                if (value != null) {
                    str = value.toString();
                }
                out.value(str);
            }
        };
        LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
        JSON_ELEMENT = new TypeAdapter<JsonElement>() {
            public JsonElement read(JsonReader in) throws IOException {
                switch (AnonymousClass32.$SwitchMap$com$google$gson$stream$JsonToken[in.peek().ordinal()]) {
                    case 1:
                        return new JsonPrimitive(new LazilyParsedNumber(in.nextString()));
                    case 2:
                        return new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
                    case 3:
                        return new JsonPrimitive(in.nextString());
                    case 4:
                        in.nextNull();
                        return JsonNull.INSTANCE;
                    case 5:
                        JsonArray array = new JsonArray();
                        in.beginArray();
                        while (in.hasNext()) {
                            array.add(read(in));
                        }
                        in.endArray();
                        return array;
                    case 6:
                        JsonObject object = new JsonObject();
                        in.beginObject();
                        while (in.hasNext()) {
                            object.add(in.nextName(), read(in));
                        }
                        in.endObject();
                        return object;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            public void write(JsonWriter out, JsonElement value) throws IOException {
                if (value == null || value.isJsonNull()) {
                    out.nullValue();
                } else if (value.isJsonPrimitive()) {
                    JsonPrimitive primitive = value.getAsJsonPrimitive();
                    if (primitive.isNumber()) {
                        out.value(primitive.getAsNumber());
                    } else if (primitive.isBoolean()) {
                        out.value(primitive.getAsBoolean());
                    } else {
                        out.value(primitive.getAsString());
                    }
                } else if (value.isJsonArray()) {
                    out.beginArray();
                    i$ = value.getAsJsonArray().iterator();
                    while (i$.hasNext()) {
                        write(out, (JsonElement) i$.next());
                    }
                    out.endArray();
                } else if (value.isJsonObject()) {
                    out.beginObject();
                    for (Entry<String, JsonElement> e : value.getAsJsonObject().entrySet()) {
                        out.name((String) e.getKey());
                        write(out, (JsonElement) e.getValue());
                    }
                    out.endObject();
                } else {
                    throw new IllegalArgumentException("Couldn't write " + value.getClass());
                }
            }
        };
        JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
        ENUM_FACTORY = new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                    return null;
                }
                if (!rawType.isEnum()) {
                    rawType = rawType.getSuperclass();
                }
                return new EnumTypeAdapter(rawType);
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(TypeToken<TT> type, TypeAdapter<TT> typeAdapter) {
        return new AnonymousClass27(type, typeAdapter);
    }

    public static <TT> TypeAdapterFactory newFactory(Class<TT> type, TypeAdapter<TT> typeAdapter) {
        return new AnonymousClass28(type, typeAdapter);
    }

    public static <TT> TypeAdapterFactory newFactory(Class<TT> unboxed, Class<TT> boxed, TypeAdapter<? super TT> typeAdapter) {
        return new AnonymousClass29(unboxed, boxed, typeAdapter);
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(Class<TT> base, Class<? extends TT> sub, TypeAdapter<? super TT> typeAdapter) {
        return new AnonymousClass30(base, sub, typeAdapter);
    }

    public static <TT> TypeAdapterFactory newTypeHierarchyFactory(Class<TT> clazz, TypeAdapter<TT> typeAdapter) {
        return new AnonymousClass31(clazz, typeAdapter);
    }
}
