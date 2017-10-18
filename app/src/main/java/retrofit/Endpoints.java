package retrofit;

public final class Endpoints {
    private static final String DEFAULT_NAME = "default";

    class FixedEndpoint implements Endpoint {
        private final String apiUrl;
        private final String name;

        FixedEndpoint(String str, String str2) {
            this.apiUrl = str;
            this.name = str2;
        }

        public String getName() {
            return this.name;
        }

        public String getUrl() {
            return this.apiUrl;
        }
    }

    private Endpoints() {
    }

    public static Endpoint newFixedEndpoint(String str) {
        return new FixedEndpoint(str, DEFAULT_NAME);
    }

    public static Endpoint newFixedEndpoint(String str, String str2) {
        return new FixedEndpoint(str, str2);
    }
}
