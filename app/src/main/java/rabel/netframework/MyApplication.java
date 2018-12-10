package rabel.netframework;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.rabel.rxretrofit.Cache.DiskCache;
import com.rabel.rxretrofit.Cache.MemoryCache;
import com.rabel.rxretrofit.Utils.FileUtils;
import com.volley.request.base.api.json.JsonRequestManager;
import com.volley.request.cache.imageCache.ImageCacheUtil;

import java.io.File;

/**
 * Created by LC on 2018/12/7.
 */

public class MyApplication extends Application {

    private static MyApplication appContext;

    public static MyApplication getMyApplicationContext() {
        return appContext;
    }

    public static ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;//存储引用

        //Volley框架调用
        JsonRequestManager.start(this, null, true);
        //
        initRetrofitCache();

//        initVolleyImageLoader();


    }

    private void initRetrofitCache() {
        File cacheFile = FileUtils.getDiskCacheDir(this, "data");
        DiskCache.init(cacheFile, 1);
        MemoryCache.init();
    }

    private void initVolleyImageLoader() {
        RequestQueue mQueue = Volley.newRequestQueue(this);

        // OkHttpStack okHttpStack = new OkHttpStack(new OkHttpClient());
        // int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // int cacheSize = maxMemory / 8;
        // RequestQueue mQueue = MyVolley.newRequestQueue(this, okHttpStack,
        // cacheSize);

        ImageCacheUtil lruImageCache = new ImageCacheUtil(this);
        // LruImageCache lruImageCache = LruImageCache.instance();

        imageLoader = new ImageLoader(mQueue, lruImageCache);

    }
}
