package com.volley.request.cache;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import java.io.IOException;
import java.util.Map;

import okhttp3.Response;

public interface OkHttpStack  {
    /**
     * 通过这个方法来去执行请求
     * @param var1  volley中的请求
     * @param var2  参数
     * @return OkHttp3的请求响应
     * @throws IOException
     * @throws AuthFailureError
     */
    Response performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureError;
}