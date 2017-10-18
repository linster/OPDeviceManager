package com.loc;

import java.util.List;

/* compiled from: Unknown */
public final class W {
    private double f = 0.0d;
    private boolean mu = false;
    private String mv = "";
    private String mw = "";
    private boolean mx = false;
    private double my = 0.0d;

    protected W(List list, String str, String str2, String str3) {
        this.mv = str;
        this.mw = str3;
        nD();
    }

    private void nD() {
        int i;
        int i2;
        String[] split;
        int i3 = 0;
        String str = this.mw;
        if (str != null && str.length() > 8) {
            i = 0;
            for (i2 = 1; i2 < str.length() - 3; i2++) {
                i ^= str.charAt(i2);
            }
            if (Integer.toHexString(i).equalsIgnoreCase(str.substring(str.length() - 2, str.length()))) {
                boolean z = true;
                if (i2 != 0) {
                    str = this.mw.substring(0, this.mw.length() - 3);
                    i = 0;
                    for (i2 = 0; i2 < str.length(); i2++) {
                        if (str.charAt(i2) != ',') {
                            i++;
                        }
                    }
                    split = str.split(",", i + 1);
                    if (split.length < 6) {
                        return;
                    }
                    if (!(split[2].equals("") || split[split.length - 3].equals("") || split[split.length - 2].equals("") || split[split.length - 1].equals(""))) {
                        Integer.valueOf(split[2]).intValue();
                        this.my = Double.valueOf(split[split.length - 3]).doubleValue();
                        this.f = Double.valueOf(split[split.length - 2]).doubleValue();
                        this.mx = true;
                    }
                }
                if (this.mv != null) {
                    if (this.mv.length() >= 0 && this.mv.contains("GPGGA")) {
                        split = this.mv.split(",");
                        if (split.length == 15 && split[2] != null && split[2].length() > 0 && split[4] != null && split[4].length() > 0 && Integer.parseInt(split[7]) >= 5 && Double.parseDouble(split[8]) <= 2.7d) {
                            i3 = 1;
                        }
                    }
                }
                this.mu = this.mx & i3;
            }
        }
        i2 = 0;
        if (i2 != 0) {
            str = this.mw.substring(0, this.mw.length() - 3);
            i = 0;
            for (i2 = 0; i2 < str.length(); i2++) {
                if (str.charAt(i2) != ',') {
                    i++;
                }
            }
            split = str.split(",", i + 1);
            if (split.length < 6) {
                Integer.valueOf(split[2]).intValue();
                this.my = Double.valueOf(split[split.length - 3]).doubleValue();
                this.f = Double.valueOf(split[split.length - 2]).doubleValue();
                this.mx = true;
            } else {
                return;
            }
        }
        try {
            if (this.mv != null) {
                split = this.mv.split(",");
                i3 = 1;
            }
        } catch (Exception e) {
        }
        this.mu = this.mx & i3;
    }

    protected final boolean nE() {
        return this.mu;
    }

    protected final double nF() {
        return this.my;
    }

    protected final double nG() {
        return this.f;
    }
}
