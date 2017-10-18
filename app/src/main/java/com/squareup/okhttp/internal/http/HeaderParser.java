package com.squareup.okhttp.internal.http;

public final class HeaderParser {
    private HeaderParser() {
    }

    public static int parseSeconds(String str, int i) {
        int i2 = 1;
        try {
            long parseLong = Long.parseLong(str);
            if ((parseLong <= 2147483647L ? 1 : 0) == 0) {
                return Integer.MAX_VALUE;
            }
            if (parseLong < 0) {
                i2 = 0;
            }
            return i2 == 0 ? 0 : (int) parseLong;
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static int skipUntil(String str, int i, String str2) {
        while (i < str.length() && str2.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        return i;
    }

    public static int skipWhitespace(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                if (charAt != '\t') {
                    break;
                }
            }
            i++;
        }
        return i;
    }
}
