package com.rabel.download.connect;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.UUID;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED
                            || info[i].getState() == NetworkInfo.State.CONNECTING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static File getCacheDir(Context context, boolean internal) {
        if (internal) {
            return context.getCacheDir();
        } else {
            return context.getExternalCacheDir();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void enableHttpResponseCache(Context context, boolean internal, long size) {
        long httpCacheSize;
        if (size <= 0) {
            httpCacheSize = 10 * 1024 * 1024; // 10 MiB
        } else {
            httpCacheSize = size;
        }
        File httpCacheDir = new File(getCacheDir(context, internal), "http");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                HttpResponseCache.install(httpCacheDir, httpCacheSize);
            } catch (IOException e) {
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void flushHttpResponseCache() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache != null) {
                cache.flush();
            }
        }
    }

    private static String mUserAgent;

    public static String getUserAgent(Context context) {
        if (mUserAgent instanceof String) {
            return mUserAgent;
        }
        mUserAgent = getDefaultUserAgentString(context);
        return mUserAgent;
    }

    public static String getDefaultUserAgentString(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String userAgent = NewApiWrapperForUserAgent.getUserAgentJellyBeanMR1(context);
            return userAgent;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            String userAgent = NewApiWrapperForUserAgent.getUserAgentJellyBean(context);
            return userAgent;
        }

        try {
            Constructor<WebSettings> constructor = WebSettings.class.getDeclaredConstructor(Context.class,
                    WebView.class);
            constructor.setAccessible(true);
            try {
                WebSettings settings = constructor.newInstance(context, null);
                String userAgent = settings.getUserAgentString();
                return userAgent;
            } finally {
                constructor.setAccessible(false);
            }
        } catch (Exception e) {
            String userAgent = new WebView(context).getSettings().getUserAgentString();
            return userAgent;
        }
    }

    @SuppressLint("NewApi")
    private static class NewApiWrapperForUserAgent {
        static String getUserAgentJellyBeanMR1(Context context) {
            return WebSettings.getDefaultUserAgent(context);
        }

        static String getUserAgentJellyBean(Context context) {
            try {
                @SuppressWarnings("unchecked")
                Class<? extends WebSettings> clz = (Class<? extends WebSettings>) Class
                        .forName("android.webkit.WebSettingsClassic");
                Class<?> webViewClassicClz = (Class<?>) Class.forName("android.webkit.WebViewClassic");
                Constructor<? extends WebSettings> constructor = clz.getDeclaredConstructor(Context.class,
                        webViewClassicClz);
                constructor.setAccessible(true);
                try {
                    WebSettings settings = constructor.newInstance(context, null);
                    String userAgent = settings.getUserAgentString();
                    return userAgent;
                } finally {
                    constructor.setAccessible(false);
                }
            } catch (Exception e) {
                String userAgent = new WebView(context).getSettings().getUserAgentString();
                return userAgent;
            }
        }
    }

    public static String getFileNameFromUrl(String url) {
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }

        if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
            filename = UUID.randomUUID() + ".apk";// 默认取一个文件名
        }
        return filename;
    }
}
