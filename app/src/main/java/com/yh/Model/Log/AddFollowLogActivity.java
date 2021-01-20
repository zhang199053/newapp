package com.yh.Model.Log;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.App.App;
import com.yh.App.BaseActivity;
import com.yh.Dialog.SelectDateDialog;
import com.yh.Dialog.SelectGeneralDialog;
import com.yh.Dialog.SelectGeneralDialogT;
import com.yh.Entity.CallStateEntity;
import com.yh.Entity.CustomerBean;
import com.yh.Entity.FollowBean;
import com.yh.Entity.FollowStateEntity;
import com.yh.Entity.LoginEntity;
import com.yh.Main.MainActivity;
import com.yh.Model.Customer.CustomerDetailsActivity;
import com.yh.Model.Login.LoginActivity;
import com.yh.R;
import com.yh.Utils.ActivityMgr;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.Utils.MD5Utils;
import com.yh.Utils.SharedPrefUtil;
import com.yh.Utils.UrlUtils.Url;
import com.yh.ViewUtils.NavigationBar;
import com.yh.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_Index;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_LogAdd;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_Login;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_Logstatus;

/**
 * Administrator  ：zhouyuru
 * 2020/10/26
 * Describe ：
 */
public class AddFollowLogActivity extends BaseActivity {
    LinearLayout ll_log_state, ll_next_time, ll_sj;
    TextView tv_time, tv_state;
    ImageView iv_qd;
    NavigationBar nb_agre;
    private ArrayList<FollowBean.States> list = new ArrayList<>();
    TextView tv_state_id;
    EditText et_content;
    String content, state_id, time;

    @Override
    protected int getContentView() {
        return R.layout.add_follow_log_activity;
    }

    @Override
    protected void initView() {
        ll_log_state = findViewById(R.id.ll_log_state);
        ll_log_state.setOnClickListener(this);
        ll_next_time = findViewById(R.id.ll_next_time);
        ll_next_time.setOnClickListener(this);
        ll_sj = findViewById(R.id.ll_sj);
        ll_sj.setOnClickListener(this);
        iv_qd = findViewById(R.id.iv_qd);
        iv_qd.setOnClickListener(this);
        tv_time = findViewById(R.id.tv_time);
        tv_state = findViewById(R.id.tv_state);
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        tv_state_id = findViewById(R.id.tv_state_id);
        et_content = findViewById(R.id.et_content);
        LoadState();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_log_state:
                if (list != null && list.size() > 0) {
                    SelectGeneralDialogT selectGeneralDialog1 = new SelectGeneralDialogT(mContext, list, tv_state, "选择跟进类型", tv_state_id);
                    selectGeneralDialog1.show();
                }
                break;
            case R.id.ll_next_time:
                SelectDateDialog dateDialog = new SelectDateDialog(mContext, tv_time);
                dateDialog.show();
                break;
            case R.id.ll_sj:
                break;
            case R.id.iv_qd:
                if (check()) {
                    Followuplog();
                }

                break;
            default:
                break;
        }
    }


    private void LoadState() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_Logstatus, parame, new BaseCallback<FollowBean>(FollowBean.class) {
                @Override
                public void onSuccess(FollowBean result) {
                    list.addAll(result.getList());
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
                }
            });
        }

    }

    //添加跟进日志
    private void Followuplog() {
        String customer_id = CustomerDetailsActivity.customer_id;
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(state_id) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(this);
            Map<String, Object> parame = new HashMap<>();
            parame.put("content", content);
            parame.put("status_id", state_id);
            parame.put("nextstep_time", time);
            parame.put("id", customer_id);
            parame.put("module", "customer");
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_LogAdd, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        AppToast.showToast(json.getString("info"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finish();

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

    }

    /**
     * 校验账号密码是否符合
     */
    public boolean check() {
        content = et_content.getText().toString().trim();
        state_id = tv_state_id.getText().toString().trim();
        time = tv_time.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            AppToast.showToast("内容不能为空");
            return false;
        }
        if (TextUtils.isEmpty(state_id)) {
            AppToast.showToast("类型不能为空");
            return false;
        }
        if (TextUtils.isEmpty(time)) {
            AppToast.showToast("跟进时间不能为空");
            return false;
        }
        return true;
    }
}
