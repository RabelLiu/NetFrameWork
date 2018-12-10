package rabel.netframework.request.retrofit;


import android.util.Log;

import com.google.gson.Gson;
import com.rabel.rxretrofit.Bean.EmptyBean;
import com.rabel.rxretrofit.Bean.SongListBean;
import com.rabel.rxretrofit.Cache.NetWorkCache;
import com.rabel.rxretrofit.Net.MBarApi;
import com.rabel.rxretrofit.Net.RetrofitHelper;
import com.rabel.rxretrofit.Utils.GetMacUtil;
import com.rabel.rxretrofit.Utils.RxJavaUtils;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by LC on 2016/12/5.
 *
 * 此法中的SongListBean在Module中，没有从module中分离出来，如果需要分离出来则需要
 * 引入在app中引入Retrofit,并实现MBarApiTest
 *
 */


public class RetrofitManager {

    public static String MBarBaseUrl = "http://room.ucmbar.com:8805/";

    static MBarApi mMBarAPI = RetrofitHelper.getmMBarAPI();


    public static Observable<SongListBean> getSongList(String type_value) {

        final RequestBody body = getRequestBody("1002", "songs_type", type_value);

        NetWorkCache<SongListBean> netCache = new NetWorkCache<SongListBean>() {
            @Override
            public Observable<SongListBean> get(String key, Class<SongListBean> cls) {

                return mMBarAPI.getSongList(body)
                        .compose(RxJavaUtils.<SongListBean>applySchedulers());
            }
        };
        //注意key的使用，其会影响第二次请求的结果
        return RetrofitHelper.LoadData(type_value, SongListBean.class, netCache);

    }
    public static Observable<EmptyBean> getResString(String type_value) {

        final RequestBody body = getRequestBody("1002", "songs_type", type_value);

        NetWorkCache<EmptyBean> netCache = new NetWorkCache<EmptyBean>() {
            @Override
            public Observable<EmptyBean> get(String key, Class<EmptyBean> cls) {

                return mMBarAPI.getResString(body)
                        .compose(RxJavaUtils.<EmptyBean>applySchedulers());
            }
        };

        return RetrofitHelper.LoadData("getResString", EmptyBean.class, netCache);

    }

    /**
     * 请求主体
     * @param function
     * @param type
     * @param type_value
     * @return
     */
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

}




