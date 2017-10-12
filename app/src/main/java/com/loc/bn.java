package com.loc;

import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: Parser */
public class bn {

    /* compiled from: Parser */
    private class a extends DefaultHandler {
        public AmapLoc a;
        final /* synthetic */ bn b;
        private String c;

        private a(bn bnVar) {
            this.b = bnVar;
            this.a = new AmapLoc();
            this.c = "";
        }

        public void characters(char[] cArr, int i, int i2) {
            this.c = String.valueOf(cArr, i, i2);
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) {
            this.c = "";
        }

        public void endElement(String str, String str2, String str3) {
            if (str2.equals("retype")) {
                this.a.g(this.c);
            } else if (str2.equals("rdesc")) {
                this.a.h(this.c);
            } else if (str2.equals("adcode")) {
                this.a.k(this.c);
            } else if (str2.equals("citycode")) {
                this.a.i(this.c);
            } else if (str2.equals("radius")) {
                try {
                    this.a.a(Float.parseFloat(this.c));
                } catch (Exception e) {
                    this.a.a(3891.0f);
                }
            } else if (str2.equals("cenx")) {
                try {
                    this.a.a(Double.parseDouble(this.c));
                } catch (Exception e2) {
                    this.a.a(0.0d);
                }
            } else if (str2.equals("ceny")) {
                try {
                    this.a.b(Double.parseDouble(this.c));
                } catch (Exception e3) {
                    this.a.b(0.0d);
                }
            } else if (str2.equals("desc")) {
                this.a.j(this.c);
            } else if (str2.equals("country")) {
                this.a.l(this.c);
            } else if (str2.equals("province")) {
                this.a.m(this.c);
            } else if (str2.equals("city")) {
                this.a.n(this.c);
            } else if (str2.equals("district")) {
                this.a.o(this.c);
            } else if (str2.equals("road")) {
                this.a.p(this.c);
            } else if (str2.equals("street")) {
                this.a.q(this.c);
            } else if (str2.equals("poiname")) {
                this.a.r(this.c);
            } else if (str2.equals("BIZ")) {
                if (this.a.z() == null) {
                    this.a.a(new JSONObject());
                }
                try {
                    this.a.z().put("BIZ", this.c);
                } catch (Exception e4) {
                }
            } else if (str2.equals("cens")) {
                this.a.s(this.c);
            } else if (str2.equals("pid")) {
                this.a.t(this.c);
            } else if (str2.equals("flr")) {
                this.a.u(this.c);
            } else if (str2.equals("coord")) {
                if (TextUtils.isEmpty(c.h)) {
                    c.h = this.c;
                }
                this.a.v(this.c);
            } else if (str2.equals("mcell")) {
                this.a.w(this.c);
            } else if (!str2.equals("gkeyloc") && str2.equals("gkeygeo")) {
            }
        }
    }

    public AmapLoc a(String str) {
        InputStream inputStream;
        if (!str.contains("SuccessCode")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.reverse();
            try {
                str = o.a(stringBuilder.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.delete(0, stringBuilder.length());
        }
        if (!str.contains("SuccessCode=\"0\"")) {
            try {
            } catch (UnsupportedEncodingException e2) {
                inputStream = null;
            }
        }
        inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        SAXParserFactory newInstance = SAXParserFactory.newInstance();
        DefaultHandler aVar = new a();
        try {
            newInstance.newSAXParser().parse(inputStream, aVar);
            inputStream.close();
            aVar.a.c("network");
            aVar.a.a(br.a());
            return aVar.a;
        } catch (Exception e3) {
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.b(5);
            as.c.append("parser error:" + e3.getMessage());
            amapLoc.b(as.c.toString());
            return amapLoc;
        }
    }

    public AmapLoc b(String str) {
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.b(7);
        try {
            String string;
            String string2;
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status")) {
                if (jSONObject.has("info")) {
                    string = jSONObject.getString("status");
                    string2 = jSONObject.getString("info");
                    if (string.equals("1")) {
                        as.c.append("json is error " + str);
                    }
                    if (string.equals("0")) {
                        if (!(string2.equals("INVALID_USER_KEY") || string2.equals("INSUFFICIENT_PRIVILEGES") || string2.equals("USERKEY_PLAT_NOMATCH") || string2.equals("INVALID_USER_SCODE"))) {
                        }
                        if (!(string2.equals("SERVICE_NOT_EXIST") || string2.equals("SERVICE_RESPONSE_ERROR") || string2.equals("UNKNOWN_ERROR"))) {
                        }
                        if (string2.equals("INVALID_PARAMS")) {
                        }
                        if (string2.equals("OVER_QUOTA")) {
                            as.c.append("auth fail:" + string2);
                        } else {
                            as.c.append("auth fail:" + string2);
                        }
                    }
                    amapLoc.b(as.c.toString());
                    return amapLoc;
                }
            }
            as.c.append("json is error " + str);
            string = jSONObject.getString("status");
            string2 = jSONObject.getString("info");
            if (string.equals("1")) {
                as.c.append("json is error " + str);
            }
            if (string.equals("0")) {
                if (string2.equals("INVALID_PARAMS")) {
                }
                if (string2.equals("OVER_QUOTA")) {
                    as.c.append("auth fail:" + string2);
                } else {
                    as.c.append("auth fail:" + string2);
                }
            }
        } catch (JSONException e) {
            as.c.append("json exception error:" + e.getMessage());
        }
        amapLoc.b(as.c.toString());
        return amapLoc;
    }
}
