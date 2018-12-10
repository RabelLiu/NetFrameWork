package com.volley.request.base.api.json;

import java.io.Serializable;

import org.json.JSONObject;


/**
 * 基类,解析封装后的对象.将服务器返回的Json串封装成对象.
 * 
 * @author LC
 * 
 */
public abstract class BaseResponse  {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5375804597574885028L;

//    public String returnecode;
//
//    /**
//     * 服务端返回的原始数据.
//     */
    public JSONObject originalResult;
}
