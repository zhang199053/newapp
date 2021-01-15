package com.cyb.Model.My;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.cyb.Adpter.Other.StatisticalAdapter;
import com.cyb.App.App;
import com.cyb.App.BaseActivity;
import com.cyb.Entity.AudioBean;
import com.cyb.Entity.Statisticalbean;
import com.cyb.R;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.HttpUtils.BaseCallback;
import com.cyb.Utils.HttpUtils.HttpClient;
import com.cyb.Utils.SystemUtil;
import com.cyb.Utils.UrlUtils.Url;
import com.cyb.ViewUtils.AbnormalView;
import com.cyb.ViewUtils.NavigationBar;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.cyb.Utils.UrlUtils.Url.AUDIO_Index;
import static com.cyb.Utils.UrlUtils.Url.CALL_STATISTCAL;

public class StatisticalActivity extends BaseActivity {


    @Bind(R.id.nb_agre)
    NavigationBar nbAgre;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.av_nodata)
    AbnormalView avNodata;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    StatisticalAdapter adapter;
    private int p = 1;
    private List<Statisticalbean.DataBeanX.DataBean>list=new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_statistical;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


        httpApi(p);
        data();

    }

    private void httpApi(final int p) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("token", App.getToken());
        parame.put("page", p);
        parame.put("page_size","10");
        Log.d("json===//", "请求参数:" + parame.toString());
        Log.d("json===//", "请求地址:" +Url.ip+"=="+ Url.CALL_STATISTCAL+"=="+Url.DOMAIN_NAME);
        HttpClient.getInstance().post(mContext,Url.DOMAIN_NAME+ Url.CALL_STATISTCAL, parame, new BaseCallback<Statisticalbean>(Statisticalbean.class) {
            @Override
            public void onSuccess(Statisticalbean result) {
                String datas = new Gson().toJson(result);
                Log.e("json=====fstatiss", datas);

                list.addAll(result.getData().getData());

                if (list.size() == 0 && p == 1) {
                    avNodata.setVisibility(View.VISIBLE);
                } else {
                    avNodata.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
               // AppToast.showToast(msg);

            }

            @Override
            public void onCancelled(Callback.CancelledException var1) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void data() {
        adapter = new StatisticalAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                p = 1;
                httpApi(p);
                refreshLayout.finishRefresh();
            }
        });


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (avNodata.getVisibility() == View.VISIBLE) {
                    refreshLayout.finishLoadMore();
                    return;
                }
                p = p + 1;
                httpApi(p);
                refreshLayout.finishLoadMore();

            }
        });
    }

    @Override
    public void onClick(View v) {

    }


}
