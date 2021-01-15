package com.xsl.Model.Customer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xsl.Adpter.Customer.SearchTabLayoutAdapter;
import com.xsl.App.App;
import com.xsl.App.BaseActivity;
import com.xsl.Entity.CustomerDetailsTwoBean;
import com.xsl.Model.BusinessOpportunity.BusinessOpportunityFragment;
import com.xsl.Model.Contract.ContractFragment;
import com.xsl.Model.Finance.FinanceFragment;
import com.xsl.Model.Log.LogFragment;
import com.xsl.Model.MakeCall.CallActivity;
import com.xsl.R;
import com.xsl.Utils.AppToast;
import com.xsl.Utils.HttpUtils.BaseCallback;
import com.xsl.Utils.HttpUtils.HttpClient;
import com.xsl.ViewUtils.NavigationBar;

import org.xutils.common.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xsl.Utils.UrlUtils.Url.DOMAIN_Dynamic2;
import static com.xsl.ViewUtils.numToPxUtils.dip2px;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：客户详情聚合页面
 */
public class CustomerDetailsActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    NavigationBar nb_agre;
    private TabLayout tabs;
    private ViewPager viewPager;
    private String[] tiles = new String[]{"跟进记录", "商机", "合同", "回款"};
    //    切换子页面
    private Fragment[] mFragments = new Fragment[]{new LogFragment(), new BusinessOpportunityFragment(), new ContractFragment(), new FinanceFragment()};
    private SearchTabLayoutAdapter mSearchTabLayoutAdapter;
    //    更多，客户隐藏简介信息，收起
    private LinearLayout ll_more, ll_sq;
    //    编辑
    private LinearLayout tv_bj;
    //    拨打电话,
    private ImageView ll_customer_details;
    //    客户个人信息姓名，修改时间,负责人
    private static TextView tv_name, tv_update_time;
    private RecyclerView rv_list;
    private CustomerDetailAdpter customerDetailAdpter;
    private SmartRefreshLayout srl_refresh;
    //    客户id,客户名称，客户创建日期
    public static String customer_id, name, create_time;
    //    客户信息
    List<CustomerDetailsTwoBean.Data> data = new ArrayList<>();
    private LinearLayout ll_list;

    @Override
    protected int getContentView() {
        return R.layout.customer_details_activity;
    }

    @Override
    protected void initView() {
        customer_id = getIntent().getStringExtra("customer_id");
        name = getIntent().getStringExtra("name");
        create_time = getIntent().getStringExtra("create_time");
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.autoRefresh();
        srl_refresh.setOnRefreshListener(this);
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        ll_customer_details = findViewById(R.id.ll_customer_details);
        ll_customer_details.setOnClickListener(this);
        ll_more = findViewById(R.id.ll_more);
        ll_more.setOnClickListener(this);
        ll_sq = findViewById(R.id.ll_sq);
        ll_sq.setOnClickListener(this);
        tv_bj = findViewById(R.id.tv_bj);
        tv_bj.setOnClickListener(this);
//        客户名字
//        这是要重新显示的客户名字
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);
        tv_update_time = findViewById(R.id.tv_update_time);
        tv_update_time.setText(create_time);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
//        第二种是在适配器种，把当前的TEXTView控件穿过去，再赋值
        customerDetailAdpter = new CustomerDetailAdpter(mContext, R.layout.customer_details_list, data, tv_name);
        rv_list.setAdapter(customerDetailAdpter);
        ll_list = findViewById(R.id.ll_list);
        initViewPager(); // 初始化ViewPagerd
    }

    private void initViewPager() {
        mSearchTabLayoutAdapter = new SearchTabLayoutAdapter(getSupportFragmentManager(), this, tiles, mFragments);
        viewPager.setAdapter(mSearchTabLayoutAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bj:
                Intent intent = new Intent(mContext, EditCustomerActivity.class).putExtra("data", (Serializable) data);
                mContext.startActivity(intent);
                break;
//            case R.id.ll_fzr:
//                App.startActivity(mContext, PersonalDetailsActivity.class);
//                break;
//                更多
            case R.id.ll_more:
                ll_more.setVisibility(View.GONE);
                ll_list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ll_sq.setVisibility(View.VISIBLE);
                break;
//                收起
            case R.id.ll_sq:
                ll_more.setVisibility(View.VISIBLE);
                ll_sq.setVisibility(View.GONE);
                ll_list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, 180)));
                break;
            case R.id.ll_customer_details:
                String phone = customerDetailAdpter.phone;
                CallActivity.Call(this, mContext, phone, customer_id,0);
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

    public static void SetName(String name) {
        tv_name.setText("");
        tv_name.setText(name);
    }

    @Override
    public void onResume() {
        super.onResume();
        inData();

    }
}
