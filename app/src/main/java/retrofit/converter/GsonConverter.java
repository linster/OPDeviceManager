package retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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
    private final Gson gson;

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        public String fileName() {
            return null;
        }

        public String mimeType() {
            return this.mimeType;
        }

        public long length() {
            return (long) this.jsonBytes.length;
        }

        public void writeTo(OutputStream out) throws IOException {
            out.write(this.jsonBytes);
        }
    }

    public GsonConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    public GsonConverter(Gson gson, String charset) {
        this.gson = gson;
        this.charset = charset;
    }

    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        Throwable e;
        Reader reader;
        Throwable th;
        Throwable e2;
        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader inputStreamReader = null;
        try {
            Reader isr = new InputStreamReader(body.in(), charset);
            try {
                try {
                    Object fromJson = this.gson.fromJson(isr, type);
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e3) {
                        }
                    }
                    return fromJson;
                } catch (IOException e4) {
                    e = e4;
                    reader = isr;
                    try {
                        throw new ConversionException(e);
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                } catch (JsonParseException e6) {
                    e2 = e6;
                    reader = isr;
                    throw new ConversionException(e2);
                } catch (Throwable th3) {
                    th = th3;
                    reader = isr;
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e = e7;
                reader = isr;
                throw new ConversionException(e);
            } catch (JsonParseException e8) {
                e2 = e8;
                reader = isr;
                throw new ConversionException(e2);
            } catch (Throwable th4) {
                th = th4;
                reader = isr;
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                throw th;
            }
        } catch (IOException e9) {
            e = e9;
            throw new ConversionException(e);
        } catch (JsonParseException e10) {
            e2 = e10;
            throw new ConversionException(e2);
        }
    }

    public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(this.gson.toJson(object).getBytes(this.charset), this.charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
