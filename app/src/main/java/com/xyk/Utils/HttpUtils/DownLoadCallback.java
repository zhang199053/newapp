package com.xyk.Utils.HttpUtils;

import org.xutils.common.Callback;

/**
 * des:
 * Created by Administrator
 * on 2018/4/21 0021.
 */

public interface DownLoadCallback<T> {

    /**
     * @param result
     */
    void onSuccess(T result, String requestcode);

    /**
     * @param e
     */
    void onError(Throwable e, boolean isoncallback, String requestcode);

    /**
     * @param var1
     */
    void onCancelled(Callback.CancelledException var1);

    /**
     * 完成
     */
    void onFinished();

    void onWaiting();

    void onStarted();

    void onLoading(long total, long current, boolean isDownloading, String requestcode);
}
