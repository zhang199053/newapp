package com.ymz.Model.TrafficMeasurement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ymz.Adpter.CustomerPool.CustomerPoolAdaper;
import com.ymz.Adpter.Other.CalJuAdapter;
import com.ymz.App.App;
import com.ymz.App.BaseFragment;
import com.ymz.Entity.ConversationEntity;
import com.ymz.Entity.CustomerBean;
import com.ymz.Model.Customer.AddCustomerActivity;
import com.ymz.Model.Customer.SearchActivity;
import com.ymz.Model.MakeCall.MakeCallActivity;
import com.ymz.R;
import com.ymz.Utils.AppToast;
import com.ymz.Utils.HttpUtils.BaseCallback;
import com.ymz.Utils.HttpUtils.HttpClient;
import com.ymz.Utils.SelectValueUtils;
import com.ymz.ViewUtils.AbnormalView;
import com.ymz.ViewUtils.NavigationBar;
import com.ymz.ViewUtils.PopTop.ActionItem;
import com.ymz.ViewUtils.PopTop.TablePopup;
import com.ymz.ViewUtils.PopTop.TitlePopup;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ymz.Utils.UrlUtils.Url.DOMAIN_allTelRecordDetail;

/**
 * Administrator  ：zhouyuru
 * 2020/11/27
 * Describe ：通话记录统计
 */
public class TrafficMeasurementFragment extends BaseFragment implements View.OnClickListener {
    private SmartRefreshLayout srl_refresh;
    //    话务统计数据
    private List<ConversationEntity.DataBeanX.DataBean> datas = new ArrayList<>();
    //客户适配器
    private CalJuAdapter mAdpter;
    //    客户列表
    private RecyclerView rv_list;
    //    当前页数
    private int p = 1;
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
        rv_list = rootView.findViewById(R.id.rv_list);
        av_nodata = rootView.findViewById(R.id.av_nodata);
        iv_bd = rootView.findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);

        initData(p);

        mAdpter = new CalJuAdapter(mContext,datas);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(mAdpter);

        srl_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                datas.clear();
                p = 1;
                initData(p);
                srl_refresh.finishRefresh();
            }
        });


        srl_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (av_nodata.getVisibility() == View.VISIBLE) {
                    srl_refresh.finishLoadMore();
                    return;
                }
                p =p+ 1;
                initData(p);
                srl_refresh.finishLoadMore();

            }
        });
    }
    @Override
    protected void lazyLoad() {

    }
    //加载数据
    private void initData(int mPage) {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("page", mPage);
            parame.put("pagelimit", "20");
            parame.put("start_time", "2020-11-17 00:00:00");
            parame.put("end_time", "2021-1-15 24:00:00");
            Log.e("json=====fstatiss/////", parame.toString());
            HttpClient.getInstance().post(mContext, DOMAIN_allTelRecordDetail, parame, new BaseCallback<ConversationEntity>(ConversationEntity.class) {
                @Override
                public void onSuccess(ConversationEntity result) {

                    //  datas = result.getData().getDatas();

                    datas.addAll(result.getData().getData());
                    String json = new Gson().toJson(datas);
                    Log.e("json=====fstatiss====", json);

                    if (datas.size() == 0 && p == 1) {
                        av_nodata.setVisibility(View.VISIBLE);
                    } else {
                        av_nodata.setVisibility(View.GONE);
                    }

                    mAdpter.notifyDataSetChanged();
                }

                @Override
                public void onError(String msg) {
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    // ShowViews(datas);
                }
            });
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



}
