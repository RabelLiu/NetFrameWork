package com.volley;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.volley.request.base.api.json.BaseParser;
import com.volley.request.base.api.json.BaseResponse;
import com.volley.request.base.api.json.JsonRequestManager;
import com.volley.request.base.api.json.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static String BASE_URL = "https://uc.ipktv.com/youCS/youC20170216/YoucServer/index";


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.volley.test", appContext.getPackageName());
        getListSongs();

    }


    /**
     * 获取主页歌曲相关列表
     */
    public static void getListSongs() {
        try {

            JSONObject mJson = new JSONObject();
            JSONObject json = new JSONObject();

            try {
                json.put("function", "1036");
                json.put("version", "1.0");
                json.put("family_server_id", "f0f644a0d925");
//                json.put("songs_type", songTitle);

                mJson.put("request", json);
                System.out.print("DEBUG_LC" + ":" + mJson.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Request request = JsonRequestManager.getInstance().jsonRequest(
                    BASE_URL, mJson, new BaseParser() {
                        @Override
                        public BaseResponse parse(String jsonString) {
                            System.out.print("DEBUG_LC" + ":" + jsonString);
                            return null;
                        }
                    }, new ResponseListener() {
                        @Override
                        public void onResponse(Object result) {
                            System.out.print("DEBUG_LC" + ":" + result.toString());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            System.out.print("DEBUG_LC" + ":" + error.toString());
                        }

                        @Override
                        public void onFinish() {

                        }
                    });

            // 可以通过setTag方法为每一个Request添加tag
//            request.setTag(songTitle);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
