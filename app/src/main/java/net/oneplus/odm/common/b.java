package net.oneplus.odm.common;

import java.util.Random;

public class b {
    private static final char[] T = "0123456789abcdefghigklmnopqrstuvwxyz";
    private static final char[] U = "0123456789";

    public static String ar(String str) {
        String[] split = str.split(",");
        int aw = aw(1, 5);
        int aw2 = aw(1, 5);
        int aw3 = aw(1, 5);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String trim = split[i].trim();
            if (trim.length() != 0) {
                trim = as((at(trim + av(aw), aw2) + aw) + aw2, aw3);
                if (i > 0) {
                    stringBuilder.append('\u0003');
                }
                stringBuilder.append(trim);
            }
        }
        stringBuilder.append(aw3);
        return stringBuilder.toString();
    }

    private static String as(String str, int i) {
        char[] toCharArray = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : toCharArray) {
            stringBuilder.append((char) ((Integer.parseInt(c + "") + i) + 97));
        }
        return stringBuilder.toString();
    }

    private static String at(String str, int i) {
        char[] toCharArray = str.toCharArray();
        int length = toCharArray.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 + i;
            if (i3 < 0) {
                i3 += length;
            } else if (i3 >= length) {
                i3 -= length;
            }
            stringBuilder.append(toCharArray[i3]);
        }
        return stringBuilder.toString();
    }

    private static String au(int i, char[] cArr) {
        if (i < 1) {
            throw new IllegalArgumentException("the length of string must greater then zero");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append(cArr[aw(0, cArr.length)]);
        }
        return stringBuilder.toString();
    }

    private static String av(int i) {
        return au(i, U);
    }

    private static int aw(int i, int i2) {
        return i == i2 ? i : new Random().nextInt(i2 - i) + i;
    }
}
