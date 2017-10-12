package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = null;
    private final Gson gson;

    /* renamed from: com.google.gson.internal.bind.ObjectTypeAdapter.2 */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken = null;

        static {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.gson.internal.bind.ObjectTypeAdapter.2.<clinit>():void
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
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x004a }
            r1 = com.google.gson.stream.JsonToken.BEGIN_ARRAY;	 Catch:{ NoSuchFieldError -> 0x004a }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x004a }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x004a }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r1 = com.google.gson.stream.JsonToken.BEGIN_OBJECT;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0046 }
            r1 = com.google.gson.stream.JsonToken.STRING;	 Catch:{ NoSuchFieldError -> 0x0046 }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x0046 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0046 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0044 }
            r1 = com.google.gson.stream.JsonToken.NUMBER;	 Catch:{ NoSuchFieldError -> 0x0044 }
            r2 = 4;	 Catch:{ NoSuchFieldError -> 0x0044 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0044 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0042 }
            r1 = com.google.gson.stream.JsonToken.BOOLEAN;	 Catch:{ NoSuchFieldError -> 0x0042 }
            r2 = 5;	 Catch:{ NoSuchFieldError -> 0x0042 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0042 }
            r0 = $SwitchMap$com$google$gson$stream$JsonToken;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.google.gson.stream.JsonToken.NULL;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 6;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r0 = move-exception;
            goto L_0x003f;
        L_0x0042:
            r0 = move-exception;
            goto L_0x0036;
        L_0x0044:
            r0 = move-exception;
            goto L_0x002d;
        L_0x0046:
            r0 = move-exception;
            goto L_0x0024;
        L_0x0048:
            r0 = move-exception;
            goto L_0x001b;
        L_0x004a:
            r0 = move-exception;
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.ObjectTypeAdapter.2.<clinit>():void");
        }
    }

    /* synthetic */ ObjectTypeAdapter(Gson x0, AnonymousClass1 x1) {
        this(x0);
    }

    static {
        FACTORY = new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() != Object.class) {
                    return null;
                }
                return new ObjectTypeAdapter(gson, null);
            }
        };
    }

    private ObjectTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    public Object read(JsonReader in) throws IOException {
        switch (AnonymousClass2.$SwitchMap$com$google$gson$stream$JsonToken[in.peek().ordinal()]) {
            case 1:
                List<Object> list = new ArrayList();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(read(in));
                }
                in.endArray();
                return list;
            case 2:
                Map<String, Object> map = new LinkedTreeMap();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), read(in));
                }
                in.endObject();
                return map;
            case 3:
                return in.nextString();
            case 4:
                return Double.valueOf(in.nextDouble());
            case 5:
                return Boolean.valueOf(in.nextBoolean());
            case 6:
                in.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void write(JsonWriter out, Object value) throws IOException {
        if (value != null) {
            TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
            if (typeAdapter instanceof ObjectTypeAdapter) {
                out.beginObject();
                out.endObject();
                return;
            }
            typeAdapter.write(out, value);
            return;
        }
        out.nullValue();
    }
}
