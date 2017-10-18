package com.google.gson.internal;

class v implements CharSequence {
    char[] chars;

    v() {
    }

    public char charAt(int i) {
        return this.chars[i];
    }

    public int length() {
        return this.chars.length;
    }

    public CharSequence subSequence(int i, int i2) {
        return new String(this.chars, i, i2 - i);
    }
}
