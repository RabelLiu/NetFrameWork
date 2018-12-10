package com.volley.request.base.api.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 默认解析器.当解析器为空时,用此默认解析器,返回原始字符串.
 *
 * @author LC
 */
public class EmptyParser extends BaseParser<EmptyResponse> {

    @Override
    public EmptyResponse parse(String paramString) {
        EmptyResponse er = new EmptyResponse();
        JSONObject jo;
        try {
            jo = new JSONObject(paramString);
            parseMsg(jo, er);
            er.json = paramString;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return er;
    }
}
