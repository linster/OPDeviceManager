package retrofit;

import java.io.IOException;
import java.io.InputStream;
import retrofit.mime.TypedInput;

class ExceptionCatchingTypedInput implements TypedInput {
    private final TypedInput delegate;
    private final ExceptionCatchingInputStream delegateStream;

    class ExceptionCatchingInputStream extends InputStream {
        private final InputStream delegate;
        private IOException thrownException;

        ExceptionCatchingInputStream(InputStream inputStream) {
            this.delegate = inputStream;
        }

        public int available() {
            try {
                return this.delegate.available();
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public void close() {
            try {
                this.delegate.close();
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public synchronized void mark(int i) {
            this.delegate.mark(i);
        }

        public boolean markSupported() {
            return this.delegate.markSupported();
        }

        public int read() {
            try {
                return this.delegate.read();
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public int read(byte[] bArr) {
            try {
                return this.delegate.read(bArr);
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public int read(byte[] bArr, int i, int i2) {
            try {
                return this.delegate.read(bArr, i, i2);
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public synchronized void reset() {
            try {
                this.delegate.reset();
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }

        public long skip(long j) {
            try {
                return this.delegate.skip(j);
            } catch (IOException e) {
                this.thrownException = e;
                throw e;
            }
        }
    }

    ExceptionCatchingTypedInput(TypedInput typedInput) {
        this.delegate = typedInput;
        this.delegateStream = new ExceptionCatchingInputStream(typedInput.in());
    }

    IOException getThrownException() {
        return this.delegateStream.thrownException;
    }

    public InputStream in() {
        return this.delegateStream;
    }

    public long length() {
        return this.delegate.length();
    }

    public String mimeType() {
        return this.delegate.mimeType();
    }

    boolean threwException() {
        return this.delegateStream.thrownException != null;
    }
}
