package com.volley.request.base.api.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 工具类.
 * 
 * @date 2015-12-01
 */
public class AppTools {
    public static final String OPERID = "1";

    /**
     * 给后台上报的数据 无网络
     */
    public static final String TYPE_NONE = "0";

    /**
     * WIFI网络
     */
    public static final String TYPE_WIFI = "1";

    /**
     * 移动4G网络
     */
    public static final String TYPE_CMCC_4G = "2";

    /**
     * 移动2G、3G网络
     */
    public static final String TYPE_CMCC_2G = "3";

    /**
     * 其他运营商网络（电信、联通）
     */
    public static final String TYPE_OTHER = "4";

    /**
     * A、wifi：通过WLAN方式接入的网络 B、移动4G：移动的4G网络 C、移动2/3G：移动的2G、3G网络
     * D、其他网络：剔除掉上诉三类网络的其他网络，包含电信、联通网络等
     * 
     * //用户网络制式 "0"=>array('typeId'=>0,'typeName'=>'无'),
     * "1"=>array('typeId'=>1,'typeName'=>'wifi'),
     * "2"=>array('typeId'=>2,'typeName'=>'移动4G'),
     * "3"=>array('typeId'=>3,'typeName'=>'移动2/3G'),
     * "4"=>array('typeId'=>4,'typeName'=>'其他网络'), ),
     * 
     * 获取手机当前网络类型
     * 
     * @param context
     * @return
     */
    public static String getMobileNetworkType(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cwjManager.getActiveNetworkInfo() != null) {
            if (cwjManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (cwjManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
                return getMobileNetType(context);
            }
        }
        return TYPE_NONE;
    }

    /**
     * 判断手机网络运营商
     * 
     * @param context
     * @return
     */
    private static String getMobileNetType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
        case TelephonyManager.NETWORK_TYPE_EDGE:
        case TelephonyManager.NETWORK_TYPE_HSDPA:
            return TYPE_CMCC_2G;

        case TelephonyManager.NETWORK_TYPE_1xRTT:
        case TelephonyManager.NETWORK_TYPE_CDMA:
        case TelephonyManager.NETWORK_TYPE_GPRS:
        case TelephonyManager.NETWORK_TYPE_IDEN:
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
        case TelephonyManager.NETWORK_TYPE_HSPA:
        case TelephonyManager.NETWORK_TYPE_HSUPA:
        case TelephonyManager.NETWORK_TYPE_UMTS:
        case TelephonyManager.NETWORK_TYPE_EHRPD:
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
        case TelephonyManager.NETWORK_TYPE_HSPAP:
            return TYPE_OTHER;

        case TelephonyManager.NETWORK_TYPE_LTE:
            return TYPE_CMCC_4G;

        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            return TYPE_NONE;

