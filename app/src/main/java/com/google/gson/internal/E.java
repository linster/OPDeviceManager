package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class E {
    public static E fq() {
        Method declaredMethod;
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            return new p(cls.getMethod("allocateInstance", new Class[]{Class.class}), declaredField.get(null));
        } catch (Exception e) {
            try {
                declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new G(declaredMethod2, intValue);
            } catch (Exception e2) {
                try {
                    declaredMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod.setAccessible(true);
                    return new e(declaredMethod);
                } catch (Exception e3) {
                    return new l();
                }
            }
        }
    }

    public abstract Object eX(Class cls);
}
