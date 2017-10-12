package com.squareup.okhttp.internal.http;

public final class HeaderParser {
    public static int skipUntil(String input, int pos, String characters) {
        while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
            pos++;
        }
        return pos;
    }

    public static int skipWhitespace(String input, int pos) {
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c != ' ') {
                if (c != '\t') {
                    break;
                }
            }
            pos++;
        }
        return pos;
    }

    public static int parseSeconds(String value, int defaultValue) {
        int i = 1;
        try {
            int i2;
            long seconds = Long.parseLong(value);
            if (seconds <= 2147483647L) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (i2 == 0) {
                return Integer.MAX_VALUE;
            }
            if (seconds < 0) {
                i = 0;
            }
            if (i == 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private HeaderParser() {
    }
}
