package com.yh.Model.My.About;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.App.BaseActivity;
import com.yh.R;

/**
 * Administrator  ：zhouyuru
 * 2020/10/29
 * Describe ：
 */
public class AboutusActivity extends BaseActivity {
    LinearLayout iv_back;
    TextView tv_fwtk, tv_yszc;

    @Override
    protected int getContentView() {
        return R.layout.about_us_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_yszc = findViewById(R.id.tv_yszc);
        tv_yszc.setOnClickListener(this);
        tv_fwtk = findViewById(R.id.tv_fwtk);
        tv_fwtk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fwtk:
//                服务条款
                mContext.startActivity(new Intent(mContext, TermsOfServiceActivity.class));
                break;
            case R.id.tv_yszc:
//                隐私政策
                mContext.startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
                break;
            default:
                break;
        }
    }
}
