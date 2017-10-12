package net.oneplus.odm.common;

public class Def {
    public static String HYDROGEN_OAUTH_URL;
    public static String HYDROGEN_SERVER_URL;
    public static String OXYGEN_OAUTH_URL;
    public static String OXYGEN_SERVER_URL;
    public static int RANDOM_UPLOAD_DATA_DELAY;
    public static long UPLOAD_JOB_INTERVAL;
    public static boolean USE_GZIP;

    static {
        HYDROGEN_SERVER_URL = "https://open.oneplus.cn/";
        HYDROGEN_OAUTH_URL = "https://open.oneplus.cn/";
        OXYGEN_SERVER_URL = "https://open.oneplus.net/";
        OXYGEN_OAUTH_URL = "https://open.oneplus.net/";
        UPLOAD_JOB_INTERVAL = 14400000;
        RANDOM_UPLOAD_DATA_DELAY = 3600000;
        USE_GZIP = true;
    }
}
