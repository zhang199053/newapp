package com.yh.Model.My;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yh.App.App;
import com.yh.App.BaseActivity;
import com.yh.Entity.PhoneSysEntity;
import com.yh.Model.Login.LoginActivity;
import com.yh.R;
import com.yh.Utils.ActivityMgr;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.Utils.MD5Utils;
import com.yh.Utils.SharedPrefUtil;
import com.yh.ViewUtils.NavigationBar;
import com.yh.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_EditEcpInfo;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_EditPassword;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_PhoneSys;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：修改密码
 */
public class UpdatePsdActivity extends BaseActivity implements View.OnClickListener {
    NavigationBar nb_agre;
    EditText ev_pwd, ev_new_pwd, ev_new_pwd2;
    String pwd, new_pwd, new_pwd2;
    ImageView iv_qd;


    @Override
    protected int getContentView() {
        return R.layout.update_psd_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        ev_pwd = findViewById(R.id.ev_pwd);
        ev_new_pwd = findViewById(R.id.ev_new_pwd);
        ev_new_pwd2 = findViewById(R.id.ev_new_pwd2);
        iv_qd = findViewById(R.id.iv_qd);
        iv_qd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qd:
                if (check()) {
                    Update(pwd, new_pwd, new_pwd2);
                }
                break;
            default:
                break;
        }
    }

    private void Update(String pwd, String new_pwd, String new_pwd2) {
        ProgressDialogUtil.getInstance().startLoad(this);
        Map<String, Object> parame = new HashMap<>();
        parame.put("old_password", MD5Utils.MD5(pwd));
        parame.put("new_password", MD5Utils.MD5(new_pwd));
        parame.put("confirm_password", MD5Utils.MD5(new_pwd2));
        parame.put("token", App.getToken());
        HttpClient.getInstance().post(mContext, DOMAIN_EditPassword, parame, new BaseCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    AppToast.showToast(json.getString("info"));
                    SharedPrefUtil.getInstance().clearUserInfor();
                    SharedPrefUtil.getInstance().putBool(SharedPrefUtil.IS_LOGIN, false);
                    ActivityMgr.getInstance().removeAll();
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
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


    /**
     * 校验账号密码是否符合
     */
    public boolean check() {
        pwd = ev_pwd.getText().toString().trim();
        new_pwd = ev_new_pwd.getText().toString().trim();
        new_pwd2 = ev_new_pwd2.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            AppToast.showToast("旧密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(new_pwd)) {
            AppToast.showToast("新密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(new_pwd2)) {
            AppToast.showToast("请再次确认密码");
            return false;
        }
        if (!new_pwd.equals(new_pwd2)) {
            AppToast.showToast("两次新密码不一致");
            return false;
        }
        return true;
    }
}
