package retrofit;

import retrofit.client.Response;

public interface Callback {
    void failure(RetrofitError retrofitError);

    void success(Object obj, Response response);
}
