package com.volley.request.cache;

import android.os.SystemClock;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ByteArrayPool;
import com.volley.request.base.api.tools.DateUtils;

import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.impl.cookie.DateUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by LC on 2018/1/2.
 */

public class BasicNetworkOkHttp3 implements Network {
    protected static final boolean DEBUG;
    private static int SLOW_REQUEST_THRESHOLD_MS;
    private static int DEFAULT_POOL_SIZE;
    protected final OkHttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    static {
        DEBUG = VolleyLog.DEBUG;
        SLOW_REQUEST_THRESHOLD_MS = 3000;
        DEFAULT_POOL_SIZE = 4096;
    }

    public BasicNetworkOkHttp3(OkHttpStack httpStack) {
        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    public BasicNetworkOkHttp3(OkHttpStack httpStack, ByteArrayPool pool) {
        this.mHttpStack = httpStack;
        this.mPool = pool;
    }

    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        long requestStart = SystemClock.elapsedRealtime();

        while (true) {
            Response okHttpResponse = null;
            Object responseContents = null;
            HashMap responseHeaders = new HashMap();

            try {
                HashMap e = new HashMap();
                this.addCacheHeaders(e, request.getCacheEntry());
                okHttpResponse = this.mHttpStack.performRequest(request, e);


                int networkResponse1 = okHttpResponse.code();
                Map responseHeaders1 = convertHeaders(okHttpResponse.headers());
                if (networkResponse1 == 304) {
                    return new NetworkResponse(304, request.getCacheEntry().data, responseHeaders1, true);
                }

                byte[] responseContents1 = okHttpResponse.body().bytes();
                long requestLifetime = SystemClock.elapsedRealtime() - requestStart;
                this.logSlowRequests(requestLifetime, request, responseContents1, networkResponse1);
                if (networkResponse1 != 200 && networkResponse1 != 204) {
                    throw new IOException();
                }

                return new NetworkResponse(networkResponse1, responseContents1, responseHeaders1, false);
            } catch (SocketTimeoutException var12) {
                var12.printStackTrace();
                attemptRetryOnException("socket", request, new TimeoutError());
            } catch (ConnectTimeoutException var13) {
                attemptRetryOnException("connection", request, new TimeoutError());
            } catch (MalformedURLException var14) {
                throw new RuntimeException("Bad URL " + request.getUrl(), var14);
            } catch (IOException var15) {
                NetworkResponse networkResponse = null;
                if (okHttpResponse == null) {
                    throw new NoConnectionError(var15);
                }

                int statusCode1 = okHttpResponse.code();
                VolleyLog.e("Unexpected response code %d for %s", new Object[]{Integer.valueOf(statusCode1), request.getUrl()});
                if (responseContents == null) {
                    throw new NetworkError(networkResponse);
                }

                networkResponse = new NetworkResponse(statusCode1, (byte[]) responseContents, responseHeaders, false);
                if (statusCode1 != 401 && statusCode1 != 403) {
                    throw new ServerError(networkResponse);
                }

                attemptRetryOnException("auth", request, new AuthFailureError(networkResponse));
            }
        }
    }

    private void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, int status) {
        if (DEBUG || requestLifetime > (long) SLOW_REQUEST_THRESHOLD_MS) {
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", new Object[]{request, Long.valueOf(requestLifetime), responseContents != null ? Integer.valueOf(responseContents.length) : "null", Integer.valueOf(status), Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount())});
        }

    }

    private static void attemptRetryOnException(String logPrefix, Request<?> request, VolleyError exception) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();

        try {
            retryPolicy.retry(exception);
        } catch (VolleyError var6) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
            throw var6;
        }

        request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
    }

    private void addCacheHeaders(Map<String, String> headers, Cache.Entry entry) {
        if (entry != null) {
            if (entry.etag != null) {
                headers.put("If-None-Match", entry.etag);
            }

            if (entry.serverDate > 0L) {
                Date refTime = new Date(entry.serverDate);
//                headers.put("If-Modified-Since", DateUtils.formatDate(refTime));
            }

        }
    }

    private static Map<String, String> convertHeaders(Headers headers) {
        HashMap result = new HashMap();

        for (int i = 0; i < headers.size(); ++i) {
            result.put(headers.name(i), headers.value(i));
        }

        return result;
    }
}