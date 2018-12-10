package com.rabel.rxretrofit.Bean;

import com.google.gson.Gson;

/**
 * Created by LC on 2016/12/28.
 */

public  class BaseBean {
    private long mCreatTime = System.currentTimeMillis();
    //过期时间
    private static final long EXPIRE_LIMIT = 5 * 60 * 1000;

    @Override
    public String toString() {

        try {
            return new Gson().toJson(this);
        } catch (Exception e) {
            return "";
        }


    }

    public Boolean isExpire() {
        return System.currentTimeMillis() - mCreatTime >= EXPIRE_LIMIT;
    }
}
