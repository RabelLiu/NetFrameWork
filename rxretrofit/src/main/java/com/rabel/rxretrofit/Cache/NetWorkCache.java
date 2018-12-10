package com.rabel.rxretrofit.Cache;

import rx.Observable;

/**
 * Created by LC on 2016/12/26.
 */

public abstract class NetWorkCache<T> {
    public abstract Observable<T> get(String key, final Class<T> cls);
}
