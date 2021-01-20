package com.yh.Utils.HttpUtils;

import org.xutils.common.Callback;

/**
 * Des：
 *
 * @author zhouyuru
 * @date 2018/4/2
 */

public abstract class BaseCallback<T> {
    Class<T> mClass;

    public BaseCallback(Class<T> clazz) {
        this.mClass = clazz;
    }


    /**
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * @param msg
     */
    public abstract void onError(String msg);

    /**
     * @param var1
     */
    public abstract void onCancelled(Callback.CancelledException var1);

    /**
     * 完成
     */
    public abstract void onFinished();
}
