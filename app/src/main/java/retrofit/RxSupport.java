package retrofit;

import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

final class RxSupport {
    private final ErrorHandler errorHandler;
    private final Executor executor;
    private final RequestInterceptor requestInterceptor;

    interface Invoker {
        ResponseWrapper invoke(RequestInterceptor requestInterceptor);
    }

    /* renamed from: retrofit.RxSupport.1 */
    class AnonymousClass1 implements OnSubscribe<Object> {
        final /* synthetic */ Invoker val$invoker;

        AnonymousClass1(Invoker invoker) {
            this.val$invoker = invoker;
        }

        public void call(Subscriber<? super Object> subscriber) {
            RequestInterceptorTape interceptorTape = new RequestInterceptorTape();
            RxSupport.this.requestInterceptor.intercept(interceptorTape);
            FutureTask<Void> task = new FutureTask(RxSupport.this.getRunnable(subscriber, this.val$invoker, interceptorTape), null);
            subscriber.add(Subscriptions.from(task));
            RxSupport.this.executor.execute(task);
        }
    }

    /* renamed from: retrofit.RxSupport.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ RequestInterceptorTape val$interceptorTape;
        final /* synthetic */ Invoker val$invoker;
        final /* synthetic */ Subscriber val$subscriber;

        AnonymousClass2(Subscriber subscriber, Invoker invoker, RequestInterceptorTape requestInterceptorTape) {
            this.val$subscriber = subscriber;
            this.val$invoker = invoker;
            this.val$interceptorTape = requestInterceptorTape;
        }

        public void run() {
            try {
                if (!this.val$subscriber.isUnsubscribed()) {
                    this.val$subscriber.onNext(this.val$invoker.invoke(this.val$interceptorTape).responseBody);
                    this.val$subscriber.onCompleted();
                }
            } catch (RetrofitError e) {
                this.val$subscriber.onError(RxSupport.this.errorHandler.handleError(e));
            }
        }
    }

    RxSupport(Executor executor, ErrorHandler errorHandler, RequestInterceptor requestInterceptor) {
        this.executor = executor;
        this.errorHandler = errorHandler;
        this.requestInterceptor = requestInterceptor;
    }

    Observable createRequestObservable(Invoker invoker) {
        return Observable.create(new AnonymousClass1(invoker));
    }

    private Runnable getRunnable(Subscriber<? super Object> subscriber, Invoker invoker, RequestInterceptorTape interceptorTape) {
        return new AnonymousClass2(subscriber, invoker, interceptorTape);
    }
}
