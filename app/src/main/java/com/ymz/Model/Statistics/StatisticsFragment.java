package com.ymz.Model.Statistics;

import com.githang.statusbar.StatusBarCompat;
import com.github.mikephil.charting.charts.LineChart;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ymz.App.App;
import com.ymz.App.BaseFragment;
import com.ymz.Entity.AddCustomerEntity;
import com.ymz.Entity.CompositeIndexBean;
import com.ymz.Entity.IncomeBean;
import com.ymz.Entity.LineChartBean;
import com.ymz.Entity.StatisticsEntity;
import com.ymz.Entity.UserBean;
import com.ymz.R;
import com.ymz.Utils.AppToast;
import com.ymz.Utils.HttpUtils.BaseCallback;
import com.ymz.Utils.HttpUtils.HttpClient;
import com.ymz.Utils.LineChartUtils;
import com.ymz.ViewUtils.LineChartManager;
import com.ymz.ViewUtils.StatusBarUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ymz.Utils.UrlUtils.Url.DOMAIN_Fields;
import static com.ymz.Utils.UrlUtils.Url.DOMAIN_TJ_Index;

/**
 * Administrator  ：zhouyuru
 * 2020/10/14
 * Describe ：统计页面
 */
public class StatisticsFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {
    //    private LineChart lineChart;
    //    private LineChartManager lineChartManager1;
//    List<IncomeBean> list = new ArrayList<>();
//    List<CompositeIndexBean> compositeIndexBeans = new ArrayList<>();
//    List<UserBean> userBeans = new ArrayList<>();
    //    private LineChart lineChart1;
    private SmartRefreshLayout srl_refresh;
    private StatisticsEntity.Datas datas;
    private TextView tv_customer_count, tv_contacts_count, tv_business_count;
    private TextView tv_week_kh;
    private TextView tv_week_lxr;
    private TextView tv_week_sj;
    private TextView tv_week_skje;
    private TextView tv_month_kh, tv_month_lxr, tv_month_sj, tv_month_skje;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.statiscs_fragment;
    }

    @Override
    protected void init() {
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
//        srl_refresh.autoRefresh();
        tv_customer_count = rootView.findViewById(R.id.tv_customer_count);
        tv_contacts_count = rootView.findViewById(R.id.tv_contacts_count);
        tv_business_count = rootView.findViewById(R.id.tv_business_count);
        tv_week_kh = rootView.findViewById(R.id.tv_week_kh);
        tv_week_lxr = rootView.findViewById(R.id.tv_week_lxr);
        tv_week_sj = rootView.findViewById(R.id.tv_week_sj);
        tv_week_skje = rootView.findViewById(R.id.tv_week_skje);
        tv_month_kh = rootView.findViewById(R.id.tv_month_kh);
        tv_month_lxr = rootView.findViewById(R.id.tv_month_lxr);
        tv_month_sj = rootView.findViewById(R.id.tv_month_sj);
        tv_month_skje = rootView.findViewById(R.id.tv_month_skje);
//        initData();
//        lineChart1 = rootView.findViewById(R.id.lineChart);
//        lineChartManager1 = new LineChartManager(lineChart1);
//        //展示图表
//        lineChartManager1.showLineChart(list, "新增客户", getResources().getColor(R.color.red));
//        lineChartManager1.addLine(compositeIndexBeans, "新增联系人", getResources().getColor(R.color.yellow));
//        lineChartManager1.addUserLine(userBeans, "新增商机", getResources().getColor(R.color.green));
//        //设置曲线填充色 以及 MarkerView
//        Drawable drawable = getResources().getDrawable(R.drawable.white);
//        lineChartManager1.setChartFillDrawable(drawable);
//        lineChartManager1.setMarkerView(mContext);

//        initDatas();

    }

    @Override
    protected void lazyLoad() {
        initDatas();
    }

    private void initDatas() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_TJ_Index, parame, new BaseCallback<StatisticsEntity>(StatisticsEntity.class) {
                @Override
                public void onSuccess(StatisticsEntity result) {

                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            datas = null;


                        }
                        if (result.getData() != null) {
                            datas = result.getData();
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
                    Show(datas);

                }
            });
        }
    }

    private void Show(StatisticsEntity.Datas datas) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        tv_customer_count.setText(datas.getCustomer_count().getDay());
        tv_contacts_count.setText(datas.getContacts_count().getDay());
        tv_business_count.setText(datas.getBusiness_count().getDay());
        tv_week_kh.setText(datas.getCustomer_count().getWeek());
        tv_week_lxr.setText(datas.getContacts_count().getWeek());
        tv_week_sj.setText(datas.getBusiness_count().getWeek());
        tv_week_skje.setText(datas.getSum_price_week());
        tv_month_kh.setText(datas.getCustomer_count().getMonth());
        tv_month_lxr.setText(datas.getContacts_count().getMonth());
        tv_month_sj.setText(datas.getBusiness_count().getMonth());
        tv_month_skje.setText(datas.getSum_price_month());
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initDatas();
    }

