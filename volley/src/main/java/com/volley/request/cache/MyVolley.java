package com.volley.request.cache;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import okhttp3.OkHttpClient;

public class MyVolley {

    private static String DEFAULT_CACHE_DIR = "micro";

    public static RequestQueue newRequestQueue(Context context, OkHttp3Stack stack, int maxDiskCacheBytes) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new OkHttp3Stack(new OkHttpClient.Builder().build());
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
//                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        BasicNetworkOkHttp3 network = new BasicNetworkOkHttp3(stack);

        RequestQueue queue;
        if (maxDiskCacheBytes <= -1) {
            // 如果你不设置磁盘缓存最大值的话这里初始化（默认是5M）
            queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        } else {
            // 设置了最大缓存值在这里初始化
            queue = new RequestQueue(new DiskBasedCache(cacheDir, maxDiskCacheBytes), network);
        }

        queue.start();

        return queue;

    }

    public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCacheBytes) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
//                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue;
        if (maxDiskCacheBytes <= -1) {
            // 如果你不设置磁盘缓存最大值的话这里初始化（默认是5M）
            queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        } else {
            // 设置了最大缓存值在这里初始化
            queue = new RequestQueue(new DiskBasedCache(cacheDir, maxDiskCacheBytes), network);
        }

        queue.start();

        return queue;

    }

    public static RequestQueue newRequestQueue2(Context context, HurlStack stack, int maxDiskCacheBytes) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            stack = new HurlStack();
            // if (Build.VERSION.SDK_INT >= 9) {
            // stack = new HurlStack();
            // } else {
            // // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // // See:
            // //
            // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            // stack = new
            // HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            // }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue;
        if (maxDiskCacheBytes <= -1) {
            // 如果你不设置磁盘缓存最大值的话这里初始化（默认是5M）
            queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        } else {
            // 设置了最大缓存值在这里初始化
            queue = new RequestQueue(new DiskBasedCache(cacheDir, maxDiskCacheBytes), network);
        }

        queue.start();

        return queue;

    }
}
