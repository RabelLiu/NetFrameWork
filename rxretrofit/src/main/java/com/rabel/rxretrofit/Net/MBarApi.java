package com.rabel.rxretrofit.Net;


import com.rabel.rxretrofit.Bean.BaseBean;
import com.rabel.rxretrofit.Bean.EmptyBean;
import com.rabel.rxretrofit.Bean.SongListBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface MBarApi{


//    @POST("http://room.ucmbar.com:8805/{jo}")
//    Observable<T> getSongList(@Body RequestBody jo);

    @POST("http://room.ucmbar.com:8805/{jo}")
    Observable<SongListBean> getSongList(@Body RequestBody jo);

    @POST("http://room.ucmbar.com:8805/{jo}")
    Observable<EmptyBean> getResString(@Body RequestBody jo);


}
