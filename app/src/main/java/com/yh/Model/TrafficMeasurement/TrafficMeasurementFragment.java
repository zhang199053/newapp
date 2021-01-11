package com.yh.Model.TrafficMeasurement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yh.Adpter.CustomerPool.CustomerPoolAdaper;
import com.yh.Adpter.TrafficMeasurement.TrafficMeasurementAdpter;
import com.yh.App.App;
import com.yh.App.BaseFragment;
import com.yh.Entity.ConversationEntity;
import com.yh.Entity.CustomerBean;
import com.yh.Model.Customer.AddCustomerActivity;
import com.yh.Model.Customer.SearchActivity;
import com.yh.Model.MakeCall.MakeCallActivity;
import com.yh.R;
import com.yh.Utils.AppToast;
import com.yh.Utils.HttpUtils.BaseCallback;
import com.yh.Utils.HttpUtils.HttpClient;
import com.yh.Utils.SelectValueUtils;
import com.yh.ViewUtils.AbnormalView;
import com.yh.ViewUtils.NavigationBar;
import com.yh.ViewUtils.PopTop.ActionItem;
import com.yh.ViewUtils.PopTop.TablePopup;
import com.yh.ViewUtils.PopTop.TitlePopup;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yh.Utils.UrlUtils.Url.DOMAIN_allTelRecordDetail;

/**
 * Administrator  ：zhouyuru
 * 2020/11/27
 * Describe ：通话记录统计
 */
public class TrafficMeasurementFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnRefreshLoadMoreListener {
    private SmartRefreshLayout srl_refresh;
    //    话务统计数据
    private List<ConversationEntity.Datas> datas = new ArrayList<ConversationEntity.Datas>();
    //客户适配器
    private TrafficMeasurementAdpter mAdpter;
    //    客户列表
    private RecyclerView rv_list;
    //    当前页数
    private int mPage = 1;
    //    缺失图
    private AbnormalView av_nodata;
    //    拨号盘
    private ImageView iv_bd;

    @Override
    protected int setContentView() {
        return R.layout.traffic_measurement_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    protected void init() {
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.setOnRefreshLoadMoreListener(this);
        rv_list = rootView.findViewById(R.id.rv_list);
        mAdpter = new TrafficMeasurementAdpter(mContext, R.layout.traffic_measurement_item, datas);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(mAdpter);
        av_nodata = rootView.findViewById(R.id.av_nodata);
        iv_bd = rootView.findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);
    }
    @Override
    protected void lazyLoad() {
        initData(mPage);
    }
    //加载数据
    private void initData(int mPage) {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("page", mPage);
            parame.put("pagelimit", "20");
            parame.put("start_time", "2020-11-17 00:00:00");
            parame.put("end_time", "2020-11-19 24:00:00");
            HttpClient.getInstance().post(mContext, DOMAIN_allTelRecordDetail, parame, new BaseCallback<ConversationEntity>(ConversationEntity.class) {
                @Override
                public void onSuccess(ConversationEntity result) {
                    datas = result.getData().getDatas();
                }

                @Override
                public void onError(String msg) {
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    ShowViews(datas);
                }
            });
        }
    }

    //    回调展示数据
    private void ShowViews(List<ConversationEntity.Datas> datas) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }
        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        if (datas != null && datas.size() > 0) {
            mAdpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//                取消排序
            case R.id.iv_bd:
//                拨打电话页面
                App.startActivity(mContext, MakeCallActivity.class);
                break;
            default:
                break;
        }
    }

    //更新做题记录
    @Override
    public void onResume() {
        super.onResume();
        initData(mPage);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }
}
