package com.google.gson;

import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.g;
import java.math.BigInteger;

public final class f extends b {
    private static final Class[] gP = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object gQ;

    public f(Boolean bool) {
        setValue(bool);
    }

    public f(Number number) {
        setValue(number);
    }

    public f(String str) {
        setValue(str);
    }

    private static boolean gH(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class cls = obj.getClass();
        for (Class isAssignableFrom : gP) {
            if (isAssignableFrom.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    private static boolean gI(f fVar) {
        if (!(fVar.gQ instanceof Number)) {
            return false;
        }
        Number number = (Number) fVar.gQ;
        boolean z = (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
        return z;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        boolean z2 = false;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        if (this.gQ == null) {
            if (fVar.gQ == null) {
                z2 = true;
            }
            return z2;
        } else if (gI(this) && gI(fVar)) {
            if (gD().longValue() != fVar.gD().longValue()) {
                z = false;
            }
            return z;
        } else if (!(this.gQ instanceof Number) || !(fVar.gQ instanceof Number)) {
            return this.gQ.equals(fVar.gQ);
        } else {
            double doubleValue = gD().doubleValue();
            double doubleValue2 = fVar.gD().doubleValue();
            if (doubleValue == doubleValue2 || (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2))) {
                z2 = true;
            }
            return z2;
        }
    }

    Boolean gA() {
        return (Boolean) this.gQ;
    }

    public boolean gB() {
        return !gz() ? Boolean.parseBoolean(gu()) : gA().booleanValue();
    }

    public boolean gC() {
        return this.gQ instanceof Number;
    }

    public Number gD() {
        return !(this.gQ instanceof String) ? (Number) this.gQ : new LazilyParsedNumber((String) this.gQ);
    }

    public boolean gE() {
        return this.gQ instanceof String;
    }

    public double gF() {
        return !gC() ? Double.parseDouble(gu()) : gD().doubleValue();
    }

    public int gG() {
        return !gC() ? Integer.parseInt(gu()) : gD().intValue();
    }

    public String gu() {
        return !gC() ? !gz() ? (String) this.gQ : gA().toString() : gD().toString();
    }

    public long gv() {
        return !gC() ? Long.parseLong(gu()) : gD().longValue();
    }

    public boolean gz() {
        return this.gQ instanceof Boolean;
    }

    public int hashCode() {
        if (this.gQ == null) {
            return 31;
        }
        long longValue;
        if (gI(this)) {
            longValue = gD().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.gQ instanceof Number)) {
            return this.gQ.hashCode();
        } else {
            longValue = Double.doubleToLongBits(gD().doubleValue());
            return (int) (longValue ^ (longValue >>> 32));
        }
    }

    void setValue(Object obj) {
        boolean z = false;
        if (obj instanceof Character) {
            this.gQ = String.valueOf(((Character) obj).charValue());
            return;
        }
        if ((obj instanceof Number) || gH(obj)) {
            z = true;
        }
        g.fa(z);
        this.gQ = obj;
    }
}
