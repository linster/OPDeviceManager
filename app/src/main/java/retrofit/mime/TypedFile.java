package retrofit.mime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TypedFile implements TypedInput, TypedOutput {
    private static final int BUFFER_SIZE = 4096;
    private final File file;
    private final String mimeType;

    public TypedFile(String str, File file) {
        if (str == null) {
            throw new NullPointerException("mimeType");
        } else if (file != null) {
            this.mimeType = str;
            this.file = file;
        } else {
            throw new NullPointerException("file");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypedFile)) {
            return false;
        }
        return this.file.equals(((TypedFile) obj).file);
    }

    public File file() {
        return this.file;
    }

    public String fileName() {
        return this.file.getName();
    }

    public int hashCode() {
        return this.file.hashCode();
    }

    public InputStream in() {
        return new FileInputStream(this.file);
    }

    public long length() {
        return this.file.length();
    }

    public String mimeType() {
        return this.mimeType;
    }

    public void moveTo(TypedFile typedFile) {
        if (!mimeType().equals(typedFile.mimeType())) {
            throw new IOException("Type mismatch.");
        } else if (!this.file.renameTo(typedFile.file())) {
            throw new IOException("Rename failed!");
        }
    }

    public String toString() {
        return this.file.getAbsolutePath() + " (" + mimeType() + ")";
    }

    public void writeTo(OutputStream outputStream) {
        byte[] bArr = new byte[BUFFER_SIZE];
        FileInputStream fileInputStream = new FileInputStream(this.file);
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read == -1) {
                break;
            }
            try {
                outputStream.write(bArr, 0, read);
            } finally {
                fileInputStream.close();
            }
        }
    }
}
