package retrofit.mime;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class TypedByteArray implements TypedInput, TypedOutput {
    private final byte[] bytes;
    private final String mimeType;

    public TypedByteArray(String str, byte[] bArr) {
        if (str == null) {
            str = "application/unknown";
        }
        if (bArr != null) {
            this.mimeType = str;
            this.bytes = bArr;
            return;
        }
        throw new NullPointerException("bytes");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TypedByteArray typedByteArray = (TypedByteArray) obj;
        return Arrays.equals(this.bytes, typedByteArray.bytes) && this.mimeType.equals(typedByteArray.mimeType);
    }

    public String fileName() {
        return null;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int hashCode() {
        return (this.mimeType.hashCode() * 31) + Arrays.hashCode(this.bytes);
    }

    public InputStream in() {
        return new ByteArrayInputStream(this.bytes);
    }

    public long length() {
        return (long) this.bytes.length;
    }

    public String mimeType() {
        return this.mimeType;
    }

    public String toString() {
        return "TypedByteArray[length=" + length() + "]";
    }

    public void writeTo(OutputStream outputStream) {
        outputStream.write(this.bytes);
    }
}
