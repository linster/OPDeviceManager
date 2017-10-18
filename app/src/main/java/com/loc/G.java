package com.loc;

import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class G extends DefaultHandler {
    final /* synthetic */ M lA;
    public AmapLoc ly;
    private String lz;

    private G(M m) {
        this.lA = m;
        this.ly = new AmapLoc();
        this.lz = "";
    }

    public void characters(char[] cArr, int i, int i2) {
        this.lz = String.valueOf(cArr, i, i2);
    }

    public void endElement(String str, String str2, String str3) {
        if (str2.equals("retype")) {
            this.ly.yZ(this.lz);
        } else if (str2.equals("rdesc")) {
            this.ly.zb(this.lz);
        } else if (str2.equals("adcode")) {
            this.ly.zh(this.lz);
        } else if (str2.equals("citycode")) {
            this.ly.zd(this.lz);
        } else if (str2.equals("radius")) {
            try {
                this.ly.yO(Float.parseFloat(this.lz));
            } catch (Exception e) {
                this.ly.yO(3891.0f);
            }
        } else if (str2.equals("cenx")) {
            try {
                this.ly.yH(Double.parseDouble(this.lz));
            } catch (Exception e2) {
                this.ly.yH(0.0d);
            }
        } else if (str2.equals("ceny")) {
            try {
                this.ly.yK(Double.parseDouble(this.lz));
            } catch (Exception e3) {
                this.ly.yK(0.0d);
            }
        } else if (str2.equals("desc")) {
            this.ly.zf(this.lz);
        } else if (str2.equals("country")) {
            this.ly.zj(this.lz);
        } else if (str2.equals("province")) {
            this.ly.zl(this.lz);
        } else if (str2.equals("city")) {
            this.ly.zn(this.lz);
        } else if (str2.equals("district")) {
            this.ly.zp(this.lz);
        } else if (str2.equals("road")) {
            this.ly.zr(this.lz);
        } else if (str2.equals("street")) {
            this.ly.zs(this.lz);
        } else if (str2.equals("poiname")) {
            this.ly.zu(this.lz);
        } else if (str2.equals("BIZ")) {
            if (this.ly.zE() == null) {
                this.ly.zF(new JSONObject());
            }
            try {
                this.ly.zE().put("BIZ", this.lz);
            } catch (Exception e4) {
            }
        } else if (str2.equals("cens")) {
            this.ly.zv(this.lz);
        } else if (str2.equals("pid")) {
            this.ly.zx(this.lz);
        } else if (str2.equals("flr")) {
            this.ly.zy(this.lz);
        } else if (str2.equals("coord")) {
            if (TextUtils.isEmpty(H.lJ)) {
                H.lJ = this.lz;
            }
            this.ly.zA(this.lz);
        } else if (str2.equals("mcell")) {
            this.ly.zD(this.lz);
        } else if (!str2.equals("gkeyloc") && str2.equals("gkeygeo")) {
        }
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) {
        this.lz = "";
    }
}
