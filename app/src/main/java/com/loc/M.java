package com.loc;

import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.helpers.DefaultHandler;

public class M {
    public AmapLoc nh(String str) {
        InputStream inputStream;
        if (!str.contains("SuccessCode")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.reverse();
            try {
                str = aG.ro(stringBuilder.toString().getBytes("UTF-8"));
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
        DefaultHandler g = new G();
        try {
            newInstance.newSAXParser().parse(inputStream, g);
            inputStream.close();
            g.ly.yF("network");
            g.ly.yV(bq.vL());
            return g.ly;
        } catch (Exception e3) {
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.yz(5);
            e.jI.append("parser error:" + e3.getMessage());
            amapLoc.yD(e.jI.toString());
            return amapLoc;
        }
    }

    public AmapLoc ni(String str) {
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.yz(7);
        try {
            String string;
            String string2;
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status")) {
                if (jSONObject.has("info")) {
                    string = jSONObject.getString("status");
                    string2 = jSONObject.getString("info");
                    if (string.equals("1")) {
                        e.jI.append("json is error " + str);
                    }
                    if (string.equals("0")) {
                        if (!(string2.equals("INVALID_USER_KEY") || string2.equals("INSUFFICIENT_PRIVILEGES") || string2.equals("USERKEY_PLAT_NOMATCH") || string2.equals("INVALID_USER_SCODE"))) {
                        }
                        if (!(string2.equals("SERVICE_NOT_EXIST") || string2.equals("SERVICE_RESPONSE_ERROR") || string2.equals("UNKNOWN_ERROR"))) {
                        }
                        if (string2.equals("INVALID_PARAMS")) {
                        }
                        if (string2.equals("OVER_QUOTA")) {
                            e.jI.append("auth fail:" + string2);
                        } else {
                            e.jI.append("auth fail:" + string2);
                        }
                    }
                    amapLoc.yD(e.jI.toString());
                    return amapLoc;
                }
            }
            e.jI.append("json is error " + str);
            string = jSONObject.getString("status");
            string2 = jSONObject.getString("info");
            if (string.equals("1")) {
                e.jI.append("json is error " + str);
            }
            if (string.equals("0")) {
                if (string2.equals("INVALID_PARAMS")) {
                }
                if (string2.equals("OVER_QUOTA")) {
                    e.jI.append("auth fail:" + string2);
                } else {
                    e.jI.append("auth fail:" + string2);
                }
            }
        } catch (JSONException e) {
            e.jI.append("json exception error:" + e.getMessage());
        }
        amapLoc.yD(e.jI.toString());
        return amapLoc;
    }
}