//
//    private void initData() {
//        //获取数据
//        IncomeBean bean = new IncomeBean();
//        bean.setValue(1);
//        IncomeBean bean1 = new IncomeBean();
//        bean1.setValue(3);
//        IncomeBean bean2 = new IncomeBean();
//        bean2.setValue(2);
//        IncomeBean bean3 = new IncomeBean();
//        bean3.setValue(4);
//        IncomeBean bean4 = new IncomeBean();
//        bean4.setValue(3);
//        IncomeBean bean5 = new IncomeBean();
//        bean5.setValue(6);
//        IncomeBean bean6 = new IncomeBean();
//        bean6.setValue(3);
//        list.add(bean);
//        list.add(bean1);
//        list.add(bean2);
//        list.add(bean3);
//        list.add(bean4);
//        list.add(bean5);
//        list.add(bean6);
//        CompositeIndexBean compositeIndexBean = new CompositeIndexBean();
//        compositeIndexBean.setRate(2);
//        CompositeIndexBean compositeIndexBean1 = new CompositeIndexBean();
//        compositeIndexBean1.setRate(3);
//        CompositeIndexBean compositeIndexBean2 = new CompositeIndexBean();
//        compositeIndexBean2.setRate(5);
//        CompositeIndexBean compositeIndexBean3 = new CompositeIndexBean();
//        compositeIndexBean3.setRate(6);
//        CompositeIndexBean compositeIndexBean4 = new CompositeIndexBean();
//        compositeIndexBean4.setRate(7);
//        CompositeIndexBean compositeIndexBean5 = new CompositeIndexBean();
//        compositeIndexBean5.setRate(2);
//        CompositeIndexBean compositeIndexBean6 = new CompositeIndexBean();
//        compositeIndexBean6.setRate(0);
//        compositeIndexBeans.add(compositeIndexBean);
//        compositeIndexBeans.add(compositeIndexBean1);
//        compositeIndexBeans.add(compositeIndexBean2);
//        compositeIndexBeans.add(compositeIndexBean3);
//        compositeIndexBeans.add(compositeIndexBean4);
//        compositeIndexBeans.add(compositeIndexBean5);
//        compositeIndexBeans.add(compositeIndexBean6);
//        UserBean userBean = new UserBean();
//        userBean.setRate(0);
//        UserBean userBean1 = new UserBean();
//        userBean1.setRate(4);
//        UserBean userBean2 = new UserBean();
//        userBean2.setRate(6);
//        UserBean userBean3 = new UserBean();
//        userBean3.setRate(1);
//        UserBean userBean4 = new UserBean();
//        userBean4.setRate(3);
//        UserBean userBean5 = new UserBean();
//        userBean5.setRate(5);
//        UserBean userBean6 = new UserBean();
//        userBean6.setRate(4);
//        userBeans.add(userBean);
//        userBeans.add(userBean1);
//        userBeans.add(userBean2);
//        userBeans.add(userBean3);
//        userBeans.add(userBean4);
//        userBeans.add(userBean5);
//        userBeans.add(userBean6);
//    }

    //更新做题记录
    @Override
    public void onResume() {
        super.onResume();
        initDatas();

    }

}
