package com.xsl.Model.Customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xsl.Adpter.Customer.CustomerAdaper;
import com.xsl.App.App;
import com.xsl.App.BaseFragment;
import com.xsl.Entity.CustomerBean;
import com.xsl.Model.MakeCall.MakeCallActivity;
import com.xsl.Model.screen.SortUtils;
import com.xsl.R;
import com.xsl.Utils.AppToast;
import com.xsl.Utils.SelectValueUtils;
import com.xsl.ViewUtils.AbnormalView;
import com.xsl.ViewUtils.NavigationBar;
import com.xsl.ViewUtils.PopTop.ActionItem;
import com.xsl.ViewUtils.PopTop.TablePopup;
import com.xsl.ViewUtils.PopTop.TitlePopup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.xsl.Utils.UrlUtils.Url.dialpanel_phone;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：客户列表页
 */
public class CustomerListFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnRefreshLoadMoreListener {
    private TablePopup tablePopup;
    private TitlePopup titlePopup;
    NavigationBar nb_agre;
    ImageView iv_add;
    LinearLayout ll_px;
    ImageView iv_search;
    ImageView iv_bd;
    RelativeLayout rv_zz;
    TextView title;
    private static SmartRefreshLayout srl_refresh;
    private static List<CustomerBean.Custome_list> custome_lists = new ArrayList<CustomerBean.Custome_list>();
    /**
     * 客户适配器
     */
    private static CustomerAdaper customerAdaper;
    //    客户列表
    private static RecyclerView rv_list;
    private static int mPage = 1;
    static AbnormalView av_nodata;
    private static ActionItem bean;
    //    排序
    private LinearLayout ll_qxpx;
    private ImageView iv_sx, iv_pos;
    private TextView tv;
    //    筛选
    private LinearLayout ll_sx;
    private TextView tv_sx2;
    private ImageView iv_sx2, iv_pos2;
    //    取消筛选的文案
    private TextView tv_name;
    //    筛选框
    public static List<CustomerBean.Fields_list> fields_list = new ArrayList<CustomerBean.Fields_list>();
    //    查看用户所属
    public static List<CustomerBean.Scene_list> scene_list = new ArrayList<CustomerBean.Scene_list>();
    //    返回得数据
    public static CustomerBean beans;

    @Override
    protected int setContentView() {
        return R.layout.customer_list_activity;

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
        rv_zz = rootView.findViewById(R.id.rv_zz);
        initPopWindow();
        nb_agre = rootView.findViewById(R.id.nb_agre);
        iv_add = rootView.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);

        iv_search = rootView.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        iv_bd = rootView.findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);
        ll_px = rootView.findViewById(R.id.ll_px);
        ll_px.setOnClickListener(this);
        title = rootView.findViewById(R.id.title);
        title.setText("客户列表");
        ll_qxpx = rootView.findViewById(R.id.ll_qxpx);
        ll_qxpx.setOnClickListener(this);
        iv_sx = rootView.findViewById(R.id.iv_sx);
        iv_pos = rootView.findViewById(R.id.iv_pos);
        tv = rootView.findViewById(R.id.tv);
        rv_list = rootView.findViewById(R.id.rv_list);
        customerAdaper = new CustomerAdaper(mContext, R.layout.customer_item_activity, custome_lists, getActivity());
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(customerAdaper);
        av_nodata = rootView.findViewById(R.id.av_nodata);
        ll_sx = rootView.findViewById(R.id.ll_sx);
        ll_sx.setOnClickListener(this);
        tv_sx2 = rootView.findViewById(R.id.tv_sx2);
        iv_sx2 = rootView.findViewById(R.id.iv_sx2);
        iv_pos2 = rootView.findViewById(R.id.iv_pos2);
        tv_name = rootView.findViewById(R.id.tv_name);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
