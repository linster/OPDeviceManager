package okio;

import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ByteString implements Serializable {
    private static final long serialVersionUID = 1;
    static final char[] uC = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final ByteString uD = Ar(new byte[0]);
    final byte[] data;
    transient int uE;
    transient String uF;

    ByteString(byte[] bArr) {
        this.data = bArr;
    }

    public static ByteString Ar(byte... bArr) {
        if (bArr != null) {
            return new ByteString((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString As(String str) {
        if (str != null) {
            ByteString byteString = new ByteString(str.getBytes(r.UTF_8));
            byteString.uF = str;
            return byteString;
        }
        throw new IllegalArgumentException("s == null");
    }

    private ByteString Aw(String str) {
        try {
            return Ar(MessageDigest.getInstance(str).digest(this.data));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static ByteString Ax(String str) {
        if (str != null) {
            byte[] Bp = D.Bp(str);
            return Bp == null ? null : new ByteString(Bp);
        } else {
            throw new IllegalArgumentException("base64 == null");
        }
    }

    public static ByteString Az(InputStream inputStream, int i) {
        int i2 = 0;
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (i >= 0) {
            byte[] bArr = new byte[i];
            while (i2 < i) {
                int read = inputStream.read(bArr, i2, i - i2);
                if (read != -1) {
                    i2 += read;
                } else {
                    throw new EOFException();
                }
            }
            return new ByteString(bArr);
        } else {
            throw new IllegalArgumentException("byteCount < 0: " + i);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        ByteString Az = Az(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = ByteString.class.getDeclaredField("data");
            declaredField.setAccessible(true);
            declaredField.set(this, Az.data);
        } catch (NoSuchFieldException e) {
            throw new AssertionError();
        } catch (IllegalAccessException e2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    public ByteString AA() {
        for (int i = 0; i < this.data.length; i++) {
            byte b = this.data[i];
            if (b >= (byte) 65 && b <= (byte) 90) {
                byte[] bArr = (byte[]) this.data.clone();
                int i2 = i + 1;
                bArr[i] = (byte) ((byte) (b + 32));
                for (i = i2; i < bArr.length; i++) {
                    byte b2 = bArr[i];
                    if (b2 >= (byte) 65 && b2 <= (byte) 90) {
                        bArr[i] = (byte) ((byte) (b2 + 32));
                    }
                }
                return new ByteString(bArr);
            }
        }
        return this;
    }

    public byte[] AB() {
        return (byte[]) this.data.clone();
    }

    void AC(k kVar) {
        kVar.write(this.data, 0, this.data.length);
    }

    public boolean AD(int i, byte[] bArr, int i2, int i3) {
        return i <= this.data.length - i3 && i2 <= bArr.length - i3 && r.Bm(this.data, i, bArr, i2, i3);
    }

    public String At() {
        String str = this.uF;
        if (str != null) {
            return str;
        }
        str = new String(this.data, r.UTF_8);
        this.uF = str;
        return str;
    }

    public String Au() {
        return D.Bq(this.data);
    }

    public ByteString Av() {
        return Aw("MD5");
    }

    public String Ay() {
        int i = 0;
        char[] cArr = new char[(this.data.length * 2)];
        byte[] bArr = this.data;
        int length = bArr.length;
        int i2 = 0;
        while (i < length) {
            byte b = bArr[i];
            int i3 = i2 + 1;
            cArr[i2] = (char) uC[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = (char) uC[b & 15];
            i++;
        }
        return new String(cArr);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        boolean z = (obj instanceof ByteString) && ((ByteString) obj).size() == this.data.length && ((ByteString) obj).AD(0, this.data, 0, this.data.length);
        return z;
    }

    public byte getByte(int i) {
        return this.data[i];
    }

    public int hashCode() {
        int i = this.uE;
        if (i != 0) {
            return i;
        }
        i = Arrays.hashCode(this.data);
        this.uE = i;
        return i;
    }

    public int size() {
        return this.data.length;
    }

    public String toString() {
        if (this.data.length == 0) {
            return "ByteString[size=0]";
        }
        if (this.data.length > 16) {
            return String.format("ByteString[size=%s md5=%s]", new Object[]{Integer.valueOf(this.data.length), Av().Ay()});
        }
        return String.format("ByteString[size=%s data=%s]", new Object[]{Integer.valueOf(this.data.length), Ay()});
    }
}
