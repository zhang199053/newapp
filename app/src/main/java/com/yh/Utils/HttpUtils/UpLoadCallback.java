package com.yh.Utils.HttpUtils;

import org.xutils.common.Callback;

/**
 * des:
 * Created by Administrator
 * on 2018/4/21 0021.
 */

public abstract class UpLoadCallback<T> {

    Class<T> mClass;

    public UpLoadCallback(Class<T> clazz) {
        this.mClass = clazz;
    }


    /**
     * @param result
     */
    public  abstract void onSuccess(T result);

    /**
     * @param e
     */
    public abstract void onError(Throwable e, String requestcode);

    /**
     * @param var1
     */
    protected abstract void onCancelled(Callback.CancelledException var1);

    /**
     * 完成
     */
    public  abstract void onFinished();

    public  abstract void onWaiting();

    public abstract void onStarted();

    public abstract void onLoading(long total, long current, boolean isDownloading, String requestcode);
}
