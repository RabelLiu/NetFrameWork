package com.volley.request.base.api.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;


/**
 * 基类,Json解析器.解析服务端返回的Json数据.
 *
 * @param <T>
 * @author LC
 */
public abstract class BaseParser<T> {

    /**
     * Json解析,将Json解析后的属性集合封装成对象.
     *
     * @param jsonString 服务端返回的数据.
     * @return 封装成JavaBean后的属性集合.
     */
    public abstract BaseResponse parse(String jsonString);

    /**
     * 解析基础信息.
     *
     * @param jo 封装后的Json对象.
     * @param br 封装成JavaBean后的属性集合.
     */
    public void parseMsg(JSONObject jo, BaseResponse br) {

        br.originalResult = jo;
//        if (jo != null) {
//            try {
//                if(jo.has("returnecode")){
//                    br.returnecode = jo.getString("returnecode");
//                }
//                br.originalResult = jo;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

    }


    public void parseMsg(JSONObject jo, String key, BaseResponse br) {
        if (jo == null || TextUtils.isEmpty(key)) {
            return;
        }
//        try {
//            if(jo.has("returnecode")){
//                br.returnecode = jo.getString("returnecode");
//            }
//            br.originalResult = jo;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}
