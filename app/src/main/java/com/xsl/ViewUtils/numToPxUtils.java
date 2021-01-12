package com.xsl.ViewUtils;

import android.content.Context;

/**
 * Administrator  ：zhouyuru
 * 2020/11/4
 * Describe ：
 */
public class numToPxUtils {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
