package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class OkHeaders {
    private static final Comparator FIELD_NAME_COMPARATOR = new Comparator() {
        public int compare(String str, String str2) {
            return str != str2 ? str != null ? str2 != null ? String.CASE_INSENSITIVE_ORDER.compare(str, str2) : 1 : -1 : 0;
        }
    };
    static final String PREFIX = Platform.get();
    public static final String RECEIVED_MILLIS;
    public static final String SELECTED_PROTOCOL;
    public static final String SENT_MILLIS;

    static {
        Object stringBuilder = new StringBuilder();
        String str = PREFIX;
        str = "-Sent-Millis";
        SENT_MILLIS = stringBuilder;
        stringBuilder = new StringBuilder();
        str = PREFIX;
        str = "-Received-Millis";
        RECEIVED_MILLIS = stringBuilder;
        stringBuilder = new StringBuilder();
        str = PREFIX;
        str = "-Selected-Protocol";
        SELECTED_PROTOCOL = stringBuilder;
    }

    private OkHeaders() {
    }

    public static void addCookies(Builder builder, Map map) {
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if ("Cookie".equalsIgnoreCase(str) || "Cookie2".equalsIgnoreCase(str)) {
                if (!((List) entry.getValue()).isEmpty()) {
                    builder.addHeader(str, buildCookieHeader((List) entry.getValue()));
                }
            }
        }
    }

    private static String buildCookieHeader(List list) {
        if (list.size() == 1) {
            return (String) list.get(0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            stringBuilder.append((String) list.get(i));
        }
        return stringBuilder.toString();
    }

    public static long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    public static long contentLength(Request request) {
        return contentLength(request.headers());
    }

    public static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public static boolean hasVaryAll(Response response) {
        return varyFields(response).contains("*");
    }

    static boolean isEndToEnd(String str) {
        return ("Connection".equalsIgnoreCase(str) || "Keep-Alive".equalsIgnoreCase(str) || "Proxy-Authenticate".equalsIgnoreCase(str) || "Proxy-Authorization".equalsIgnoreCase(str) || "TE".equalsIgnoreCase(str) || "Trailers".equalsIgnoreCase(str) || "Transfer-Encoding".equalsIgnoreCase(str) || "Upgrade".equalsIgnoreCase(str)) ? false : true;
    }

    public static List parseChallenges(Headers headers, String str) {
        List arrayList = new ArrayList();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            if (str.equalsIgnoreCase(headers.name(i))) {
                String value = headers.value(i);
                int i2 = 0;
                while (i2 < value.length()) {
                    int skipUntil = HeaderParser.skipUntil(value, i2, " ");
                    String trim = value.substring(i2, skipUntil).trim();
                    skipUntil = HeaderParser.skipWhitespace(value, skipUntil);
                    if (!value.regionMatches(true, skipUntil, "realm=\"", 0, "realm=\"".length())) {
                        break;
                    }
                    i2 = "realm=\"".length() + skipUntil;
                    skipUntil = HeaderParser.skipUntil(value, i2, "\"");
                    String substring = value.substring(i2, skipUntil);
                    i2 = HeaderParser.skipWhitespace(value, HeaderParser.skipUntil(value, skipUntil + 1, ",") + 1);
                    arrayList.add(new Challenge(trim, substring));
                }
            }
        }
        return arrayList;
    }

    public static Request processAuthHeader(Authenticator authenticator, Response response, Proxy proxy) {
        return response.code() != 407 ? authenticator.authenticate(proxy, response) : authenticator.authenticateProxy(proxy, response);
    }

    private static long stringToLong(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Map toMultimap(Headers headers, String str) {
        Map treeMap = new TreeMap(FIELD_NAME_COMPARATOR);
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            List arrayList = new ArrayList();
            List list = (List) treeMap.get(name);
            if (list != null) {
                arrayList.addAll(list);
            }
            arrayList.add(value);
            treeMap.put(name, Collections.unmodifiableList(arrayList));
        }
        if (str != null) {
            treeMap.put(null, Collections.unmodifiableList(Collections.singletonList(str)));
        }
        return Collections.unmodifiableMap(treeMap);
    }

    private static Set varyFields(Response response) {
        Set emptySet = Collections.emptySet();
        Headers headers = response.headers();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            if ("Vary".equalsIgnoreCase(headers.name(i))) {
                String value = headers.value(i);
                if (emptySet.isEmpty()) {
                    emptySet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                }
                for (String trim : value.split(",")) {
                    emptySet.add(trim.trim());
                }
            }
        }
        return emptySet;
    }

    public static Headers varyHeaders(Response response) {
        Set varyFields = varyFields(response);
        if (varyFields.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers headers = response.networkResponse().request().headers();
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            if (varyFields.contains(name)) {
                builder.add(name, headers.value(i));
            }
        }
        return builder.build();
    }

    public static boolean varyMatches(Response response, Headers headers, Request request) {
        for (String str : varyFields(response)) {
            if (!Util.equal(headers.values(str), request.headers(str))) {
                return false;
            }
        }
        return true;
    }
}
