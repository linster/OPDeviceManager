package retrofit.client;

public final class Header {
    private final String name;
    private final String value;

    public Header(String str, String str2) {
        this.name = str;
        this.value = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Header header = (Header) obj;
        return (this.name != null ? this.name.equals(header.name) : header.name == null) ? this.value != null ? this.value.equals(header.value) : header.value == null : false;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.name == null ? 0 : this.name.hashCode()) * 31;
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return (this.name == null ? "" : this.name) + ": " + (this.value == null ? "" : this.value);
    }
}
