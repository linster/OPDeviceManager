package retrofit;

import java.util.concurrent.Executor;

abstract class CallbackRunnable<T> implements Runnable {
    private final Callback<T> callback;
    private final Executor callbackExecutor;
    private final ErrorHandler errorHandler;

    /* renamed from: retrofit.CallbackRunnable.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ ResponseWrapper val$wrapper;

        AnonymousClass1(ResponseWrapper responseWrapper) {
            this.val$wrapper = responseWrapper;
        }

        public void run() {
            CallbackRunnable.this.callback.success(this.val$wrapper.responseBody, this.val$wrapper.response);
        }
    }

    /* renamed from: retrofit.CallbackRunnable.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ RetrofitError val$handled;

        AnonymousClass2(RetrofitError retrofitError) {
            this.val$handled = retrofitError;
        }

        public void run() {
            CallbackRunnable.this.callback.failure(this.val$handled);
        }
    }

    public abstract ResponseWrapper obtainResponse();

    CallbackRunnable(Callback<T> callback, Executor callbackExecutor, ErrorHandler errorHandler) {
        this.callback = callback;
        this.callbackExecutor = callbackExecutor;
        this.errorHandler = errorHandler;
    }

    public final void run() {
        try {
            this.callbackExecutor.execute(new AnonymousClass1(obtainResponse()));
        } catch (Throwable e) {
            RetrofitError handled;
            Throwable cause = this.errorHandler.handleError(e);
            if (cause != e) {
                handled = RetrofitError.unexpectedError(e.getUrl(), cause);
            } else {
                Throwable handled2 = e;
            }
            this.callbackExecutor.execute(new AnonymousClass2(handled));
        }
    }
}
