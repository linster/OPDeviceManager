package com.loc;

public class U {
    public static int nC(String str, String str2) {
        int i = 0;
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int min = Math.min(split.length, split2.length);
            for (int i2 = 0; i2 < min; i2++) {
                i = split[i2].length() - split2[i2].length();
                if (i != 0) {
                    break;
                }
                i = split[i2].compareTo(split2[i2]);
                if (i != 0) {
                    break;
                }
            }
            if (i == 0) {
                i = split.length - split2.length;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
