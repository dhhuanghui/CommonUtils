package com.dh.commonutilslib;


import com.dh.commonutilslib.interfaces.RxCallback;
import com.dh.commonutilslib.interfaces.RxCallback2;
import com.dh.commonutilslib.interfaces.RxCallbackSub;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dh on 2018/11/2.
 */

public class RxUtil {

    public static <T, E> Subscription execute(E justParam, final RxCallback<T, E> callback) {
        return Observable.just(justParam)
                .subscribeOn(Schedulers.io())
                .map(new Func1<E, T>() {
                    @Override
                    public T call(E justParam) {
                        if (callback != null) {
                            return callback.onSubThreadExecute(justParam);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onMainThreadExecute(o);
                        }
                    }
                });
    }

    public static <T> Subscription execute(final RxCallback<T, Integer> callback) {
        return Observable.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, T>() {
                    @Override
                    public T call(Integer justParam) {
                        if (callback != null) {
                            return callback.onSubThreadExecute(justParam);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onMainThreadExecute(o);
                        }
                    }
                });
    }

    public static <T> Subscription execute(final RxCallback2<T, Integer> callback) {
        return Observable.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, T>() {
                    @Override
                    public T call(Integer justParam) {
                        if (callback != null) {
                            return callback.onSubThreadExecute(justParam);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.onMainThreadExecuteError(-1, e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onMainThreadExecute(o);
                        }
                    }
                });
    }

    /**
     * 执行在子线程回调
     *
     * @param callback
     */
    public static Subscription executeSubThread(final RxCallbackSub callback) {
        return Observable.just(1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (callback != null) {
                            callback.onSubThreadExecute();
                        }
                    }
                });
    }

}
