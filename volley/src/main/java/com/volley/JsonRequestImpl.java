package com.volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.volley.request.base.api.json.BaseParser;
import com.volley.request.base.api.json.ResponseListener;
import com.volley.request.cache.MyVolley;
import com.volley.request.cache.OkHttp3Stack;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Json请求的实现类.
 *
 * @author TG.1
 * @date 2015-10-09
 */
public class JsonRequestImpl {

    protected static final String TAG = "JsonRequestImpl";

    // private Context mContext;
    private final RequestQueue requestQueue;

    //cookie存储
    private ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();


    /**
     * Constructor.
     */
    public JsonRequestImpl(Context context) {
        // this.mContext = context;
        // this.requestQueue = Volley.newRequestQueue(context);

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        HurlStack stack = new HurlStack();
//        this.requestQueue = MyVolley.newRequestQueue(context, stack,
//                cacheSize);


        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {//这里可以做cookie传递，保存等操作
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {//可以做保存cookies操作
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {//加载新的cookies
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        OkHttp3Stack okHttp3Stack = new OkHttp3Stack(mOkHttpClient);

        this.requestQueue = MyVolley.newRequestQueue(context, okHttp3Stack,
                cacheSize);
    }

    /**
     * 网络请求.
     *
     * @param url              接口地址.
     * @param jsonStr          待上传的Json数据.
     * @param params           参数.
     * @param responseListener 请求结果监听器.
     * @hide
     */
    public void request(String url, String jsonStr, BaseParser parser, final Map<String, String> params,
                        final ResponseListener responseListener) {
        if (TextUtils.isEmpty(jsonStr)) {
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid requesting params."));
                responseListener.onFinish();
            }
            return;
        }

        JSONObject json = null;

        try {
            json = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid json"));
                responseListener.onFinish();
            }
            return;
        }

        request(url, json, parser, params, responseListener);
    }

    public void requestArray(String url, String jsonStr, BaseParser parser, final Map<String, String> params,
                             final ResponseListener responseListener) {
        if (TextUtils.isEmpty(jsonStr)) {
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid requesting params."));
                responseListener.onFinish();
            }
            return;
        }

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid json"));
                responseListener.onFinish();
            }
            return;
        }

        requestArray(url, jsonArray, parser, params, responseListener);
    }

    public synchronized Request requestString(String url, String jsonStr, BaseParser parser,
                                              final Map<String, String> params, final ResponseListener responseListener) {
        if (TextUtils.isEmpty(jsonStr)) {
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid json"));
                responseListener.onFinish();
            }
            return null;
        }


        JsonStringRequest jsonRequest = new JsonStringRequest(url, jsonStr, parser, responseListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, 0.01f));
        jsonRequest.setShouldCache(true);

        // requestQueue.start();
        requestQueue.add(jsonRequest);

        return jsonRequest;
    }

    /**
     * 网络请求.
     *
     * @param url              接口地址.
     * @param json             待上传的Json数据.
     * @param params           参数.
     * @param responseListener 请求结果监听器.
     * @hide
     */
    public synchronized Request request(String url, JSONObject json, BaseParser parser,
                                        final Map<String, String> params, final ResponseListener responseListener) {
        if (json == null) {
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid json"));
                responseListener.onFinish();
            }
            return null;
        }

//        synchronized (json) {
//            json = checkSign(json);
//
//        }

        JsonStringRequest jsonRequest = new JsonStringRequest(url, json.toString(), parser, responseListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, 0.01f));
        jsonRequest.setShouldCache(true);

        // requestQueue.start();
        requestQueue.add(jsonRequest);

        return jsonRequest;
    }

    /**
     * 验签.
     *
     * @param requestParams 待计算的json数据.
     * @return 加入验签数据的Json.
     */
    private JSONObject checkSign(JSONObject requestParams) {
        Tools.logD(TAG, requestParams.toString());

        // 1.获取params数据.
        try {
            // JSONObject requestParams = json.getJSONObject("params");

            Map<String, String> ps = new HashMap<String, String>();
            Iterator<String> keys = requestParams.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                if (key.equals("feedbackContent")) {
                    continue;
                }

                String value = requestParams.get(key).toString();

                ps.put(key, value);
            }

            // 2.排序.
            Map<String, String> temp = Tools.sortMapByKey(ps);

            // 3.计算string
            StringBuffer stringB = new StringBuffer();

            for (Map.Entry<String, String> entry : temp.entrySet()) {
                stringB.append(entry.getKey()).append(Constants.SEPERATE_EQUEAL).append(entry.getValue())
                        .append(Constants.SEPERATE_AND);
            }
            stringB.append("key=").append(Constants.SIGN_SECRET_KEY);
            // stringB.deleteCharAt(stringB.length() - 1);

            System.out.println("this is stringB" + stringB.toString());
            // 4.计算MD5.
            String md5 = Tools.calcMD5(stringB.toString()).toUpperCase();

            // NativeManager mg = new NativeManager();
            // String jsonStr = json.toString().replaceAll("%","%%");
            // String sign = NativeManager.getSignedString(jsonStr);

            // 5.回写进params.
            requestParams.accumulate("sign", md5);

            // Tools.logD(TAG, "finalJson:" + json.toString());

            return requestParams;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestParams;
    }

    public synchronized Request requestArray(String url, JSONArray json, BaseParser parser,
                                             final Map<String, String> params, final ResponseListener responseListener) {
        if (json == null) {
            if (responseListener != null) {
                responseListener.onError(new VolleyError("invalid json"));
                responseListener.onFinish();
            }
            return null;
        }

        JsonStringRequest jsonRequest = new JsonStringRequest(url, json.toString(), parser, responseListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(18000, 5, 0.01f));
        jsonRequest.setShouldCache(false);

        requestQueue.add(jsonRequest);

        return jsonRequest;
    }

    /**
     * 停止所有请求.
     *
     * @hide
     */
    public void stopAll() {
        if (requestQueue == null) {
            return;
        }

        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });

        requestQueue.stop();
    }

    /**
     * 停止所有请求.
     *
     * @hide
     */
    public void stopTagAll(String tag) {
        if (requestQueue == null) {
            return;
        }
        requestQueue.cancelAll(tag);
//        requestQueue.stop();
    }
}
