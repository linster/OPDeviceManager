package com.google.gson;

import java.lang.reflect.Field;

public enum FieldNamingPolicy implements h {
    IDENTITY {
        public String gO(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE {
        public String gO(Field field) {
            return FieldNamingPolicy.gK(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String gO(Field field) {
            return FieldNamingPolicy.gK(FieldNamingPolicy.gJ(field.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public String gO(Field field) {
            return FieldNamingPolicy.gJ(field.getName(), "_").toLowerCase();
        }
    },
    LOWER_CASE_WITH_DASHES {
        public String gO(Field field) {
            return FieldNamingPolicy.gJ(field.getName(), "-").toLowerCase();
        }
    };

    private static String gJ(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && stringBuilder.length() != 0) {
                stringBuilder.append(str2);
            }
            stringBuilder.append(charAt);
        }
        return stringBuilder.toString();
    }

    private static String gK(String str) {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        char charAt = str.charAt(0);
        while (i < str.length() - 1 && !Character.isLetter(charAt)) {
            stringBuilder.append(charAt);
            i++;
            charAt = str.charAt(i);
        }
        return i != str.length() ? Character.isUpperCase(charAt) ? str : stringBuilder.append(gL(Character.toUpperCase(charAt), str, i + 1)).toString() : stringBuilder.toString();
    }

    private static String gL(char c, String str, int i) {
        return i >= str.length() ? String.valueOf(c) : c + str.substring(i);
    }
}
