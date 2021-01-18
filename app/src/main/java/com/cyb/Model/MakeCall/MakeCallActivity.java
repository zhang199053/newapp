package com.cyb.Model.MakeCall;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.cyb.App.App;
import com.cyb.App.BaseActivity;
import com.cyb.Entity.CallRemarkbean;
import com.cyb.Entity.CheckPermissionbean;
import com.cyb.Model.Customer.AddCustomerActivity;
import com.cyb.R;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.HttpUtils.BaseCallback;
import com.cyb.Utils.HttpUtils.HttpClient;
import com.cyb.Utils.MyDialog;
import com.cyb.Utils.SharedPrefUtil;
import com.cyb.ViewUtils.NavigationBar;
import com.cyb.ViewUtils.ProgressDialogUtil;

import static com.cyb.Utils.UrlUtils.Url.DOMAIN_CallPermission;
import static com.cyb.Utils.UrlUtils.Url.DOMAIN_CallRemark;
import static com.cyb.Utils.UrlUtils.Url.dialpanel_phone;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Administrator  ：zhouyuru
 * 2020/10/22
 * Describe ：
 */
public class MakeCallActivity extends BaseActivity {
    private ImageView iv_tg, iv_bd;
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_01, tv_0, tv_02;
    private LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5, ll_6, ll_7, ll_8, ll_9, ll_01, ll_0, ll_02;

