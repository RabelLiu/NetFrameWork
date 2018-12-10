package com.volley.request.base.api.json;

import com.android.volley.VolleyError;

/**
 * 请求结果监听.
 * 
 * @author TG.1
 * @date 2015-10-12
 */
public interface ResponseListener<T> {

    /**
     * 反馈请求的结果.
     * 
     * @param result
     *            封装后的对象.
     */
    void onResponse(T result);

    /**
     * 返回错误信息.
     * 
     * @param error
     *            包含错误信息的对象.
     */
    void onError(VolleyError error);

    /**
     * 请求结束.
     */
    void onFinish();
}
