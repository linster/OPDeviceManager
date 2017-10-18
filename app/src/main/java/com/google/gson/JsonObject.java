package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map.Entry;
import java.util.Set;

public final class JsonObject extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members;

    public JsonObject() {
        this.members = new LinkedTreeMap();
    }

    public void add(String property, JsonElement value) {
        if (value == null) {
            value = JsonNull.INSTANCE;
        }
        this.members.put(property, value);
    }

    public Set<Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public boolean has(String memberName) {
        return this.members.containsKey(memberName);
    }

    public JsonElement get(String memberName) {
        return (JsonElement) this.members.get(memberName);
    }

    public boolean equals(Object o) {
        if (o != this) {
            if (!(o instanceof JsonObject)) {
                return false;
            }
            if (!((JsonObject) o).members.equals(this.members)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.members.hashCode();
    }
}
