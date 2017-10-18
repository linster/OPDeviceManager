package com.squareup.okhttp.internal;

import java.io.IOException;
import okio.f;
import okio.k;
import okio.n;

class FaultHidingSink extends f {
    private boolean hasErrors;

    public FaultHidingSink(n nVar) {
        super(nVar);
    }

    public void close() {
        if (!this.hasErrors) {
            try {
                super.close();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    public void flush() {
        if (!this.hasErrors) {
            try {
                super.flush();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    protected void onException(IOException iOException) {
    }

    public void write(k kVar, long j) {
        if (this.hasErrors) {
            kVar.skip(j);
            return;
        }
        try {
            super.write(kVar, j);
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }
}
