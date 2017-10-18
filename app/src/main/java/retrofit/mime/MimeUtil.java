package retrofit.mime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MimeUtil {
    private static final Pattern CHARSET = Pattern.compile("\\Wcharset=([^\\s;]+)", 2);

    private MimeUtil() {
    }

    @Deprecated
    public static String parseCharset(String str) {
        return parseCharset(str, "UTF-8");
    }

    public static String parseCharset(String str, String str2) {
        Matcher matcher = CHARSET.matcher(str);
        return !matcher.find() ? str2 : matcher.group(1).replaceAll("[\"\\\\]", "");
    }
}
