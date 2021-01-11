package com.yh.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.yh.App.App;


/**
 * 封装Toast工具类
 */

public class AppToast {


    private static Toast toast;

    private static Toast initToast(CharSequence message, int duration) {
        if (ObjectUtil.isNullOrEmpty(message)) {
            return toast;
        } else {
            if (toast == null) {
                toast = Toast.makeText(App.getAppContext(), message, duration);
            } else {
                toast.setText(message);
                toast.setDuration(duration);
            }
        }
        return toast;
    }

    private AppToast() {
    }

    /**
     * @param text
     * @description
     */
    public static void showToast(String text) {
        if (text == null || text.trim().length() == 0) {
            return;
        }
        initToast(text, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param text
     * @param duration
     * @description
     */
    public static void showToast(String text, int duration) {
        initToast(text, duration).show();
    }


    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }


}

