package retrofit;

import java.util.concurrent.Executor;

abstract class CallbackRunnable implements Runnable {
    private final Callback callback;
    private final Executor callbackExecutor;
    private final ErrorHandler errorHandler;

    CallbackRunnable(Callback callback, Executor executor, ErrorHandler errorHandler) {
        this.callback = callback;
        this.callbackExecutor = executor;
        this.errorHandler = errorHandler;
    }

    public abstract ResponseWrapper obtainResponse();

    public final void run() {
        try {
            final ResponseWrapper obtainResponse = obtainResponse();
            this.callbackExecutor.execute(new Runnable() {
                public void run() {
                    CallbackRunnable.this.callback.success(obtainResponse.responseBody, obtainResponse.response);
                }
            });
        } catch (RetrofitError e) {
            RetrofitError e2 = e;
            Throwable handleError = this.errorHandler.handleError(e2);
            if (handleError != e2) {
                e2 = RetrofitError.unexpectedError(e2.getUrl(), handleError);
            }
            this.callbackExecutor.execute(new Runnable() {
                public void run() {
                    CallbackRunnable.this.callback.failure(e2);
                }
            });
        }
    }
}
