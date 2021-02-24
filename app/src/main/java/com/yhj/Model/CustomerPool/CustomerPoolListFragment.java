package com.yhj.Model.CustomerPool;

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
import com.yhj.Adpter.CustomerPool.CustomerPoolAdaper;
import com.yhj.App.App;
import com.yhj.App.BaseFragment;
import com.yhj.Entity.CustomerBean;
import com.yhj.Model.Customer.AddCustomerActivity;
import com.yhj.Model.Customer.SearchActivity;
import com.yhj.Model.MakeCall.MakeCallActivity;
import com.yhj.Model.screen.SortUtils;
import com.yhj.R;
import com.yhj.Utils.AppToast;
import com.yhj.Utils.HttpUtils.BaseCallback;
import com.yhj.Utils.HttpUtils.HttpClient;
import com.yhj.Utils.SelectValueUtils;
import com.yhj.ViewUtils.AbnormalView;
import com.yhj.ViewUtils.NavigationBar;
import com.yhj.ViewUtils.PopTop.ActionItem;
import com.yhj.ViewUtils.PopTop.TablePopup;
import com.yhj.ViewUtils.PopTop.TitlePopup;
import com.yhj.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Receive;

/**
 * Administrator  ：zhouyuru
 * 2020/10/12
 * Describe ：
 */
public class CustomerPoolListFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnRefreshLoadMoreListener {
    private TablePopup tablePopup;
    private TitlePopup titlePopup;
    NavigationBar nb_agre;
    TextView title;
    ImageView iv_search, iv_add;
    private static SmartRefreshLayout srl_refresh;
    private CustomerBean bean;
    private static List<CustomerBean.Custome_list> custome_lists = new ArrayList<CustomerBean.Custome_list>();
    /**
     * 客户适配器
     */
    private static CustomerPoolAdaper customerPoolAdaper;
    //    客户列表
    private static RecyclerView rv_list;
    private static int mPage = 1;
    static AbnormalView av_nodata;
    private LinearLayout ll_px;
    private static ActionItem beans;
    //    排序
    private LinearLayout ll_qxpx;
    private ImageView iv_sx, iv_pos;
    private TextView tv;
    //    筛选
    private LinearLayout ll_sx;
    private TextView tv_sx2;
    private ImageView iv_sx2, iv_pos2;
    //    筛选框
    public static List<CustomerBean.Fields_list> fields_list = new ArrayList<CustomerBean.Fields_list>();
    //    查看用户所属
    public static List<CustomerBean.Scene_list> scene_list = new ArrayList<CustomerBean.Scene_list>();
    //    取消筛选的文案
    private TextView tv_name;
    //    拨号盘
    ImageView iv_bd;

    @Override
    protected int setContentView() {
        return R.layout.customer_pool_list_activity;
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
        nb_agre = rootView.findViewById(R.id.nb_agre);
        iv_add = rootView.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
        title = rootView.findViewById(R.id.title);
        title.setText("公海列表");
        iv_search = rootView.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        rv_list = rootView.findViewById(R.id.rv_list);
        customerPoolAdaper = new CustomerPoolAdaper(mContext, R.layout.customer_pool_item_activity, custome_lists);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(customerPoolAdaper);
        av_nodata = rootView.findViewById(R.id.av_nodata);
        ll_px = rootView.findViewById(R.id.ll_px);
        ll_px.setOnClickListener(this);
        ll_qxpx = rootView.findViewById(R.id.ll_qxpx);
        ll_qxpx.setOnClickListener(this);
        iv_sx = rootView.findViewById(R.id.iv_sx);
        iv_pos = rootView.findViewById(R.id.iv_pos);
        tv = rootView.findViewById(R.id.tv);
        ll_sx = rootView.findViewById(R.id.ll_sx);
        ll_sx.setOnClickListener(this);
        tv_sx2 = rootView.findViewById(R.id.tv_sx2);
        iv_sx2 = rootView.findViewById(R.id.iv_sx2);
        iv_pos2 = rootView.findViewById(R.id.iv_pos2);
        tv_name = rootView.findViewById(R.id.tv_name);
        iv_bd = rootView.findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);

        initPopWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_customer:
////                客户池聚合详情
//                App.startActivity(mContext, CustomerPoolDetailsActivity.class);
//                break;
//                搜索页面
            case R.id.iv_search:
                Intent intent1 = new Intent(mContext, SearchActivity.class).putExtra("title", "公海列表");
                mContext.startActivity(intent1);
                break;
//                新增客户
            case R.id.iv_add:
                App.startActivity(mContext, AddCustomerActivity.class);
                break;
            case R.id.ll_px:
//              排序
                if (fields_list != null && fields_list.size() > 0) {

                    titlePopup.show(rootView.findViewById(R.id.tv), beans);
                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px)));
                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
                    tv.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                } else {
                    AppToast.showToast("暂无数据！");
                }
                break;
