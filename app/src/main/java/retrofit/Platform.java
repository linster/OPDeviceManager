package retrofit;

import android.os.Build.VERSION;
import android.os.Process;
import com.google.gson.Gson;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import retrofit.RestAdapter.Log;
import retrofit.android.AndroidApacheClient;
import retrofit.android.AndroidLog;
import retrofit.android.MainThreadExecutor;
import retrofit.appengine.UrlFetchClient;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.OkClient;
import retrofit.client.UrlConnectionClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

abstract class Platform {
    static final boolean HAS_RX_JAVA;
    private static final Platform PLATFORM;

    private static class Android extends Platform {

        /* renamed from: retrofit.Platform.Android.1 */
        class AnonymousClass1 implements Provider {
            final /* synthetic */ Client val$client;

            AnonymousClass1(Client client) {
                this.val$client = client;
            }

            public Client get() {
                return this.val$client;
            }
        }

        private Android() {
        }

        Converter defaultConverter() {
            return new GsonConverter(new Gson());
        }

        Provider defaultClient() {
            Client client;
            if (Platform.hasOkHttpOnClasspath()) {
                client = OkClientInstantiator.instantiate();
            } else if (VERSION.SDK_INT >= 9) {
                client = new UrlConnectionClient();
            } else {
                client = new AndroidApacheClient();
            }
            return new AnonymousClass1(client);
        }

        Executor defaultHttpExecutor() {
            return Executors.newCachedThreadPool(new ThreadFactory() {

                /* renamed from: retrofit.Platform.Android.2.1 */
                class AnonymousClass1 implements Runnable {
                    final /* synthetic */ Runnable val$r;

                    AnonymousClass1(Runnable runnable) {
                        this.val$r = runnable;
                    }

                    public void run() {
                        Process.setThreadPriority(10);
                        this.val$r.run();
                    }
                }

                public Thread newThread(Runnable r) {
                    return new Thread(new AnonymousClass1(r), "Retrofit-Idle");
                }
            });
        }

        Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        Log defaultLog() {
            return new AndroidLog("Retrofit");
        }
    }

    private static class Base extends Platform {

        /* renamed from: retrofit.Platform.Base.1 */
        class AnonymousClass1 implements Provider {
            final /* synthetic */ Client val$client;

            AnonymousClass1(Client client) {
                this.val$client = client;
            }

            public Client get() {
                return this.val$client;
            }
        }

        private Base() {
        }

        Converter defaultConverter() {
            return new GsonConverter(new Gson());
        }

        Provider defaultClient() {
            Client client;
            if (Platform.hasOkHttpOnClasspath()) {
                client = OkClientInstantiator.instantiate();
            } else {
                client = new UrlConnectionClient();
            }
            return new AnonymousClass1(client);
        }

        Executor defaultHttpExecutor() {
            return Executors.newCachedThreadPool(new ThreadFactory() {

                /* renamed from: retrofit.Platform.Base.2.1 */
                class AnonymousClass1 implements Runnable {
                    final /* synthetic */ Runnable val$r;

                    AnonymousClass1(Runnable runnable) {
                        this.val$r = runnable;
                    }

                    public void run() {
                        Thread.currentThread().setPriority(1);
                        this.val$r.run();
                    }
                }

                public Thread newThread(Runnable r) {
                    return new Thread(new AnonymousClass1(r), "Retrofit-Idle");
                }
            });
        }

        Executor defaultCallbackExecutor() {
            return new SynchronousExecutor();
        }

        Log defaultLog() {
            return new Log() {
                public void log(String message) {
                    System.out.println(message);
                }
            };
        }
    }

    private static class AppEngine extends Base {

        /* renamed from: retrofit.Platform.AppEngine.1 */
        class AnonymousClass1 implements Provider {
            final /* synthetic */ UrlFetchClient val$client;

            AnonymousClass1(UrlFetchClient urlFetchClient) {
                this.val$client = urlFetchClient;
            }

            public Client get() {
                return this.val$client;
            }
        }

        private AppEngine() {
            super();
        }

        Provider defaultClient() {
            return new AnonymousClass1(new UrlFetchClient());
        }
    }

    private static class OkClientInstantiator {
        private OkClientInstantiator() {
        }

        static Client instantiate() {
            return new OkClient();
        }
    }

    abstract Executor defaultCallbackExecutor();

    abstract Provider defaultClient();

    abstract Converter defaultConverter();

    abstract Executor defaultHttpExecutor();

    abstract Log defaultLog();

    Platform() {
    }

    static {
        PLATFORM = findPlatform();
        HAS_RX_JAVA = hasRxJavaOnClasspath();
    }

    static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException e) {
        }
        if (System.getProperty("com.google.appengine.runtime.version") == null) {
            return new Base();
        }
        return new AppEngine();
    }

    private static boolean hasOkHttpOnClasspath() {
        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean hasRxJavaOnClasspath() {
        try {
            Class.forName("rx.Observable");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
