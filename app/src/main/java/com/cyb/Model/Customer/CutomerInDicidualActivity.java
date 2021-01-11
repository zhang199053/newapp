package com.cyb.Model.Customer;

import android.view.View;
import android.widget.LinearLayout;

import com.cyb.App.App;
import com.cyb.App.BaseActivity;
import com.cyb.Model.My.PersonalDetailsActivity;
import com.cyb.R;
import com.cyb.ViewUtils.NavigationBar;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：用户个人详情
 */
public class CutomerInDicidualActivity extends BaseActivity implements View.OnClickListener {
    NavigationBar nb_agre;
    LinearLayout ll_czr;
    LinearLayout ll_fzr;

    @Override
    protected int getContentView() {
        return R.layout.custmoer_indicidual_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        ll_czr = findViewById(R.id.ll_czr);
        ll_czr.setOnClickListener(this);
        ll_fzr = findViewById(R.id.ll_fzr);
        ll_fzr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fzr:
                App.startActivity(mContext,PersonalDetailsActivity.class);
                break;
            case R.id.ll_czr:
                App.startActivity(mContext,PersonalDetailsActivity.class);
                break;
            default:
                break;


        }
    }
}
