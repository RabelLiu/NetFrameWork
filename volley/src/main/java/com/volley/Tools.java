package com.volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.text.TextUtils;
import android.util.Log;

/**
 * 工具类.
 *
 * @author TG.1
 * @date 2015-07-12
 */
public class Tools {
    private static final String TAG = Tools.class.getSimpleName();

    public static boolean DEBUG = false;

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

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return new HashMap<String, String>();
        }

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    public static String calcMD5(String input) {
        if (input == null) {
            return "";
        }
        // FIXME 发布时请关闭此Log.
        Log.d("test", "ic:" + input);
        return calcMD5(input.getBytes());
    }

    private static final int OFFSET_0XFF = 0xff;
    private static final int MD5_SUBSTRING_LENGTH = 16;

    private static String calcMD5(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        // generate MessageDigest
        md.update(data);
        byte[] hash = md.digest();

        // translate to string
        StringBuffer sbRet = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & OFFSET_0XFF;
            if (v < MD5_SUBSTRING_LENGTH) {
                sbRet.append("0");
            }
            sbRet.append(Integer.toString(v, MD5_SUBSTRING_LENGTH));
        }
        return sbRet.toString();
    }

}

class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}