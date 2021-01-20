package com.yh.Model.TrafficMeasurement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rcw.popuplib.FixPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yh.Adpter.CustomerPool.CustomerPoolAdaper;
import com.yh.Adpter.Other.CalJuAdapter;
import com.yh.Adpter.Other.SortListAdapter;
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
    private RelativeLayout searchRay,search_ray;
    private ImageView iv_bd,iv_search;
    private EditText content;
    private TextView title;
    int mScreenWidth, mScreenHeight;
    WindowManager wm;
    private LinearLayout lay,ll_px;
    private View contentView;
    LinearLayout layout;
    LinearLayout.LayoutParams params;
    FixPopupWindow mFixPopupWindow;
    private String sortstr,editstr;
    private Handler handler = new Handler();


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
        wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的宽和高
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenHeight = wm.getDefaultDisplay().getHeight();
        srl_refresh = rootView.findViewById(R.id.srl_refresh);
        rv_list = rootView.findViewById(R.id.rv_list);
        av_nodata = rootView.findViewById(R.id.av_nodata);
        lay=rootView.findViewById(R.id.lay);
        iv_bd = rootView.findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);
        ll_px=rootView.findViewById(R.id.ll_px);
        ll_px.setOnClickListener(this);
        iv_search=rootView.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        searchRay=rootView.findViewById(R.id.searchRay);
        search_ray=rootView.findViewById(R.id.search_ray);
        search_ray.setOnClickListener(this);
        content=rootView.findViewById(R.id.content);
        content.addTextChangedListener(textWatcher);
        title=rootView.findViewById(R.id.title);
        title.setText("通话记录");


        initData(p,"desc","");

        mAdpter = new CalJuAdapter(mContext,datas);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(mAdpter);

        srl_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                datas.clear();
                p = 1;
                initData(p,sortstr,"");
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
                initData(p,sortstr,"");
                srl_refresh.finishLoadMore();

            }
        });
    }
    @Override
    protected void lazyLoad() {

    }
    //加载数据
    private void initData(int mPage,String ordertype ,String pho) {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("page", mPage);
            parame.put("pagelimit", "20");
            parame.put("start_time", "");
            parame.put("end_time", "");
            parame.put("order",ordertype);
            if (!TextUtils.isEmpty(pho)){
                parame.put("customerName",pho);
            }
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
            case R.id.ll_px:
                SortPopwindow();
                break;
            case R.id.iv_search:
                searchRay.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);

                break;
            case R.id.search_ray:
                searchRay.setVisibility(View.GONE);
                iv_search.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(editstr)||editstr.equals("")){
                    return;
                }else {
                    p=1;
                    datas.clear();
                    initData(p,sortstr,"");
                }

                break;
        }
    }


    private void SortPopwindow() {
        contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.sort_pop, null);
        RecyclerView reclerview1 = contentView.findViewById(R.id.reclerview1);


        List<String>pList=new ArrayList<>();
        pList.add("时间正序");
        pList.add("时间倒序");
        SortListAdapter sortadapter = new SortListAdapter(getActivity(), pList);
        reclerview1.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
        reclerview1.setAdapter(sortadapter);



        sortadapter.setOnItemClickListener(getActivity(), new SortListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String str) {
                //只有一级分类，调接口关闭弹窗
                p=1;
                if (str.equals("时间正序")){
                    sortstr="asc";

                }else   if (str.equals("时间倒序")){
                    sortstr="desc";
                }
                datas.clear();
                initData(p,sortstr,"");
                mFixPopupWindow.dismiss();
            }


        });

        layout = new LinearLayout(getActivity());
        wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的宽和高
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenHeight = wm.getDefaultDisplay().getHeight();
        //  params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.3));
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initPopupView(contentView);
    }


    public void initPopupView(View view) {
        view.setLayoutParams(params);
        layout.addView(view);
        //设置背景色，不设置的话在有些机型会不显示popupWindow
        layout.setBackgroundColor(Color.argb(60, 0, 0, 0));
        //自定义的FixPopupWindow，解决在Build.VERSION.SDK_INT >= 24时，popupWindow显示位置在屏幕顶部问题
        Log.e("height==", mScreenHeight + "");
        mFixPopupWindow = new FixPopupWindow(layout, mScreenWidth, mScreenHeight);
        mFixPopupWindow.setFocusable(true);
        mFixPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击popupWindow外部可消失
        mFixPopupWindow.setOutsideTouchable(true);
        //      mFixPopupWindow.setOnDismissListener(this);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFixPopupWindow.dismiss();
            }
        });

        mFixPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                Log.e("pop===", "1");
                init();

            }
        });
        //           }
        //设置点击popupButton时的状态
        mFixPopupWindow.showAsDropDown(lay);
    }



    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            editstr = editable.toString();
            if (delayRun != null) {
                //每次editText有变化的时候，则移除上次发出的延迟线程
                handler.removeCallbacks(delayRun);
            }
            //延迟500ms，如果不再输入字符，则执行该线程的run方法
            handler.postDelayed(delayRun, 200);
        }
    };


    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            if (editstr == null||editstr.equals("") ) {
                p=1;
                datas.clear();
                initData(p,sortstr,"");
                return;
            }
            p=1;
            datas.clear();
            initData(p,sortstr,editstr);
        }
    };


}
