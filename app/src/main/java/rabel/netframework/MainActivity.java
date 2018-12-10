package rabel.netframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.rabel.rxretrofit.Bean.EmptyBean;
import com.rabel.rxretrofit.Bean.SongListBean;
import com.volley.request.base.api.json.EmptyResponse;
import com.volley.request.base.api.json.ResponseListener;

import rabel.netframework.request.retrofit.RetrofitManager;
import rabel.netframework.request.volley.RequestManager;
import rabel.netframework.utils.LogUtil;
import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_V = "MainActivity_Volley";
    private static final String TAG_R = "MainActivity_Retrofit";

    private long oldTime_v,oldTime_r,newTiem_v,newTime_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestVolleyData();
        TestRetrofitData();
    }

    private void TestRetrofitData() {
        oldTime_r = System.currentTimeMillis();
        getDataFromListSongByRetro("情歌对唱");
        getDataFromListSongByRetro("中国新歌声");
        getDataFromListSongByRetro("流行经典");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){

                }
                getDataFromListSongByRetro("中国新歌声");
            }
        }).start();

    }

    private void getDataFromListSongByRetro(final String typeValue) {
        RetrofitManager.getSongList(typeValue)
                .subscribe(new Observer<SongListBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.logD(TAG_R,"this is onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.logD(TAG_R,"this is onError:"+e.toString());
                    }

                    @Override
                    public void onNext(SongListBean songListBean) {
                        LogUtil.logD(TAG_R,"this is onNext:"+typeValue+":;"+songListBean.getResponse()
                        .getSongs_list().get(0).getSongs_id());
                        LogUtil.logD(TAG_R,"this is Time_r:"+(System.currentTimeMillis()-oldTime_r));
                    }
                });

    }

    private void TestVolleyData() {
        oldTime_v = System.currentTimeMillis();
        getDataFromListSong();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){

                }
                getDataFromListSong();
            }
        }).start();
    }

    private void getDataFromListSong() {

        RequestManager.getListSongs("流行经典", new ResponseListener<EmptyResponse>() {
            @Override
            public void onResponse(EmptyResponse result) {
//                LogUtil.logD(TAG_V, "this is r:" + result.json);
                LogUtil.logD(TAG_R,"this is Time_v:"+(System.currentTimeMillis()-oldTime_v));

            }

            @Override
            public void onError(VolleyError error) {
                LogUtil.logD(TAG_V, "this is err:" + error.toString());
            }

            @Override
            public void onFinish() {

            }
        });

    }
}
