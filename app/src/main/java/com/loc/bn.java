package com.loc;

import java.io.IOException;

public class bn extends bH {
    public bn() {
        super(1048576);
    }

    protected int vp(String str, j jVar) {
        int i = 0;
        if (jVar == null) {
            return i;
        }
        try {
            return (int) jVar.lt();
        } catch (IOException e) {
            return i;
        }
    }

    protected void vq(boolean z, String str, j jVar, j jVar2) {
        if (jVar != null) {
            try {
                jVar.ln();
            } catch (IOException e) {
            }
        }
        super.vs(z, str, jVar, jVar2);
    }

    protected /* bridge */ /* synthetic */ int vr(Object obj, Object obj2) {
        return vp((String) obj, (j) obj2);
    }

    protected /* bridge */ /* synthetic */ void vs(boolean z, Object obj, Object obj2, Object obj3) {
        vq(z, (String) obj, (j) obj2, (j) obj3);
    }
}
