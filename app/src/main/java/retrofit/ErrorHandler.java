package retrofit;

public interface ErrorHandler {
    public static final ErrorHandler DEFAULT;

    Throwable handleError(RetrofitError retrofitError);

    static {
        DEFAULT = new ErrorHandler() {
            public Throwable handleError(RetrofitError cause) {
                return cause;
            }
        };
    }
}
