package com.yhj.App;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.yhj.R;

/**
 * Administrator  ：zhouyuru
 * 2020/10/9
 * Describe ：弹窗父类
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    protected Context mContext;

    public BaseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = getContext();
        initView();
        SetPosition();
    }

    protected abstract int getContentView();

    //初始化页面
    protected abstract void initView();

    //设置弹窗位置
    protected abstract void SetPosition();
}
