/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.volley;

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.volley.request.base.api.json.BaseParser;
import com.volley.request.base.api.json.BaseResponse;
import com.volley.request.base.api.json.EmptyParser;
import com.volley.request.base.api.json.ResponseListener;

/**
 * A request for retrieving a response body at a given URL, allowing for an
 * optional to be passed in as part of the request body.
 */
public class JsonStringRequest extends JsonRequest<BaseResponse> {
    private static final String TAG = JsonStringRequest.class.getSimpleName();
    private final BaseParser mParser;
    private final ResponseListener mIResponseListener;
    private String jsonRequest;

    public JsonStringRequest(String url, String jsonRequest, BaseParser parser, final ResponseListener responseListener) {
        super(Method.POST, url, (jsonRequest == null) ? null : jsonRequest, new Listener<BaseResponse>() {

            @Override
            public void onResponse(BaseResponse response) {
                if (responseListener != null) {
                    responseListener.onResponse(response);
                    responseListener.onFinish();
                }
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onError(error);

                    responseListener.onFinish();
                }
            }
        });

        this.mParser = parser;
        this.mIResponseListener = responseListener;
        this.jsonRequest = Tools.calcMD5(jsonRequest);

        Tools.logD(TAG, "-->url:" + url);
        Tools.logD(TAG, "-->jsonRequest:" + jsonRequest);
    }

    @Override
    protected void deliverResponse(BaseResponse response) {

        mIResponseListener.onResponse(response);
        mIResponseListener.onFinish();
        System.out.println("this is deliverResponse");
        // super.deliverResponse(response);
    }

    /*
     * 没有网的情况也是出现了异常，然后就会调用到deliverError
     */
    @Override
    public void deliverError(VolleyError error) {
        Log.i("DEBUG_ER", "THIS IS deliverError0");
        if (error instanceof NoConnectionError) {
            Cache.Entry entry = this.getCacheEntry();
            if (entry != null) {
                Response<BaseResponse> response = parseNetworkResponse(new NetworkResponse(entry.data,
                        entry.responseHeaders));
                deliverResponse(response.result);
                Log.i("DEBUG_ER", "THIS IS deliverError");
                return;
            }
        }
        super.deliverError(error);
    }

    @Override
    protected Response<BaseResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "utf-8");

            // jsonString = fromEncodedUnicode(jsonString.toCharArray(), 0,
            // jsonString.length());
            Tools.logD(TAG, "-->result:" + jsonString);

            // 如果解析器非空,则解析.
            if (mParser != null) {
                BaseResponse baseResponse = mParser.parse(jsonString);
                Tools.logD(TAG, "parser:" + mParser.getClass().getSimpleName());
                if (baseResponse != null) {
                    Tools.logD(TAG, "baseResponse:" + baseResponse.getClass().getSimpleName());
                }
                //
                return Response.success(baseResponse, HttpHeaderParser.parseCacheHeaders(response));

            } else {
                // 否则,返回String.
                EmptyParser parser = new EmptyParser();
                BaseResponse baseResponse = parser.parse(jsonString);
                return Response.success(baseResponse, HttpHeaderParser.parseCacheHeaders(response));

            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public String getCacheKey() {
        // TODO Auto-generated method stub
        return super.getCacheKey() + jsonRequest;
    }

    // 解中文乱码（-->utf-8）
    private String fromEncodedUnicode(char[] in, int off, int len) {
        char aChar;
        char[] out = new char[len];
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = aChar;
            }
        }
        return new String(out, 0, outLen);
    }

}
