package com.yhj.Model.BusinessOpportunity;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yhj.Adpter.Customer.CustomerBusnessAdpter;
import com.yhj.Adpter.Customer.CustomerLogAdpter;
import com.yhj.App.App;
import com.yhj.App.BaseFragment;
import com.yhj.Entity.CustomerBusinessEntity;
import com.yhj.Entity.CustomerLogEntity;
import com.yhj.Model.Customer.CustomerDetailsActivity;
import com.yhj.R;
import com.yhj.Utils.AppToast;
import com.yhj.Utils.HttpUtils.BaseCallback;
import com.yhj.Utils.HttpUtils.HttpClient;
import com.yhj.ViewUtils.AbnormalView;
import com.yhj.ViewUtils.CircularProgressView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Business;
import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Loglist;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：用户-商机页面
 */
public class BusinessOpportunityFragment extends BaseFragment implements OnRefreshListener {
    private RecyclerView rv_list;
    private CustomerBusnessAdpter customerLogAdpter;
    private SmartRefreshLayout srl_refresh;
    private int page = 1;
    private ArrayList<CustomerBusinessEntity.BusinessList> lists = new ArrayList<CustomerBusinessEntity.BusinessList>();
    AbnormalView av_nodata;

    @Override
    protected int setContentView() {
        return R.layout.business_opportunity_fragment;
    }

    @Override
    protected void init() {
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
//        srl_refresh.setOnLoadMoreListener(this);
        rv_list = rootView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        customerLogAdpter = new CustomerBusnessAdpter(mContext, R.layout.customer_business_list, lists);
        rv_list.setAdapter(customerLogAdpter);
        av_nodata = rootView.findViewById(R.id.av_nodata);
    }

    @Override
    protected void lazyLoad() {
        inData(page);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData(page);

    }

//    @Override
//    public void onLoadMore(RefreshLayout refreshLayout) {
//        page++;
//        inData(page);
//    }

    private void inData(final int page) {
        String customer_id = CustomerDetailsActivity.customer_id;
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("customer_id", customer_id);
            parame.put("p", page);
            HttpClient.getInstance().post(mContext, DOMAIN_Business, parame, new BaseCallback<CustomerBusinessEntity>(CustomerBusinessEntity.class) {
                @Override
                public void onSuccess(CustomerBusinessEntity result) {
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            if (page == 1) {
                                lists.clear();
                            }
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

    private void Show(ArrayList<CustomerBusinessEntity.BusinessList> lists) {
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

}