//                取消排序
            case R.id.ll_qxpx:
                if (tv_name.getText().equals("取消排序")) {
                    bean = null;
                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_px)));
                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                    tv.setTextColor(Color.parseColor("#ff707070"));
                } else {
//               取消筛选
                    SelectValueUtils.selectPools.clear();
                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                    tv_sx2.setTextColor(Color.parseColor("#ff707070"));
                }
                initData(1);
                title.setVisibility(View.VISIBLE);
                ll_qxpx.setVisibility(View.GONE);
                break;
            case R.id.ll_sx:
//            筛选
                if (fields_list.size() > 0 && fields_list != null) {
                    tablePopup = new TablePopup(mContext, fields_list, scene_list, "公海列表");
                    tablePopup.showAsDropDown(tv_sx2);
                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px2)));
                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
                    tv_sx2.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                    tv_name.setText("取消筛选");
                    tablePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (SelectValueUtils.selectPools.size() > 0) {
                                title.setVisibility(View.GONE);
                                ll_qxpx.setVisibility(View.VISIBLE);
                            } else {
                                iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
                                iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                                tv_sx2.setTextColor(Color.parseColor("#ff707070"));
                                title.setVisibility(View.VISIBLE);
                                ll_qxpx.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    AppToast.showToast("暂无数据！");
                }


                break;
            case R.id.iv_bd:
//                拨打电话页面
                App.startActivity(mContext, MakeCallActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    protected void lazyLoad() {
        initData(1);
    }

    private void initPopWindow() {
        // 实例化标题栏弹窗
        titlePopup = new TitlePopup(mContext);
        ActionItem actionItem = new ActionItem("1", "创建时间正序", "create_time", "asc");
        ActionItem actionItem1 = new ActionItem("2", "创建时间倒序", "create_time", "desc");
        ActionItem actionItem2 = new ActionItem("3", "修改时间正序", "update_time", "asc");
        ActionItem actionItem3 = new ActionItem("4", "修改时间倒序", "update_time", "desc");
        titlePopup.addAction(actionItem);
        titlePopup.addAction(actionItem1);
        titlePopup.addAction(actionItem2);
        titlePopup.addAction(actionItem3);
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
            @Override
            public void onItemClick(ActionItem item, int position) {
                beans = item;
                initData(1);
            }
        });
        titlePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (beans != null) {
                    tv_name.setText("取消排序");
                    title.setVisibility(View.GONE);
                    ll_qxpx.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.VISIBLE);
                    ll_qxpx.setVisibility(View.GONE);
                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_px)));
                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                    tv.setTextColor(Color.parseColor("#ff707070"));
                }
            }
        });
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData(1);
    }

    //    回调展示数据
    public static void ShowViews2(CustomerBean bean, int page) {
        if (bean != null) {
            mPage = page;

            if (bean.getList() != null && bean.getList().size() > 0) {
//                填充客户数据
                if (page == 1) {
                    custome_lists.clear();
                    fields_list.clear();
                    //                填充客户所属类型
                    fields_list.addAll(bean.getFields_list());
//                填充排序
                    scene_list.clear();
                    scene_list.addAll(bean.getScene_list());
                }
                custome_lists.addAll(bean.getList());
                customerPoolAdaper.notifyDataSetChanged();
                av_nodata.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
            } else if (page > 1 && bean.getList().size() == 0) {
                customerPoolAdaper.notifyDataSetChanged();
            } else {
                av_nodata.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mPage++;
        initData(mPage);

    }

    //领取客户
    public static void logout(String customer_id, final int pos, final CustomerPoolDetailsActivity customerPoolDetailsActivity) {
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            ProgressDialogUtil.getInstance().startLoad(mContext);
            parame.put("token", App.getToken());
            parame.put("id", customer_id);
            parame.put("type", 1);
            HttpClient.getInstance().post(mContext, DOMAIN_Receive, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        AppToast.showToast(json.getString("info"));
                        customerPoolAdaper.remove(custome_lists.get(pos));
                        if (customerPoolDetailsActivity != null) {
                            customerPoolDetailsActivity.finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    ProgressDialogUtil.getInstance().stopLoad();
                    customerPoolAdaper.notifyDataSetChanged();
                }
            });
        }
    }

    //更新做题记录
    @Override
    public void onResume() {
        super.onResume();
        initData(mPage);
    }

    //调用加载方法
    public static void initData(int mPage) {
        SortUtils.inData2(mPage, beans, SelectValueUtils.selectPools, mContext, srl_refresh);
    }
}
