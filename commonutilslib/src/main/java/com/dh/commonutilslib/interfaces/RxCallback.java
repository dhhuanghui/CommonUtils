package com.dh.commonutilslib.interfaces;

/**
 * Created by dh on 2018/11/2.
 */

public interface RxCallback<T, E> {
    T onSubThreadExecute(E justParam);

    void onMainThreadExecute(T t);
}
