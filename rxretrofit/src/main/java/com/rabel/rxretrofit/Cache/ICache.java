package com.rabel.rxretrofit.Cache;

import rx.Observable;

/**
 * Created by LC on 2016/12/25.
 */

public interface ICache {

    <T> Observable<T> get(String key, Class<T> cls);

    <T> void put(String key, T t);

}
