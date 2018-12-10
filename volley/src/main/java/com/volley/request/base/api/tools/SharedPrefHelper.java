package com.volley.request.base.api.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
	/**
     * SharedPreferences的名字
     */
    private static final String SP_FILE_NAME = "APPLICATION_SP";
    private static SharedPrefHelper sharedPrefHelper = null;
    private static SharedPreferences sharedPreferences;
    public static synchronized SharedPrefHelper getInstance(Context c) {
        if (null == sharedPrefHelper) {
            sharedPrefHelper = new SharedPrefHelper(c);
        }
        return sharedPrefHelper;
    }

    private SharedPrefHelper(Context c) {
        sharedPreferences = c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences sp(Context c) {
        return c.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }
    
    /**
     * 应用第一次安装，也就是第一次启动
     * 
     */
    public void setInstall(boolean isFirst) {
        sharedPreferences.edit().putBoolean("install", isFirst).commit();
    }

    public boolean getInstall() {
        return sharedPreferences.getBoolean("install", false);
    }
}
