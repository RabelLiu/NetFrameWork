package com.rabel.rxretrofit.Net;

import android.util.Log;

import com.google.gson.Gson;
import com.rabel.rxretrofit.Bean.BaseBean;
import com.rabel.rxretrofit.Bean.SongListBean;
import com.rabel.rxretrofit.Cache.CacheManager;
import com.rabel.rxretrofit.Cache.NetWorkCache;
import com.rabel.rxretrofit.Global.Constant;
import com.rabel.rxretrofit.Utils.GetMacUtil;
import com.rabel.rxretrofit.Utils.RxJavaUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/5.
 */

public abstract class RetrofitHelper {
    private static final String Tag = "RetrofitHelper";
    public static MBarApi mMBarAPI = null;
    private static final int TIME_OUT_LIMIT = 30;
    private static RxJavaCallAdapterFactory mAdapterFactory;
    private static GsonConverterFactory mGsonConverterFactory;
    private static OkHttpClient mOkHttpClient;

    static {
        //设置超时时间
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS)
                .build();
        mGsonConverterFactory = GsonConverterFactory.create();
        mAdapterFactory = RxJavaCallAdapterFactory.create();


        Retrofit MbarRetrofit = new Retrofit.Builder().baseUrl(Constant.MBarBaseUrl)
                .addCallAdapterFactory(mAdapterFactory)
                .addConverterFactory(mGsonConverterFactory)
                .client(mOkHttpClient)
                .build();
        mMBarAPI = MbarRetrofit.create(MBarApi.class);
    }

    public static  Retrofit getApi(String baseUrl){
        Retrofit MbarRetrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(mAdapterFactory)
                .addConverterFactory(mGsonConverterFactory)
                .client(mOkHttpClient)
                .build();
        return MbarRetrofit;
    }

    public static  MBarApi getmMBarAPI(){
        return getApi(Constant.MBarBaseUrl).create(MBarApi.class);
    }


    /*****************helper中的请求等在此类中************************/

    public static  Observable<SongListBean> getSongList(final String type_value) {

        final RequestBody body = getRequestBody("1002", "songs_type", type_value);

        NetWorkCache<SongListBean> netCache = new NetWorkCache<SongListBean>() {

            @Override
            public Observable<SongListBean> get(String key, Class<SongListBean> cls) {

                return mMBarAPI.getSongList(body)
                        .compose(RxJavaUtils.<SongListBean>applySchedulers());
            }
        };
        return LoadData(Constant.MBarBaseUrl, SongListBean.class, netCache);
    }

    public static  Observable<BaseBean> getSongList(final MBarApi baseApi, final RequestBody body) {


        NetWorkCache<BaseBean> netCache = new NetWorkCache<BaseBean>() {

            @Override
            public Observable<BaseBean> get(String key, Class<BaseBean> cls) {

                return baseApi.getSongList(body)
                        .compose(RxJavaUtils.<BaseBean>applySchedulers());
            }
        };
        return LoadData(Constant.MBarBaseUrl, BaseBean.class, netCache);
    }


    public  static  RequestBody  getBody(String params){

        return RequestBody.create(MediaType.parse("application/json"), params);
    }

    private static RequestBody getRequestBody(String function, String type, String type_value) {
        Gson gson = new Gson();
        HashMap mMap = new HashMap();
        HashMap map = new HashMap();
        map.put("function", function);
        map.put("version", "1.0");
        map.put("family_server_id", GetMacUtil.getMacAddress());
        map.put(type, type_value);
        mMap.put("request", map);

        String newParams = gson.toJson(mMap);
        Log.i("DEBUG_data", "tis is-->" + newParams);
        return RequestBody.create(MediaType.parse("application/json"), newParams);
    }


    private static HashMap getHeadMap(HashMap headmap) {

        headmap.put("data_type", "normal");
        headmap.put("data_direction", "request");
        headmap.put("server", "youchang_server");
        headmap.put("id", "youchang_server");
        return headmap;
    }

    /*****************end************************/

    public static <T extends BaseBean> Observable<T> LoadData(String key, Class<T> cls, NetWorkCache<T> netCache) {
        Observable<T> Observable = CacheManager
                .getInstance()
                .loadData(key, cls, netCache)
                .compose(RxJavaUtils.<T>applySchedulers());
        Log.i("DEBUG_A", "this is getObservable ---LoadData");
        return Observable;
    }

}




