package com.google.gson.internal;

import com.google.gson.b;
import com.google.gson.internal.a.ac;
import com.google.gson.stream.a;
import java.io.Writer;

public final class k {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.gson.b fb(com.google.gson.stream.b r3) {
        /*
        r1 = 0;
        r2 = 1;
        r3.df();	 Catch:{ EOFException -> 0x000e, MalformedJsonException -> 0x001b, IOException -> 0x0022, NumberFormatException -> 0x0029 }
        r0 = com.google.gson.internal.a.ac.eC;	 Catch:{ EOFException -> 0x0030, MalformedJsonException -> 0x001b, IOException -> 0x0022, NumberFormatException -> 0x0029 }
        r0 = r0.cR(r3);	 Catch:{ EOFException -> 0x0030, MalformedJsonException -> 0x001b, IOException -> 0x0022, NumberFormatException -> 0x0029 }
        r0 = (com.google.gson.b) r0;	 Catch:{ EOFException -> 0x0030, MalformedJsonException -> 0x001b, IOException -> 0x0022, NumberFormatException -> 0x0029 }
        return r0;
    L_0x000e:
        r0 = move-exception;
        r1 = r2;
    L_0x0010:
        if (r1 != 0) goto L_0x0018;
    L_0x0012:
        r1 = new com.google.gson.JsonSyntaxException;
        r1.<init>(r0);
        throw r1;
    L_0x0018:
        r0 = com.google.gson.g.gR;
        return r0;
    L_0x001b:
        r0 = move-exception;
        r1 = new com.google.gson.JsonSyntaxException;
        r1.<init>(r0);
        throw r1;
    L_0x0022:
        r0 = move-exception;
        r1 = new com.google.gson.JsonIOException;
        r1.<init>(r0);
        throw r1;
    L_0x0029:
        r0 = move-exception;
        r1 = new com.google.gson.JsonSyntaxException;
        r1.<init>(r0);
        throw r1;
    L_0x0030:
        r0 = move-exception;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.k.fb(com.google.gson.stream.b):com.google.gson.b");
    }

    public static void fc(b bVar, a aVar) {
        ac.eC.cT(aVar, bVar);
    }

    public static Writer fd(Appendable appendable) {
        return !(appendable instanceof Writer) ? new H(appendable) : (Writer) appendable;
    }
}
