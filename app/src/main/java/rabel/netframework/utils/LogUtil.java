package rabel.netframework.utils;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 工具类.
 *
 */
public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();

    public static boolean DEBUG = true;

    /**
     * 打印Log. Log.i(TAG,String);
     *
     * @param tag
     * @param string
     */
    public static void logE(String tag, String string) {
        if (!DEBUG) {
            return;
        }

        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        Log.e(tag, "" + string);
    }

    /**
     * 打印Log. Log.d(TAG,String);
     *
     * @param tag
     * @param string
     */
    public static void logD(String tag, String string) {
        if (!DEBUG) {
            return;
        }

        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        Log.d(tag, "" + string);
    }

}