    private TextView ev_phone;
    private String phone = "";
    private NavigationBar nb_agre;
    private View v_line;
    private int customer_id;
    private int callboo;
    private Dialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.make_call_activity;
    }

    @Override
    protected void initView() {
        v_line = findViewById(R.id.v_line);
        iv_tg = findViewById(R.id.iv_tg);
        iv_tg.setOnClickListener(this);
        iv_tg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (phone.length() > 0) {
                    phone = "";
                    ev_phone.setText(phone);
                    iv_tg.setVisibility(View.GONE);
                    v_line.setBackgroundResource(R.color.line_huise);

                }
                return true;
            }
        });
        iv_bd = findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);
        ev_phone = findViewById(R.id.ev_phone);
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);
        tv_6 = findViewById(R.id.tv_6);
        tv_7 = findViewById(R.id.tv_7);
        tv_8 = findViewById(R.id.tv_8);
        tv_9 = findViewById(R.id.tv_9);
        tv_0 = findViewById(R.id.tv_0);
        tv_01 = findViewById(R.id.tv_01);
        tv_02 = findViewById(R.id.tv_02);
        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
        ll_6 = findViewById(R.id.ll_6);
        ll_7 = findViewById(R.id.ll_7);
        ll_8 = findViewById(R.id.ll_8);
        ll_9 = findViewById(R.id.ll_9);
        ll_0 = findViewById(R.id.ll_0);
        ll_01 = findViewById(R.id.ll_01);
        ll_02 = findViewById(R.id.ll_02);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        ll_6.setOnClickListener(this);
        ll_7.setOnClickListener(this);
        ll_8.setOnClickListener(this);
        ll_9.setOnClickListener(this);
        ll_0.setOnClickListener(this);
        ll_01.setOnClickListener(this);
        ll_02.setOnClickListener(this);
        phone = dialpanel_phone;
        //String tt = "122";
        String tt = "";
        if (dialpanel_phone.length() == 11) {
            tt = phone.substring(0, 3) + "****" + phone.substring(8);
        }
        ev_phone.setText(tt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                showPhone(tv_1);
                break;
            case R.id.ll_2:
                showPhone(tv_2);

                break;
            case R.id.ll_3:
                showPhone(tv_3);

                break;
            case R.id.ll_4:
                showPhone(tv_4);

                break;
            case R.id.ll_5:
                showPhone(tv_5);

                break;
            case R.id.ll_6:
                showPhone(tv_6);

                break;
            case R.id.ll_7:
                showPhone(tv_7);

                break;
            case R.id.ll_8:
                showPhone(tv_8);

                break;
            case R.id.ll_9:
                showPhone(tv_9);

                break;
            case R.id.ll_0:
                showPhone(tv_0);

                break;
            case R.id.ll_01:
                showPhone(tv_01);

                break;
            case R.id.ll_02:
                showPhone(tv_02);
                break;
            case R.id.iv_tg:
                del();
                break;
            case R.id.iv_bd:
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.CALL_PHONE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (mContext.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        }
                    }
                }

                //判断客户是否存在
                checkPermissionHttp();

                break;
            default:
                break;
        }
    }


    private void checkPermissionHttp() {
        callboo = 0;
        Map<String, Object> parame = new HashMap<>();
        parame.put("phone", phone);
        parame.put("token", App.getToken());
        Log.e("http===/", DOMAIN_CallPermission + "==" + phone);
        HttpClient.getInstance().post(this, DOMAIN_CallPermission, parame, new BaseCallback<CheckPermissionbean>(CheckPermissionbean.class) {
            @Override
            public void onSuccess(CheckPermissionbean result) {

                String datas = new Gson().toJson(result);
                Log.e("json=====make", datas);
                if (result.getData().getIs_exist() == 1) {
                    //     调用拨打电话功能
                    String state = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Login_Db_state, "1");
                    Log.e("state====/", state);
                    if (!state.equals("5")) {
                        CallActivity.Call(MakeCallActivity.this, mContext, phone, "", 1);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //mContext.startActivity(intent);
                        startActivity(intent);
                    }
                    callboo = 1;
                    customer_id = result.getData().getCustomer_id();
                } else {
                    // AppToast.showToast("此用户不存在");
                    //addInfoDialogs("此用户不存在");
                    startActivity(new Intent(MakeCallActivity.this, AddCustomerActivity.class).putExtra("pho", phone));
                }

            }

            @Override
            public void onError(String msg) {
                AppToast.showToast(msg);
            }

            @Override
            public void onCancelled(Callback.CancelledException var1) {
            }

            @Override
            public void onFinished() {
                ProgressDialogUtil.getInstance().stopLoad();
            }
        });

    }

    private void callNotoDialogs(final int customer_id) {
        dialog = MyDialog.SignDialogShow(this, R.layout.callnoto_lay, 1.0f);
        final EditText contenttv = (EditText) dialog.findViewById(R.id.contenttv);
        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交备注
                //callContenthttp(contenttv.getText().toString(),customer_id);
                callContenthttp(contenttv.getText().toString(), customer_id);
            }
        });

        //设置点击屏幕不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void addInfoDialogs(String str) {
        final Dialog dialog = MyDialog.SignDialogShow(this, R.layout.addinfo_iay, 1.0f);
        final TextView msgtv = (TextView) dialog.findViewById(R.id.msgtv);
        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
        msgtv.setText(str);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交备注
                //callContenthttp(contenttv.getText().toString(),);
                startActivity(new Intent(MakeCallActivity.this, AddCustomerActivity.class).putExtra("pho", phone));
                dialog.dismiss();
            }
        });

        //设置点击屏幕不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void callContenthttp(String str, int customer_id) {

        Map<String, Object> parame = new HashMap<>();
        parame.put("remark_info", str);
        parame.put("token", App.getToken());
        parame.put("customer_id", customer_id + "");

        Log.e("http===/", DOMAIN_CallRemark + "==" + parame.toString());
        HttpClient.getInstance().post(this, DOMAIN_CallRemark, parame, new BaseCallback<CallRemarkbean>(CallRemarkbean.class) {
            @Override
            public void onSuccess(CallRemarkbean result) {
                AppToast.showToast(result.getInfo());
                dialog.dismiss();

            }

            @Override
            public void onError(String msg) {
                AppToast.showToast(msg);
            }

            @Override
            public void onCancelled(Callback.CancelledException var1) {
            }

            @Override
            public void onFinished() {
                ProgressDialogUtil.getInstance().stopLoad();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callboo == 1) {
            //   callNotoDialogs(customer_id);
            ev_phone.setText("");
        }
    }


    public void Call2x() {
//        申请拨打权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.CALL_PHONE};
            //验证是否许可权限
            for (String str : permissions) {
                if (mContext.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
//            清除手机呼叫转移
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:%23%2367%23"));
        mContext.startActivity(intent);

    }

    private void del() {
        if (!phone.equals("") && phone.length() > 0) {//判断输入框不为空，执行删除
            ev_phone.setText(phone.substring(0, phone.length() - 1));

        }
        phone = ev_phone.getText().toString();
        if (phone.length() == 0) {
            iv_tg.setVisibility(View.GONE);
        }
    }

    private void showPhone(TextView tv) {
        phone = phone + tv.getText().toString();
        ev_phone.setText(phone);
        if (phone.length() > 0) {
            iv_tg.setVisibility(View.VISIBLE);
            v_line.setBackgroundResource(R.color.sys_qs);
        } else {
            iv_tg.setVisibility(View.GONE);
            v_line.setBackgroundResource(R.color.line_huise);

        }
    }
}
