package com.xyk.Model.Finance;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xyk.Adpter.Customer.CustomerContractAdpter;
import com.xyk.Adpter.Customer.CustomerFinanceAdpter;
import com.xyk.App.App;
import com.xyk.App.BaseFragment;
import com.xyk.Entity.CustomerContractEntity;
import com.xyk.Entity.CustomerFinancetEntity;
import com.xyk.Model.Customer.CustomerDetailsActivity;
import com.xyk.R;
import com.xyk.Utils.AppToast;
import com.xyk.Utils.HttpUtils.BaseCallback;
import com.xyk.Utils.HttpUtils.HttpClient;
import com.xyk.ViewUtils.AbnormalView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.xyk.Utils.UrlUtils.Url.DOMAIN_Contract;
import static com.xyk.Utils.UrlUtils.Url.DOMAIN_Finance;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：用户-财务
 */
public class FinanceFragment extends BaseFragment implements OnRefreshListener {
    private RecyclerView rv_list;
    private CustomerFinanceAdpter customerLogAdpter;
    private SmartRefreshLayout srl_refresh;
    private ArrayList<CustomerFinancetEntity.FinancetList> lists = new ArrayList<CustomerFinancetEntity.FinancetList>();
    AbnormalView av_nodata;

    @Override
    protected int setContentView() {
        return R.layout.finance_fragment;
    }

    @Override
    protected void init() {
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        rv_list = rootView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        customerLogAdpter = new CustomerFinanceAdpter(mContext, R.layout.customer_finance_list, lists);
        rv_list.setAdapter(customerLogAdpter);
        av_nodata = rootView.findViewById(R.id.av_nodata);

    }

    @Override
    protected void lazyLoad() {
        inData();

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData();

    }

    private void inData() {
        String customer_id = CustomerDetailsActivity.customer_id;
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("customer_id", customer_id);
            parame.put("t", "receivingorder");
            HttpClient.getInstance().post(mContext, DOMAIN_Finance, parame, new BaseCallback<CustomerFinancetEntity>(CustomerFinancetEntity.class) {
                @Override
                public void onSuccess(CustomerFinancetEntity result) {
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

    private void Show(ArrayList<CustomerFinancetEntity.FinancetList> lists) {
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
