package com.yh.Model.My;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yh.App.App;
import com.yh.App.BaseActivity;
import com.yh.Model.MakeCall.CallActivity;
import com.yh.R;
import com.yh.Utils.AppToast;
import com.yh.Utils.SharedPrefUtil;
import com.yh.ViewUtils.NavigationBar;

import static com.yh.Utils.UrlUtils.Other.Number_All;
import static com.yh.Utils.UrlUtils.Other.Number_ZX;


/**
 * Administrator  ：zhouyuru
 * 2020/11/10
 * Describe ：设置呼叫转移
 */
public class SetCallActivity extends BaseActivity {
    private NavigationBar nb_agre;
    private ImageView iv_qd;
    private CheckBox mCheckBox_call01, mCheckBox_call02, mCheckBox_call03, mCheckBox_call04, mCheckBox_call05, mCheckBox_03, mCheckBox_04, mCheckBox_call06;
    private LinearLayout ll_set_phone;
    private EditText ev_phone;
    private LinearLayout ll_ss;
    private LinearLayout ll_set_zb;

    @Override
    protected int getContentView() {
        return R.layout.set_call_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        iv_qd = findViewById(R.id.iv_qd);
        iv_qd.setOnClickListener(this);
        ll_set_phone = findViewById(R.id.ll_set_phone);
        ll_ss = findViewById(R.id.ll_ss);
        ll_ss.setOnClickListener(this);
        ev_phone = findViewById(R.id.ev_phone);
        ll_set_zb = findViewById(R.id.ll_set_zb);
        mCheckBox_call01 = findViewById(R.id.mCheckBox_call01);
        mCheckBox_call02 = findViewById(R.id.mCheckBox_call02);
        mCheckBox_call03 = findViewById(R.id.mCheckBox_call03);
        mCheckBox_call04 = findViewById(R.id.mCheckBox_call04);
        mCheckBox_call05 = findViewById(R.id.mCheckBox_call05);
        mCheckBox_call06 = findViewById(R.id.mCheckBox_call06);

        mCheckBox_03 = findViewById(R.id.mCheckBox_03);
        mCheckBox_04 = findViewById(R.id.mCheckBox_04);

        //mCheckBox_call03.setVisibility((View.GONE));
        mCheckBox_call01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call02.setChecked(false);
                    mCheckBox_call03.setChecked(false);
                    mCheckBox_call04.setChecked(false);
                    mCheckBox_call05.setChecked(false);
                    mCheckBox_call06.setChecked(false);
                }
            }
        });

        mCheckBox_call02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call01.setChecked(false);
                    mCheckBox_call03.setChecked(false);
                    mCheckBox_call04.setChecked(false);
                    mCheckBox_call05.setChecked(false);
                    mCheckBox_call06.setChecked(false);
                }
            }
        });
        mCheckBox_call06.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call01.setChecked(false);
                    mCheckBox_call02.setChecked(false);
                    mCheckBox_call03.setChecked(false);
                    mCheckBox_call04.setChecked(false);
                    mCheckBox_call05.setChecked(false);
                }
            }
        });
        mCheckBox_call04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call02.setChecked(false);
                    mCheckBox_call03.setChecked(false);
                    mCheckBox_call01.setChecked(false);
                    mCheckBox_call05.setChecked(false);
                    mCheckBox_call06.setChecked(false);
                }
            }
        });
        mCheckBox_call05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call01.setChecked(false);
                    mCheckBox_call02.setChecked(false);
                    mCheckBox_call03.setChecked(false);
                    mCheckBox_call04.setChecked(false);
                    mCheckBox_call06.setChecked(false);
                }
            }
        });

        mCheckBox_call03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_call01.setChecked(false);
                    mCheckBox_call02.setChecked(false);
                    mCheckBox_call04.setChecked(false);
                    mCheckBox_call05.setChecked(false);
                    mCheckBox_call06.setChecked(false);

                    ll_set_phone.setVisibility(View.VISIBLE);
                    ll_set_zb.setVisibility(View.VISIBLE);
                } else {
                    ll_set_phone.setVisibility(View.GONE);
                    ll_set_zb.setVisibility(View.GONE);

                }
            }
        });


        mCheckBox_03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_04.setChecked(false);
                }
            }
        });
        mCheckBox_04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox_03.setChecked(false);
                }
            }
        });

        ShowView();
    }

    private void ShowView() {
        String index = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Login_Db_state, "1");
        String HostPhone = SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_Host_phone, "1");
        String ZBState = SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_Zb_State, "21");
        if (index.equals("1")) {
            mCheckBox_call01.setChecked(true);
        }
        if (index.equals("2")) {
            mCheckBox_call02.setChecked(true);
        }

        if (index.equals("3")) {
            mCheckBox_call03.setChecked(true);
            ll_set_phone.setVisibility(View.VISIBLE);
            ev_phone.setText(HostPhone);
            ll_set_zb.setVisibility(View.VISIBLE);
            if (ZBState.equals("21")) {
                mCheckBox_03.setChecked(true);
                mCheckBox_04.setChecked(false);
            } else if (ZBState.equals("67")) {
                mCheckBox_04.setChecked(true);
                mCheckBox_03.setChecked(false);
            }
        }
        if (index.equals("4")) {
            mCheckBox_call04.setChecked(true);
        }
        if (index.equals("5")) {
            mCheckBox_call05.setChecked(true);
        }

        if (index.equals("6")) {
            mCheckBox_call06.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qd:
                if (mCheckBox_call01.isChecked()) {
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "1");
                    finish();
                    AppToast.showToast("设置成功！");
                }

                if (mCheckBox_call02.isChecked()) {
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "2");
                    finish();
                    AppToast.showToast("设置成功！");
                }

                if (mCheckBox_call03.isChecked()) {
                    String phone = ev_phone.getText().toString();
                    if (!TextUtils.isEmpty(phone)) {
//                        设置拨打方式
                        SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "3");
//                        设置本机号码
                        SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_Host_phone, phone);
                        if (mCheckBox_03.isChecked()) {
//                            设置全部转播方式
                            SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_Zb_State, "21");
                        } else if (mCheckBox_04.isChecked()) {
//                            设置占线转播
                            SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_Zb_State, "67");
                        }
                        finish();
                        AppToast.showToast("设置成功！");
                    } else {
                        AppToast.showToast("请输入本机号码");
                    }
                }

                if (mCheckBox_call04.isChecked()) {
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "4");
                    finish();
                    AppToast.showToast("设置成功！");
                }
                if (mCheckBox_call05.isChecked()) {
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "5");
                    finish();
                    AppToast.showToast("设置成功！");
                }

                if (mCheckBox_call06.isChecked()) {
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.Login_Db_state, "6");
                    finish();
                    AppToast.showToast("设置成功！");
                }
                break;
            case R.id.ll_ss:
                CallActivity.ClearCall(this, mContext);
                break;
            default:
                break;
        }
    }

//    //清除来电转接
//    public void Call() {
//        //android6版本获取动态权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {Manifest.permission.CALL_PHONE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (mContext.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return;
//                }
//            }
//        }
////           清除设置来电转接
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:%23%2321%23"));
//        mContext.startActivity(intent);
//    }
}
