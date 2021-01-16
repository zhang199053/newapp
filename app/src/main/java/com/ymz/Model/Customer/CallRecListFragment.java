//package com.ymz.Model.Customer;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
//import com.ymz.Adpter.Customer.CustomerAdaper;
//import com.ymz.Adpter.Other.CalJuAdapter;
//import com.ymz.App.App;
//import com.ymz.App.BaseFragment;
//import com.ymz.Entity.ConversationEntity;
//import com.ymz.Entity.CustomerBean;
//import com.ymz.Entity.Statisticalbean;
//import com.ymz.Model.MakeCall.MakeCallActivity;
//import com.ymz.Model.screen.SortUtils;
//import com.ymz.R;
//import com.ymz.Utils.AppToast;
//import com.ymz.Utils.HttpUtils.BaseCallback;
//import com.ymz.Utils.HttpUtils.HttpClient;
//import com.ymz.Utils.SelectValueUtils;
//import com.ymz.ViewUtils.AbnormalView;
//import com.ymz.ViewUtils.NavigationBar;
//import com.ymz.ViewUtils.PopTop.ActionItem;
//import com.ymz.ViewUtils.PopTop.TablePopup;
//import com.ymz.ViewUtils.PopTop.TitlePopup;
//import org.xutils.common.Callback;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import static com.ymz.Utils.UrlUtils.Url.DOMAIN_Index;
//import static com.ymz.Utils.UrlUtils.Url.DOMAIN_allTelRecordDetail;
//import static com.ymz.Utils.UrlUtils.Url.dialpanel_phone;
///**
// * Administrator  ：zhouyuru
// * 2020/9/28
// * Describe ：客户列表页
// */
//public class CallRecListFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnRefreshLoadMoreListener {
//    private TablePopup tablePopup;
//    private TitlePopup titlePopup;
//    NavigationBar nb_agre;
//    ImageView iv_add;
//    LinearLayout ll_px;
//    ImageView iv_search;
//    ImageView iv_bd;
//    RelativeLayout rv_zz;
//    TextView title;
//    private static SmartRefreshLayout srl_refresh;
//    private static List<CustomerBean.Custome_list> custome_lists = new ArrayList<CustomerBean.Custome_list>();
//    /**
//     * 客户适配器
//     */
//    private CalJuAdapter customerAdaper;
//    //    客户列表
//    private  RecyclerView rv_list;
//    private  int mPage = 1;
//    static AbnormalView av_nodata;
//    private static ActionItem bean;
//    //    排序
//    private LinearLayout ll_qxpx;
//    private ImageView iv_sx, iv_pos;
//    private TextView tv;
//    //    筛选
//    private LinearLayout ll_sx;
//    private TextView tv_sx2;
//    private ImageView iv_sx2, iv_pos2;
//    //    取消筛选的文案
//    private TextView tv_name;
//    //    筛选框
//    public static List<CustomerBean.Fields_list> fields_list = new ArrayList<CustomerBean.Fields_list>();
//    //    查看用户所属
//    private List<ConversationEntity.Datas> datas = new ArrayList<ConversationEntity.Datas>();
//    //    返回得数据
//    public static CustomerBean beans;
//    private List<Statisticalbean.DataBeanX.DataBean>list=new ArrayList<>();
//    @Override
//    protected int setContentView() {
//        return R.layout.callrec_list_activity;
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    protected void init() {
//        srl_refresh = rootView.findViewById(R.id.srl_refresh);
//        srl_refresh.setOnRefreshListener(this);
//        srl_refresh.setOnRefreshLoadMoreListener(this);
//        rv_zz = rootView.findViewById(R.id.rv_zz);
//        initPopWindow();
//        nb_agre = rootView.findViewById(R.id.nb_agre);
//        iv_add = rootView.findViewById(R.id.iv_add);
//        iv_add.setOnClickListener(this);
//
//        iv_search = rootView.findViewById(R.id.iv_search);
//        iv_search.setOnClickListener(this);
//        iv_bd = rootView.findViewById(R.id.iv_bd);
//        iv_bd.setOnClickListener(this);
//        ll_px = rootView.findViewById(R.id.ll_px);
//        ll_px.setOnClickListener(this);
//        title = rootView.findViewById(R.id.title);
//        title.setText("客户列表");
//        ll_qxpx = rootView.findViewById(R.id.ll_qxpx);
//        ll_qxpx.setOnClickListener(this);
//        iv_sx = rootView.findViewById(R.id.iv_sx);
//        iv_pos = rootView.findViewById(R.id.iv_pos);
//        tv = rootView.findViewById(R.id.tv);
//        rv_list = rootView.findViewById(R.id.rv_list);
//        customerAdaper = new CalJuAdapter(mContext,datas);
//        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
//        rv_list.setAdapter(customerAdaper);
//        av_nodata = rootView.findViewById(R.id.av_nodata);
//        ll_sx = rootView.findViewById(R.id.ll_sx);
//        ll_sx.setOnClickListener(this);
//        tv_sx2 = rootView.findViewById(R.id.tv_sx2);
//        iv_sx2 = rootView.findViewById(R.id.iv_sx2);
//        iv_pos2 = rootView.findViewById(R.id.iv_pos2);
//        tv_name = rootView.findViewById(R.id.tv_name);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_add:
////                新增客户
//                Intent intent = new Intent(mContext, AddCustomerActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.iv_search:
////                搜索页面
//                Intent intent1 = new Intent(mContext, SearchActivity.class).putExtra("title", "客户列表");
//                mContext.startActivity(intent1);
//                break;
//            case R.id.iv_bd:
////                拨打电话页面
//                dialpanel_phone = "";
//                App.startActivity(mContext, MakeCallActivity.class);
//                break;
//            case R.id.ll_px:
////                排序页面
//                if (fields_list != null && fields_list.size() > 0) {
//                    titlePopup.show(rootView.findViewById(R.id.tv), bean);
//                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px)));
//                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
//                    tv.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
//                } else {
//                    AppToast.showToast("暂无数据！");
//                }
//
//                break;
//            case R.id.ll_qxpx:
////                取消排序
//                if (tv_name.getText().equals("取消排序")) {
//                    bean = null;
//                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_px)));
//                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
//                    tv.setTextColor(Color.parseColor("#ff707070"));
//                } else {
////                   取消筛选
//                    SelectValueUtils.list.clear();
//                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
//                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
//                    tv_sx2.setTextColor(Color.parseColor("#ff707070"));
//                }
//                initData(1);
//                title.setVisibility(View.VISIBLE);
//                ll_qxpx.setVisibility(View.GONE);
//                break;
//            case R.id.ll_sx:
////                筛选页面
//                if (fields_list.size() > 0 && fields_list != null) {
////                    tablePopup = new TablePopup(mContext, fields_list, scene_list, "客户列表");
////                    tablePopup.showAsDropDown(tv_sx2);
////                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px2)));
////                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
////                    tv_sx2.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
////                    tv_name.setText("取消筛选");
////                    tablePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
////                        @Override
////                        public void onDismiss() {
////                            if (SelectValueUtils.list.size() > 0) {
////                                title.setVisibility(View.GONE);
////                                ll_qxpx.setVisibility(View.VISIBLE);
////                            } else {
////                                title.setVisibility(View.VISIBLE);
////                                ll_qxpx.setVisibility(View.GONE);
////                                iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
////                                iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
////                                tv_sx2.setTextColor(Color.parseColor("#ff707070"));
////                            }
////                        }
////                    });
//                } else {
//                    AppToast.showToast("暂无数据！");
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void initPopWindow() {
//        // 实例化标题栏弹窗
//        titlePopup = new TitlePopup(mContext);
//        ActionItem actionItem = new ActionItem("1", "创建时间正序", "create_time", "asc");
//        ActionItem actionItem1 = new ActionItem("2", "创建时间倒序", "create_time", "desc");
//        ActionItem actionItem2 = new ActionItem("3", "修改时间正序", "update_time", "asc");
//        ActionItem actionItem3 = new ActionItem("4", "修改时间倒序", "update_time", "desc");
//        bean=actionItem3;
//        titlePopup.addAction(actionItem);
//        titlePopup.addAction(actionItem1);
//        titlePopup.addAction(actionItem2);
//        titlePopup.addAction(actionItem3);
//        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
//            @Override
//            public void onItemClick(ActionItem item, int position) {
//                bean = item;
//                initData(1);
//
//            }
//        });
//        titlePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                if (bean != null) {
//                    tv_name.setText("取消排序");
//                    title.setVisibility(View.GONE);
//                    ll_qxpx.setVisibility(View.VISIBLE);
//                } else {
//                    title.setVisibility(View.VISIBLE);
//                    ll_qxpx.setVisibility(View.GONE);
//                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_px)));
//                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
//                    tv.setTextColor(Color.parseColor("#ff707070"));
//                }
//            }
//        });
//    }
//
//
//    @Override
//    protected void lazyLoad() {
//        initData(1);
//
//    }
//
//    @Override
//    public void onRefresh(RefreshLayout refreshLayout) {
//        initData(1);
//    }
//
//
//    @Override
//    public void onLoadMore(RefreshLayout refreshLayout) {
//        mPage++;
//        initData(mPage);
//    }
//
//    //更新数据
//    @Override
//    public void onResume() {
//        super.onResume();
//        initData(1);
//    }
//
//    //调用加载方法
//    private   void initData(int mPage) {
//
//        inData(mPage, bean , SelectValueUtils.list, mContext, srl_refresh);
//
//    }
//
//    private void inData(final int page, ActionItem actionItem, List<SelectValueUtils.Select> list, Context mContext, SmartRefreshLayout smartRefreshLayout) {
//        srl_refresh = smartRefreshLayout;
//        if (!TextUtils.isEmpty(App.getToken())) {
//            Map<String, Object> parame = new HashMap<>();
//            parame.put("token", App.getToken());
//            parame.put("p", page);
//            if (actionItem != null) {
//                parame.put("order_field", actionItem.getDate().toString());
//                parame.put("order_type", actionItem.getSort().toString());
//            } else {
////                默认按最新的创建的排序
//                parame.put("order_field", "create_time");
//                parame.put("order_type", "desc");
//            }
//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    parame.put(list.get(i).getKey(), list.get(i).getValue());
//                }
//            }
////
////            HttpClient.getInstance().post(mContext, DOMAIN_Index, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
////                @Override
////                public void onSuccess(CustomerBean result) {
////                    if (result.getInfo().equals("success")) {
////                        if (!srl_refresh.isLoading()) {
////                            if (page == 1) {
////                                //bean = null;
////                            }
////                        }
////                        if (result.getList() != null) {
////                            //bean = result;
////                        }
////                    } else {
////                        AppToast.showToast(result.getInfo());
////                    }
////                }
////
////                @Override
////                public void onError(String msg) {
////                    if (!srl_refresh.isLoading()) {
////                        custome_lists.clear();
////                    }
////                    AppToast.showToast(msg);
////                }
////
////                @Override
////                public void onCancelled(Callback.CancelledException var1) {
////                }
////
////                @Override
////                public void onFinished() {
////                    if (srl_refresh.isRefreshing()) {
////                        srl_refresh.finishRefresh(500);
////                    }
////                    if (srl_refresh.isLoading()) {
////                        srl_refresh.finishLoadMore();
////                    }
////                    //ShowViews(bean, page);
////
////                }
////            });
//        }
//
//
//
//
//
//
//
//
//    }
//
//}
