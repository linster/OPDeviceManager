package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private final List<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList();
    }

    public void add(JsonElement element) {
        if (element == null) {
            element = JsonNull.INSTANCE;
        }
        this.elements.add(element);
    }

    public Iterator<JsonElement> iterator() {
        return this.elements.iterator();
    }

    public String getAsString() {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsString();
        }
        throw new IllegalStateException();
    }

    public long getAsLong() {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsLong();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object o) {
        if (o != this) {
            if (!(o instanceof JsonArray)) {
                return false;
            }
            if (!((JsonArray) o).elements.equals(this.elements)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.elements.hashCode();
    }
}
