package com.yh.Model.My;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yh.App.App;
import com.yh.App.BaseActivity;
import com.yh.Entity.PhoneSysEntity;
import com.yh.R;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.ViewUtils.NavigationBar;
import com.yh.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_EditEcpInfo;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_Logout;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_PhoneSys;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：通信配置
 */
public class SysSetActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    NavigationBar nb_agre;
    private SmartRefreshLayout srl_refresh;
    EditText ev_ECPID, ev_AccountSid, ev_APPID, ev_Token;
    ImageView iv_qd;
    private PhoneSysEntity.Ecpinfo bean;
    private String ecp_id, accountsid, app_id, token;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qd:
                if (check()) {
                    Update(ecp_id, accountsid, app_id, token);
                }
                break;
            default:
                break;
        }
    }

    private void Update(String ecp_id, String accountsid, String app_id, String token) {
        Map<String, Object> parame = new HashMap<>();
        ProgressDialogUtil.getInstance().startLoad(this);
        parame.put("ECPID", ecp_id);
        parame.put("AccountSid", accountsid);
        parame.put("APPID", app_id);
        parame.put("Token", token);
        parame.put("token", App.getToken());
        HttpClient.getInstance().post(mContext, DOMAIN_EditEcpInfo, parame, new BaseCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    AppToast.showToast(json.getString("info"));
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
                ShowView(bean);

            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.sys_set_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.autoRefresh();
        ev_ECPID = findViewById(R.id.ev_ECPID);
        ev_AccountSid = findViewById(R.id.ev_AccountSid);
        ev_APPID = findViewById(R.id.ev_APPID);
        ev_Token = findViewById(R.id.ev_Token);
        iv_qd = findViewById(R.id.iv_qd);
        iv_qd.setOnClickListener(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        ititData();
    }

    private void ititData() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_PhoneSys, parame, new BaseCallback<PhoneSysEntity>(PhoneSysEntity.class) {
                @Override
                public void onSuccess(PhoneSysEntity result) {
                    if (result.getInfo().equals("Success")) {
                        bean = result.getData().getEcpinfo();
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
                    ShowView(bean);

                }
            });
        }

    }

    private void ShowView(PhoneSysEntity.Ecpinfo bean) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        if (bean != null) {
            ev_ECPID.setText(bean.getEcpid());
            ev_AccountSid.setText(bean.getAccountSid());
            ev_APPID.setText(bean.getAppid());
            ev_Token.setText(bean.getToken());
        }
    }

    /**
     * 校验账号密码是否符合
     */
    public boolean check() {
        ecp_id = ev_ECPID.getText().toString().trim();
        accountsid = ev_AccountSid.getText().toString().trim();
        app_id = ev_APPID.getText().toString().trim();
        token = ev_Token.getText().toString().trim();
        if (TextUtils.isEmpty(ecp_id)) {
            AppToast.showToast("ecp_id不能为空");
            return false;
        }
        if (TextUtils.isEmpty(accountsid)) {
            AppToast.showToast("accountsid不能为空");
            return false;
        }
        if (TextUtils.isEmpty(app_id)) {
            AppToast.showToast("app_id不能为空");
            return false;
        }
        if (TextUtils.isEmpty(token)) {
            AppToast.showToast("token不能为空");
            return false;
        }
        return true;
    }
}
