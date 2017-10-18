package retrofit;

import android.os.Build.VERSION;
import android.os.Process;
import com.google.gson.i;
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
    static final boolean HAS_RX_JAVA = hasRxJavaOnClasspath();
    private static final Platform PLATFORM = findPlatform();

    class Android extends Platform {
        private Android() {
        }

        Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        Provider defaultClient() {
            Client urlConnectionClient = !Platform.hasOkHttpOnClasspath() ? VERSION.SDK_INT >= 9 ? new UrlConnectionClient() : new AndroidApacheClient() : OkClientInstantiator.instantiate();
            return new Provider() {
                public Client get() {
                    return urlConnectionClient;
                }
            };
        }

        Converter defaultConverter() {
            return new GsonConverter(new i());
        }

        Executor defaultHttpExecutor() {
            return Executors.newCachedThreadPool(new ThreadFactory() {
                public Thread newThread(final Runnable runnable) {
                    return new Thread(new Runnable() {
                        public void run() {
                            Process.setThreadPriority(10);
                            runnable.run();
                        }
                    }, "Retrofit-Idle");
                }
            });
        }

        Log defaultLog() {
            return new AndroidLog("Retrofit");
        }
    }

    class Base extends Platform {
        private Base() {
        }

        Executor defaultCallbackExecutor() {
            return new SynchronousExecutor();
        }

        Provider defaultClient() {
            final Client urlConnectionClient = !Platform.hasOkHttpOnClasspath() ? new UrlConnectionClient() : OkClientInstantiator.instantiate();
            return new Provider() {
                public Client get() {
                    return urlConnectionClient;
                }
            };
        }

        Converter defaultConverter() {
            return new GsonConverter(new i());
        }

        Executor defaultHttpExecutor() {
            return Executors.newCachedThreadPool(new ThreadFactory() {
                public Thread newThread(final Runnable runnable) {
                    return new Thread(new Runnable() {
                        public void run() {
                            Thread.currentThread().setPriority(1);
                            runnable.run();
                        }
                    }, "Retrofit-Idle");
                }
            });
        }

        Log defaultLog() {
            return new Log() {
                public void log(String str) {
                    System.out.println(str);
                }
            };
        }
    }

    class AppEngine extends Base {
        private AppEngine() {
            super();
        }

        Provider defaultClient() {
            final UrlFetchClient urlFetchClient = new UrlFetchClient();
            return new Provider() {
                public Client get() {
                    return urlFetchClient;
                }
            };
        }
    }

    class OkClientInstantiator {
        private OkClientInstantiator() {
        }

        static Client instantiate() {
            return new OkClient();
        }
    }

    Platform() {
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException e) {
        }
        return System.getProperty("com.google.appengine.runtime.version") == null ? new Base() : new AppEngine();
    }

    static Platform get() {
        return PLATFORM;
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

    abstract Executor defaultCallbackExecutor();

    abstract Provider defaultClient();

    abstract Converter defaultConverter();

    abstract Executor defaultHttpExecutor();

    abstract Log defaultLog();
}
