package com.loc;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class bm extends DefaultHandler {
    bm() {
    }

    public void characters(char[] cArr, int i, int i2) {
        if (bK.tF) {
            bK.a = new String(cArr, i, i2);
        }
    }

    public void endElement(String str, String str2, String str3) {
        bK.tF = false;
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) {
        if (str2.equals("string") && "UTDID".equals(attributes.getValue("name"))) {
            bK.tF = true;
        }
    }
}
