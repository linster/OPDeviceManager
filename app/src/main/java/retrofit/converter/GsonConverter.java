package retrofit.converter;

import com.google.gson.JsonParseException;
import com.google.gson.i;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class GsonConverter implements Converter {
    private String charset;
    private final i gson;

    class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] bArr, String str) {
            this.jsonBytes = bArr;
            this.mimeType = "application/json; charset=" + str;
        }

        public String fileName() {
            return null;
        }

        public long length() {
            return (long) this.jsonBytes.length;
        }

        public String mimeType() {
            return this.mimeType;
        }

        public void writeTo(OutputStream outputStream) {
            outputStream.write(this.jsonBytes);
        }
    }

    public GsonConverter(i iVar) {
        this(iVar, "UTF-8");
    }

    public GsonConverter(i iVar, String str) {
        this.gson = iVar;
        this.charset = str;
    }

    public Object fromBody(TypedInput typedInput, Type type) {
        Throwable e;
        InputStreamReader inputStreamReader = null;
        String str = this.charset;
        if (typedInput.mimeType() != null) {
            str = MimeUtil.parseCharset(typedInput.mimeType(), str);
        }
        Reader inputStreamReader2;
        try {
            inputStreamReader2 = new InputStreamReader(typedInput.in(), str);
            try {
                Object he = this.gson.he(inputStreamReader2, type);
                if (inputStreamReader2 != null) {
                    try {
                        inputStreamReader2.close();
                    } catch (IOException e2) {
                    }
                }
                return he;
            } catch (IOException e3) {
                e = e3;
                try {
                    throw new ConversionException(e);
                } catch (Throwable th) {
                    e = th;
                    inputStreamReader = inputStreamReader2;
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw e;
                }
            } catch (JsonParseException e5) {
                e = e5;
                Reader reader = inputStreamReader2;
                try {
                    throw new ConversionException(e);
                } catch (Throwable th2) {
                    e = th2;
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    throw e;
                }
            }
        } catch (IOException e6) {
            e = e6;
            inputStreamReader2 = null;
            throw new ConversionException(e);
        } catch (JsonParseException e7) {
            e = e7;
            throw new ConversionException(e);
        }
    }

    public TypedOutput toBody(Object obj) {
        try {
            return new JsonTypedOutput(this.gson.gW(obj).getBytes(this.charset), this.charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
