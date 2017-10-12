package retrofit.client;

public final class Header {
    private final String name;
    private final String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Header header = (Header) o;
        if (this.name != null ? this.name.equals(header.name) : header.name == null) {
            return this.value != null ? this.value.equals(header.value) : header.value == null;
        } else {
            return false;
        }
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
