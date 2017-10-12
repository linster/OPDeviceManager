package com.loc;

import android.content.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* compiled from: InstanceFactory */
public class ac {
    public static <T> T a(Context context, s sVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws i {
        af a;
        try {
            a = af.a(context, sVar, ae.a(context, sVar.a(), sVar.b()), ae.a(context), null, context.getClassLoader());
        } catch (Throwable th) {
            th.printStackTrace();
            a = null;
        }
        if (a != null) {
            try {
                if (a.a() && a.a) {
                    Class loadClass = a.loadClass(str);
                    if (loadClass != null) {
                        return loadClass.getConstructor(clsArr).newInstance(objArr);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (IllegalArgumentException e5) {
                e5.printStackTrace();
            } catch (InvocationTargetException e6) {
                e6.printStackTrace();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        try {
            Constructor constructor = cls.getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (NoSuchMethodException e22) {
            e22.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        } catch (InstantiationException e32) {
            e32.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        } catch (IllegalAccessException e42) {
            e42.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        } catch (IllegalArgumentException e52) {
            e52.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        } catch (InvocationTargetException e62) {
            e62.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        } catch (Throwable th22) {
            th22.printStackTrace();
            throw new i("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
        }
    }
}