//                新增客户
                Intent intent = new Intent(mContext, AddCustomerActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.iv_search:
//                搜索页面
                Intent intent1 = new Intent(mContext, SearchActivity.class).putExtra("title", "客户列表");
                mContext.startActivity(intent1);
                break;
            case R.id.iv_bd:
//                拨打电话页面
                dialpanel_phone = "";
                App.startActivity(mContext, MakeCallActivity.class);
                break;
            case R.id.ll_px:
//                排序页面
                if (fields_list != null && fields_list.size() > 0) {
                    titlePopup.show(rootView.findViewById(R.id.tv), bean);
                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px)));
                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
                    tv.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                } else {
                    AppToast.showToast("暂无数据！");
                }

                break;
            case R.id.ll_qxpx:
//                取消排序
                if (tv_name.getText().equals("取消排序")) {
                    bean = null;
                    iv_sx.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_px)));
                    iv_pos.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                    tv.setTextColor(Color.parseColor("#ff707070"));
                } else {
//                   取消筛选
                    SelectValueUtils.list.clear();
                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                    tv_sx2.setTextColor(Color.parseColor("#ff707070"));
                }
                initData(1);
                title.setVisibility(View.VISIBLE);
                ll_qxpx.setVisibility(View.GONE);
                break;
            case R.id.ll_sx:
//                筛选页面
                if (fields_list.size() > 0 && fields_list != null) {
                    tablePopup = new TablePopup(mContext, fields_list, scene_list, "客户列表");
                    tablePopup.showAsDropDown(tv_sx2);
                    iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_px2)));
                    iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_xz_bom)));
                    tv_sx2.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                    tv_name.setText("取消筛选");
                    tablePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (SelectValueUtils.list.size() > 0) {
                                title.setVisibility(View.GONE);
                                ll_qxpx.setVisibility(View.VISIBLE);
                            } else {
                                title.setVisibility(View.VISIBLE);
                                ll_qxpx.setVisibility(View.GONE);
                                iv_sx2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_sx)));
                                iv_pos2.setImageDrawable(getResources().getDrawable((R.mipmap.icon_kh_bom)));
                                tv_sx2.setTextColor(Color.parseColor("#ff707070"));
                            }
                        }
                    });
                } else {
                    AppToast.showToast("暂无数据！");
                }
                break;
            default:
                break;
        }
    }

    private void initPopWindow() {
        // 实例化标题栏弹窗
        titlePopup = new TitlePopup(mContext);
        ActionItem actionItem = new ActionItem("1", "创建时间正序", "create_time", "asc");
        ActionItem actionItem1 = new ActionItem("2", "创建时间倒序", "create_time", "desc");
        ActionItem actionItem2 = new ActionItem("3", "修改时间正序", "update_time", "asc");
        ActionItem actionItem3 = new ActionItem("4", "修改时间倒序", "update_time", "desc");
        bean=actionItem3;
        titlePopup.addAction(actionItem);
        titlePopup.addAction(actionItem1);
        titlePopup.addAction(actionItem2);
        titlePopup.addAction(actionItem3);
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
            @Override
            public void onItemClick(ActionItem item, int position) {
                bean = item;
                initData(1);

            }
        });
        titlePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (bean != null) {
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
    protected void lazyLoad() {
        initData(1);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData(1);
    }

    //    回调展示数据
    public static void ShowViews(CustomerBean bean, int page) {
        if (bean != null) {
//            添加数据
            mPage = page;
            if (bean.getList() != null && bean.getList().size() > 0) {
//                第一页清理当前数据
                if (page == 1) {
                    custome_lists.clear();
                    fields_list.clear();
                    //                填充客户所属类型
                    fields_list.addAll(bean.getFields_list());
//                填充排序
                    scene_list.clear();
                    scene_list.addAll(bean.getScene_list());
//                    如果当前页数等于旧页数
                }
                custome_lists.addAll(bean.getList());
                customerAdaper.notifyDataSetChanged();
                av_nodata.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
//            翻页无数据时
            } else if (page > 1 && bean.getList().size() == 0) {
                customerAdaper.notifyDataSetChanged();
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

    //更新数据
    @Override
    public void onResume() {
        super.onResume();
        initData(1);
    }

    //调用加载方法
    public static void initData(int mPage) {

        SortUtils.inData(mPage, bean , SelectValueUtils.list, mContext, srl_refresh);
    }
}
