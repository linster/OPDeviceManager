package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        abstract void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        abstract boolean writeField(Object obj) throws IOException, IllegalAccessException;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }
    }

    /* renamed from: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.1 */
    class AnonymousClass1 extends BoundField {
        final TypeAdapter<?> typeAdapter;
        final /* synthetic */ Gson val$context;
        final /* synthetic */ Field val$field;
        final /* synthetic */ TypeToken val$fieldType;
        final /* synthetic */ boolean val$isPrimitive;

        AnonymousClass1(String x0, boolean x1, boolean x2, Gson gson, Field field, TypeToken typeToken, boolean z) {
            this.val$context = gson;
            this.val$field = field;
            this.val$fieldType = typeToken;
            this.val$isPrimitive = z;
            super(x0, x1, x2);
            this.typeAdapter = ReflectiveTypeAdapterFactory.this.getFieldAdapter(this.val$context, this.val$field, this.val$fieldType);
        }

        void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
            new TypeAdapterRuntimeTypeWrapper(this.val$context, this.typeAdapter, this.val$fieldType.getType()).write(writer, this.val$field.get(value));
        }

        void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
            Object fieldValue = this.typeAdapter.read(reader);
            if (fieldValue != null || !this.val$isPrimitive) {
                this.val$field.set(value, fieldValue);
            }
        }

        public boolean writeField(Object value) throws IOException, IllegalAccessException {
            boolean z = false;
            if (!this.serialized) {
                return false;
            }
            if (this.val$field.get(value) != value) {
                z = true;
            }
            return z;
        }
    }

    public static final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        private Adapter(ObjectConstructor<T> constructor, Map<String, BoundField> boundFields) {
            this.constructor = constructor;
            this.boundFields = boundFields;
        }

        public T read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.NULL) {
                T instance = this.constructor.construct();
                try {
                    in.beginObject();
                    while (in.hasNext()) {
                        BoundField field = (BoundField) this.boundFields.get(in.nextName());
                        if (field != null) {
                            if (field.deserialized) {
                                field.read(in, instance);
                            }
                        }
                        in.skipValue();
                    }
                    in.endObject();
                    return instance;
                } catch (Throwable e) {
                    throw new JsonSyntaxException(e);
                } catch (IllegalAccessException e2) {
                    throw new AssertionError(e2);
                }
            }
            in.nextNull();
            return null;
        }

        public void write(JsonWriter out, T value) throws IOException {
            if (value != null) {
                out.beginObject();
                try {
                    for (BoundField boundField : this.boundFields.values()) {
                        if (boundField.writeField(value)) {
                            out.name(boundField.name);
                            boundField.write(out, value);
                        }
                    }
                    out.endObject();
                    return;
                } catch (IllegalAccessException e) {
                    throw new AssertionError();
                }
            }
            out.nullValue();
        }
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
    }

    public boolean excludeField(Field f, boolean serialize) {
        return excludeField(f, serialize, this.excluder);
    }

    static boolean excludeField(Field f, boolean serialize, Excluder excluder) {
        return (excluder.excludeClass(f.getType(), serialize) || excluder.excludeField(f, serialize)) ? false : true;
    }

    private String getFieldName(Field f) {
        return getFieldName(this.fieldNamingPolicy, f);
    }

    static String getFieldName(FieldNamingStrategy fieldNamingPolicy, Field f) {
        SerializedName serializedName = (SerializedName) f.getAnnotation(SerializedName.class);
        return serializedName != null ? serializedName.value() : fieldNamingPolicy.translateName(f);
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (Object.class.isAssignableFrom(raw)) {
            return new Adapter(getBoundFields(gson, type, raw), null);
        }
        return null;
    }

    private BoundField createBoundField(Gson context, Field field, String name, TypeToken<?> fieldType, boolean serialize, boolean deserialize) {
        return new AnonymousClass1(name, serialize, deserialize, context, field, fieldType, Primitives.isPrimitive(fieldType.getRawType()));
    }

    private TypeAdapter<?> getFieldAdapter(Gson gson, Field field, TypeToken<?> fieldType) {
        JsonAdapter annotation = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        if (annotation != null) {
            TypeAdapter<?> adapter = JsonAdapterAnnotationTypeAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, fieldType, annotation);
            if (adapter != null) {
                return adapter;
            }
        }
        return gson.getAdapter((TypeToken) fieldType);
    }

    private Map<String, BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw) {
        Map<String, BoundField> result = new LinkedHashMap();
        if (raw.isInterface()) {
            return result;
        }
        Type declaredType = type.getType();
        while (raw != Object.class) {
            Field[] arr$ = raw.getDeclaredFields();
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                Field field = arr$[i$];
                boolean serialize = excludeField(field, true);
                boolean deserialize = excludeField(field, false);
                if (!serialize) {
                    if (!deserialize) {
                        continue;
                        i$++;
                    }
                }
                field.setAccessible(true);
                Gson gson = context;
                BoundField boundField = createBoundField(gson, field, getFieldName(field), TypeToken.get(C$Gson$Types.resolve(type.getType(), raw, field.getGenericType())), serialize, deserialize);
                BoundField previous = (BoundField) result.put(boundField.name, boundField);
                if (previous == null) {
                    i$++;
                } else {
                    throw new IllegalArgumentException(declaredType + " declares multiple JSON fields named " + previous.name);
                }
            }
            type = TypeToken.get(C$Gson$Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
            raw = type.getRawType();
        }
        return result;
    }
}
