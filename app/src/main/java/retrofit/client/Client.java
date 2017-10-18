package retrofit.client;

public interface Client {

    public interface Provider {
        Client get();
    }

    Response execute(Request request);
}
