package com.loc;

import java.io.IOException;

/* compiled from: OfflineFileCache */
public class bj extends bk<String, be> {
    public bj() {
        super(1048576);
    }

    protected int a(String str, be beVar) {
        int i = 0;
        if (beVar == null) {
            return i;
        }
        try {
            return (int) beVar.g();
        } catch (IOException e) {
            return i;
        }
    }

    protected void a(boolean z, String str, be beVar, be beVar2) {
        if (beVar != null) {
            try {
                beVar.b();
            } catch (IOException e) {
            }
        }
        super.a(z, str, beVar, beVar2);
    }
}