        default:
            return TYPE_OTHER;
        }
    }

    /**
     * 获取应用的版本信息.
     * 
     * @param context
     *            上下文.
     * @param pkgName
     *            应用包名.
     * @return 版本信息.
     */
    public static String getVersionName(Context context, String pkgName) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(pkgName, 0);
            String versionName = packInfo.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号(versionCode).
     * 
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packInfo.versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {

            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 获取版本号(versionCode).
     * 
     * @param context
     * @return
     */
    public static PackageInfo getApkInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo;
        } catch (NameNotFoundException e) {
            
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取SDK版本
     */
    public static int getSystemSDKVersion() {
        int version;
        try {
            version = Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            version = 0;
            Log.e("sdk", e.toString());
        }

        return version;
    }

    /**
     * 获取手机版本
     * 
     * @return
     */
    public static String getPhoneSDKVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机详细信息
     */
    public static String getPhoneDetail() {
        String phoneInfo = "Product: " + Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + Build.CPU_ABI;
        phoneInfo += ", TAGS: " + Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + Build.MODEL;
        phoneInfo += ", SDK: " + Build.VERSION.SDK;
        phoneInfo += ", SDK_INT: " + Build.VERSION.SDK_INT;
        phoneInfo += ", VERSION.RELEASE: " + Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + Build.DEVICE;
        phoneInfo += ", DISPLAY: " + Build.DISPLAY;
        phoneInfo += ", BRAND: " + Build.BRAND;
        phoneInfo += ", BOARD: " + Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + Build.FINGERPRINT;
        phoneInfo += ", ID: " + Build.ID;
        phoneInfo += ", MANUFACTURER: " + Build.MANUFACTURER;
        phoneInfo += ", USER: " + Build.USER;
        return phoneInfo;
    }

    /**
     * 获取手机分辨率
     */
    public static String getDisplayMetric(Activity activity) {
        // 获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 分辨率
        return String.valueOf(dm.heightPixels + "*" + dm.widthPixels);
    }

    /**
     * 安装应用.
     * 
     * @param context
     * @param file
     */
    public static void installLoadedApkFile(Context context, File file) {
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(installIntent);
    }

    /**
     * 获取mac地址.
     * 
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null || info.getMacAddress() == null) {
            return "";
        }
        return info.getMacAddress();
    }

    /**
     * SIM卡唯一识别码
     * 
     * @param c
     * @return 460002831311135
     */
    public static String getIMSI(Context c) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        if (imsi == null) {
            return "";
        }
        return imsi;
    }

    /**
     * 获取手机号.
     * 
     * @param context
     * @return
     */
    public static String getPhone(Context context) {
        // 创建电话管理
        TelephonyManager tm = (TelephonyManager)
        // 与手机建立连接
        context.getSystemService(Context.TELEPHONY_SERVICE);
        // 获取手机号码
        String phoneId = tm.getLine1Number();
        if (phoneId == null) {
            phoneId = "";
        }
        return phoneId;
    }

    /**
     * 获取IMEI 手机设备唯一码
     * 
     * @param context
     * @return 864264025502250
     */
    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        if (imei == null) {
            return "";
        }
        return imei;
    }

    /**
     * 
     * 获取当前时间(yyyy-MM-dd HH:mm:ss)
     * 
     */
    public static String getCurrentTime() {
        try {
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sDateFormat.format(curDate);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1970-01-01 00:00:00";
    }

    /**
     * 获取apk包的信息：版本号，名称，图标等
     * 
     * @param absPath
     *            apk包的绝对路径
     * @param context
     */
    public static int apkInfo(String absPath, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
            String packageName = appInfo.packageName; // 得到包名
            String version = pkgInfo.versionName; // 得到版本信息
            int versioncode = pkgInfo.versionCode; // 得到版本信息
            return versioncode;
            /* icon1和icon2其实是一样的 */
            // Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
            // Drawable icon2 = appInfo.loadIcon(pm);
            // String pkgInfoStr =
            // String.format("PackageName:%s, Vesion: %s, AppName: %s",
            // packageName, version, appName);
            // Log.i(TAG, String.format("PkgInfo: %s", pkgInfoStr));
        }
        return -1;
    }

    /**
     * String转成int.
     * 
     * @param str
     *            需要转换的string值.
     * @param defaultValue
     *            转失败后的默认值.
     * 
     * @return 转换后的结果.
     */
    public static int parseInt(String str, int defaultValue) {
        if (TextUtils.isEmpty(str)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(str);

        } catch (NumberFormatException e) {
            return defaultValue;

        }
    }

    /**
     * 将时间转换成"00:00:00"格式.
     * 
     * @param duration
     *            时长.
     * 
     * @return 转换后的时长.
     */
    public static String formatMinutes(int minutes) {
        if (minutes <= 0) {
            return "00:00:00";
        }
        int min = minutes / 60 % 60;
        int hour = minutes / 60 / 60;
        int second = 0;
        return String.format("%02d:%02d:%02d", hour, min, second);
    }

    /**
     * 将时间转换成"00:00:00"格式.
     * 
     * @param duration
     *            时长.
     * 
     * @return 转换后的时长.
     */
    public static String formatMinutesByString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "00:00:00";
        }

        int minutes = 0;

        try {
            minutes = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return "00:00:00";
        }

        if (minutes <= 0) {
            return "00:00:00";
        }
        int min = minutes % 60;
        int hour = minutes / 60;
        int second = 0;
        return String.format("%02d:%02d:%02d", hour, min, second);
    }
    
    /**
     * 在bean里调用get方法时判断是否为空
     * @param str
     * @param Default
     * @return
     */
    public static String getString(String str,String Default){
        if(TextUtils.isEmpty(str)){            
            return Default;
        }
        return str;
    }
}
