package com.ymz.Model.Log;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ymz.Adpter.Customer.CustomerLogAdpter;
import com.ymz.App.App;
import com.ymz.App.BaseFragment;
import com.ymz.Entity.CustomerDetailsTwoBean;
import com.ymz.Entity.CustomerLogEntity;
import com.ymz.Model.Customer.CustomerDetailAdpter;
import com.ymz.Model.Customer.CustomerDetailsActivity;
import com.ymz.R;
import com.ymz.Utils.AppToast;
import com.ymz.Utils.HttpUtils.BaseCallback;
import com.ymz.Utils.HttpUtils.HttpClient;
import com.ymz.ViewUtils.AbnormalView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ymz.Utils.UrlUtils.Url.DOMAIN_Dynamic2;
import static com.ymz.Utils.UrlUtils.Url.DOMAIN_Loglist;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：日志页面
 */
public class LogFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {
    LinearLayout ll_add_gjjl;
    private RecyclerView rv_list;
    private SmartRefreshLayout srl_refresh;

    private CustomerLogAdpter customerLogAdpter;
    private int page = 1;
    private ArrayList<CustomerLogEntity.LogList> lists = new ArrayList<CustomerLogEntity.LogList>();
    AbnormalView av_nodata;

    @Override
    protected int setContentView() {
        return R.layout.log_fragment;
    }

    @Override
    protected void init() {
        ll_add_gjjl = rootView.findViewById(R.id.ll_add_gjjl);
        ll_add_gjjl.setOnClickListener(this);
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
//        srl_refresh.setOnRefreshLoadMoreListener(this);

        rv_list = rootView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        customerLogAdpter = new CustomerLogAdpter(mContext, R.layout.customer_log_list, lists);
        rv_list.setAdapter(customerLogAdpter);
        av_nodata = rootView.findViewById(R.id.av_nodata);

    }

    @Override
    protected void lazyLoad() {
        inData(page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_gjjl:
                App.startActivity(mContext, AddFollowLogActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData(page);
    }

    private void inData(int page) {
        String customer_id = CustomerDetailsActivity.customer_id;
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("id", customer_id);
            parame.put("module", "customer");
            parame.put("p", page);
            HttpClient.getInstance().post(mContext, DOMAIN_Loglist, parame, new BaseCallback<CustomerLogEntity>(CustomerLogEntity.class) {
                @Override
                public void onSuccess(CustomerLogEntity result) {
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            lists.clear();
                        }
                        if (result.getList() != null) {
                            lists.addAll(result.getList());
                        }
                    } else {
                        AppToast.showToast(result.getInfo());
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
                    Show(lists);
                }
            });
        }
    }

    private void Show(ArrayList<CustomerLogEntity.LogList> lists) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        if (lists != null && lists.size() > 0) {
            av_nodata.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
            customerLogAdpter.notifyDataSetChanged();
        } else {
            av_nodata.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        }
    }
//
//    @Override
//    public void onLoadMore(RefreshLayout refreshLayout) {
//        page++;
//        inData(page);
//
//    }

    //更新做题记录
    @Override
    public void onResume() {
        super.onResume();
        inData(page);
    }
}
