package com.yh.Model.CustomerPool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yh.Adpter.Customer.SearchTabLayoutAdapter;
import com.yh.App.App;
import com.yh.App.BaseActivity;
import com.yh.Entity.CustomerDetailsTwoBean;
import com.yh.Model.BusinessOpportunity.BusinessOpportunityFragment;
import com.yh.Model.Contract.ContractFragment;
import com.yh.Model.Customer.CustomerDetailAdpter;
import com.yh.Model.Customer.CutomerInDicidualActivity;
import com.yh.Model.Enclosure.EnclosureFragment;
import com.yh.Model.Finance.FinanceFragment;
import com.yh.Model.Log.LogFragment;
import com.yh.Model.Login.LoginActivity;
import com.yh.Model.My.PersonalDetailsActivity;
import com.yh.R;
import com.yh.Utils.ActivityMgr;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.Utils.SharedPrefUtil;
import com.yh.ViewUtils.NavigationBar;
import com.yh.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_Dynamic2;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_Logout;
import static com.yh.Utils.UrlUtils.Url.DOMAIN_Receive;

/**
 * Administrator  ：zhouyuru
 * 2020/10/12
 * Describe ：用户池聚合页
 */
public class CustomerPoolDetailsActivity extends BaseActivity implements OnRefreshListener {
    NavigationBar nb_agre;
    private RecyclerView rv_list;
    private static CustomerDetailAdpter customerDetailAdpter;
    private SmartRefreshLayout srl_refresh;
    //    客户id
    private String customer_id, name, create_time;
    List<CustomerDetailsTwoBean.Data> data = new ArrayList<>();
    TextView tv_name, tv_update;
    //   认领
    ImageView iv_rl;
    private int pos;

    @Override
    protected int getContentView() {
        return R.layout.customer_pool_details_activity;
    }

    @Override
    protected void initView() {
        customer_id = getIntent().getStringExtra("customer_id");
        name = getIntent().getStringExtra("name");
        create_time = getIntent().getStringExtra("create_time");
        pos = getIntent().getIntExtra("pos", 0);
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.autoRefresh();
        srl_refresh.setOnRefreshListener(this);
        rv_list = findViewById(R.id.rv_list);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_update = findViewById(R.id.tv_update);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        customerDetailAdpter = new CustomerDetailAdpter(mContext, R.layout.customer_details_list, data, tv_name);
        rv_list.setAdapter(customerDetailAdpter);
        iv_rl = findViewById(R.id.iv_rl);
        iv_rl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_rl:
                CustomerPoolListFragment.logout(customer_id, pos,this);
                break;
            default:
                break;

        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData();
    }

    private void inData() {
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("id", customer_id);
            HttpClient.getInstance().post(mContext, DOMAIN_Dynamic2, parame, new BaseCallback<CustomerDetailsTwoBean>(CustomerDetailsTwoBean.class) {
                @Override
                public void onSuccess(CustomerDetailsTwoBean result) {
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            data.clear();
                        }
                        if (result.getData() != null) {
                            data.addAll(result.getData());

                        }
                    } else {
                        AppToast.showToast(result.getInfo());
                    }


                }

                @Override
                public void onError(String msg) {
                    if (!srl_refresh.isLoading()) {
                    }
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    Show(data);
                }
            });
        }
    }

    private void Show(List<CustomerDetailsTwoBean.Data> bean) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }

        if (bean != null) {
            customerDetailAdpter.notifyDataSetChanged();
        }
    }


}
