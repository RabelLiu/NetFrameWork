package com.volley.request.base.api.json;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.volley.JsonRequestImpl;
import com.volley.Tools;

/**
 * Json请求管理器.
 *
 * @author LC
 * @date 2015-10-09
 *
 */
public class JsonRequestManager {

	private static JsonRequestManager mInstance;
	private static JsonRequestImpl mJsonRequestImpl;

	// protected final static String URL =
	// "http://218.200.229.165:8091/Api.html";

	private JsonRequestManager() {
	}

	/**
	 * 初始化,接口使用前必须调用start方法.
	 *
	 * @param context
	 *            上下文.
	 */
	public static void start(Context context) {
		start(context, null);
	}

	/**
	 * 初始化,接口使用前必须调用start方法.
	 *
	 * @param context
	 *            上下文.
	 * @param url
	 *            服务器地址.
	 */
	public static void start(Context context, String url) {
		if (context == null) {
			return;
		}
		if (!TextUtils.isEmpty(url)) {
			Cfg.URL = url;
		}

		if (mJsonRequestImpl == null) {
			mJsonRequestImpl = new JsonRequestImpl(context);
		}

	}

	/**
	 * 初始化,接口使用前必须调用start方法.
	 *
	 * @param context
	 *            上下文.
	 * @param url
	 *            服务器地址.
	 * @param debug
	 *            是否打印Log.
	 */
	public static void start(Context context, String url, boolean debug) {
		start(context, url);
		Tools.DEBUG = debug;
	}

	/**
	 * 获取单实例.调用前必须调用start(context)方法.
	 *
	 * @return Json请求管理器.
	 */
	public static JsonRequestManager getInstance() {
		if (mInstance == null) {
			mInstance = new JsonRequestManager();
		}
		return mInstance;
	}

	/**
	 * 请求网络接口.
	 *
	 * @param url
	 *            接口地址.
	 * @param json
	 *            待上传的json字符串.
	 * @param params
	 *            请求头.
	 * @param responseListener
	 *            请求结果监听器.
	 */
	public void request(String url, String json, Map<String, String> params,
			BaseParser parser, ResponseListener responseListener) {
		mJsonRequestImpl.request(url, json, parser, params, responseListener);
	}

	/**
	 * 请求网络接口.
	 *
	 * @param url
	 *            接口地址.
	 * @param json
	 *            待上传的Json.
	 * @param parser
	 *            返回结果解析器.
	 * @param responseListener
	 *            返回结果监听器.
	 * @return 封装后的请求对象.
	 */
	public Request jsonRequest(String url, JSONObject json, BaseParser parser,
			ResponseListener responseListener) {
		return mJsonRequestImpl.request(url, json, parser, null,
				responseListener);
	}

	public Request jsonArrayRequest(String url, JSONArray json,
			BaseParser parser, ResponseListener responseListener) {
		return mJsonRequestImpl.requestArray(url, json, parser, null,
				responseListener);
	}

	/**
	 * 请求网络接口.
	 *
	 * @param url
	 *            接口地址.
	 * @param json
	 *            待上传的Json.
	 * @param params
	 *            待上传的参数.
	 * @param parser
	 *            返回结果解析器.
	 * @param responseListener
	 *            返回结果监听器.
	 */
	public void request(String url, JSONObject json,
			Map<String, String> params, BaseParser parser,
			ResponseListener responseListener) {
		mJsonRequestImpl.request(url, json, parser, params, responseListener);
	}

	/**
	 * 取消所有的请求序列,析构.
	 */
	public static void stop() {
		mJsonRequestImpl.stopAll();
	}
}
