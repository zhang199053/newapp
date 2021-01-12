package com.xsl.Model.My.About;

import android.view.View;

import com.xsl.App.BaseActivity;
import com.xsl.R;
import com.xsl.ViewUtils.NavigationBar;

/**
 * Administrator  ：zhouyuru
 * 2020/10/29
 * Describe ：隐私政策
 */
public class PrivacyPolicyActivity extends BaseActivity {
    NavigationBar nb_agre;

    @Override
    protected int getContentView() {
        return R.layout.privacy_policy_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
    }

    @Override
    public void onClick(View v) {

    }
}
