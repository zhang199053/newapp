package com.cyb.Dialog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cyb.App.BaseDialog;
import com.cyb.Model.Login.LoginActivity;
import com.cyb.R;
import com.cyb.Utils.AppToast;

/**
 * Administrator  ：zhouyuru
 * 2020/10/20
 * Describe ：退出页面，通用弹窗
 */
public class CurrencyDialog extends BaseDialog {
    public TextView tv_title, tv_qx, tv_qd;
    String Stitle, Sqx, Sqd;

    public CurrencyDialog(Context context, String title, String qx, String qd) {
        super(context);
        this.Stitle = title;
        this.Sqx = qx;
        this.Sqd = qd;
    }

    @Override
    protected int getContentView() {
        return R.layout.currency_dialog;
    }

    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_content);
        tv_qx = findViewById(R.id.tv_cancle);
        tv_qx.setOnClickListener(this);
        tv_qd = findViewById(R.id.tv_sure);
        tv_qd.setOnClickListener(this);
        tv_title.setText(Stitle);
        tv_qx.setText(Sqx);
        if (!TextUtils.isEmpty(Sqd)) {
            tv_qd.setText(Sqd);
            tv_qd.setVisibility(View.VISIBLE);
        } else {
            tv_qd.setVisibility(View.GONE);

        }

    }

    @Override
    protected void SetPosition() {
        //向上弹出
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(null);
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        lp.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                dismiss();
                break;
            default:
                break;
        }
    }
}
