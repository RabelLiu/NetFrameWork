package rabel.netframework.request.volley;

import com.android.volley.Request;
import com.volley.request.base.api.json.EmptyParser;
import com.volley.request.base.api.json.EmptyResponse;
import com.volley.request.base.api.json.JsonRequestManager;
import com.volley.request.base.api.json.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import rabel.netframework.utils.LogUtil;

/***
 * 此中的Response既可以放在module中也可以放入app中
 */
public class RequestManager {

    //友唱
    private final static String BASE_URL = "http://room.ucmbar.com:8805";


    /**
     * 获取主页歌曲相关列表
     */
    public static void getListSongs(String songTitle, ResponseListener<EmptyResponse> listener) {
        try {

            JSONObject mJson = new JSONObject();
            final JSONObject json = new JSONObject();

            try {
                json.put("function", "1002");
                json.put("version", "1.0");
                json.put("family_server_id", "f0f644a0d8cc");
                json.put("songs_type", songTitle);

                mJson.put("request", json);
                LogUtil.logD("DEBUG_DATA", "request:" + mJson.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Request request = JsonRequestManager.getInstance().jsonRequest(
                    BASE_URL, mJson, new EmptyParser(), listener);

            // 可以通过setTag方法为每一个Request添加tag
//            request.setTag(songTitle);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
