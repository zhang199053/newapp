package com.cyb.App;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.cyb.R;
import com.cyb.ViewUtils.NavigationBar;


/**
 * Des：Activity基类
 *
 * @author zhouyuru
 * @date 2018/4/2
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationBar.OnBackListen , View.OnClickListener{

    protected static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = this;
        initView();
        //设置不同android不同版本的状态栏适配（需要放在setContentView（)后面）
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));

    }

    protected abstract int getContentView();

    protected abstract void initView();

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 用户点击导航栏返回finish()
     */
    @Override
    public void back() {
        finish();
    }

    /**
     * 用户点虚拟键盘返回finish()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    /**
     * 带动画的切换activity
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
