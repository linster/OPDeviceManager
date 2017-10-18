package com.google.gson;

import com.google.gson.internal.k;
import com.google.gson.stream.a;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class b {
    public boolean gn() {
        return this instanceof e;
    }

    public boolean go() {
        return this instanceof d;
    }

    public boolean gp() {
        return this instanceof f;
    }

    public boolean gq() {
        return this instanceof g;
    }

    public d gr() {
        if (go()) {
            return (d) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public e gs() {
        if (gn()) {
            return (e) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public f gt() {
        if (gp()) {
            return (f) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public String gu() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long gv() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            Writer stringWriter = new StringWriter();
            a aVar = new a(stringWriter);
            aVar.fw(true);
            k.fc(this, aVar);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
