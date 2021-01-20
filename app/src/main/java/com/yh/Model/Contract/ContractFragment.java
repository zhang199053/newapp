package com.yh.Model.Contract;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yh.Adpter.Customer.CustomerBusnessAdpter;
import com.yh.Adpter.Customer.CustomerContractAdpter;
import com.yh.Entity.CustomerContractEntity;
import com.yh.App.App;
import com.yh.App.BaseFragment;
import com.yh.Entity.CustomerBusinessEntity;
import com.yh.Model.Customer.CustomerDetailsActivity;
import com.yh.R;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.ViewUtils.AbnormalView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_Contract;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：用户-合同
 */
public class ContractFragment extends BaseFragment implements OnRefreshListener {
    private RecyclerView rv_list;
    private CustomerContractAdpter customerLogAdpter;
    private SmartRefreshLayout srl_refresh;
    private ArrayList<CustomerContractEntity.ContractList> lists = new ArrayList<CustomerContractEntity.ContractList>();
    AbnormalView av_nodata;

    @Override
    protected int setContentView() {
        return R.layout.contract_fragment;
    }

    @Override
    protected void init() {
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        rv_list = rootView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        customerLogAdpter = new CustomerContractAdpter(mContext, R.layout.customer_contract_list, lists);
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
            HttpClient.getInstance().post(mContext, DOMAIN_Contract, parame, new BaseCallback<CustomerContractEntity>(CustomerContractEntity.class) {
                @Override
                public void onSuccess(CustomerContractEntity result) {
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

    private void Show(ArrayList<CustomerContractEntity.ContractList> lists) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }

        if (lists != null&&lists.size()>0) {
            av_nodata.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
            customerLogAdpter.notifyDataSetChanged();
        } else {
            av_nodata.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        }
    }

}