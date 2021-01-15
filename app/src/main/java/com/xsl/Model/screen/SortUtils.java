package com.xsl.Model.screen;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xsl.App.App;
import com.xsl.Entity.CustomerBean;
import com.xsl.Model.Customer.SearchActivity;
import com.xsl.Utils.AppToast;
import com.xsl.Utils.HttpUtils.BaseCallback;
import com.xsl.Utils.HttpUtils.HttpClient;
import com.xsl.Utils.SelectValueUtils;
import com.xsl.Utils.UrlUtils.Url;
import com.xsl.ViewUtils.PopTop.ActionItem;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xsl.Model.Customer.CustomerListFragment.ShowViews;
import static com.xsl.Model.CustomerPool.CustomerPoolListFragment.ShowViews2;
import static com.xsl.Utils.UrlUtils.Url.DOMAIN_Index;
import static com.xsl.Utils.UrlUtils.Url.DOMAIN_Resource;

/**
 * Administrator  ：zhouyuru
 * 2020/11/9
 * Describe ：sh工具类
 */
public class SortUtils {
    public static CustomerBean bean;
    public static SmartRefreshLayout srl_refresh;
    //    筛选框
    public static List<CustomerBean.Fields_list> fields_list = new ArrayList<CustomerBean.Fields_list>();
    //    查看用户所属
    public static List<CustomerBean.Scene_list> scene_list = new ArrayList<CustomerBean.Scene_list>();
    //    用户列表
    private static List<CustomerBean.Custome_list> custome_lists = new ArrayList<CustomerBean.Custome_list>();

    //客户数据
    public static void inData(final int page, ActionItem actionItem, List<SelectValueUtils.Select> list, Context mContext, SmartRefreshLayout smartRefreshLayout) {
        srl_refresh = smartRefreshLayout;
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("p", page);
            if (actionItem != null) {
                parame.put("order_field", actionItem.getDate().toString());
                parame.put("order_type", actionItem.getSort().toString());
            } else {
//                默认按最新的创建的排序
                parame.put("order_field", "create_time");
                parame.put("order_type", "desc");
            }
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    parame.put(list.get(i).getKey(), list.get(i).getValue());
                }
            }
            Log.d("json===//", "请求地址1:" + Url.DOMAIN_Index);

            HttpClient.getInstance().post(mContext, DOMAIN_Index, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
                @Override
                public void onSuccess(CustomerBean result) {
                    String datas = new Gson().toJson(result);
                    Log.e("json=====fragment1", datas);
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            if (page == 1) {
                                bean = null;
                            }
                        }
                        if (result.getList() != null) {
                            bean = result;
                        }
                    } else {
                        AppToast.showToast(result.getInfo());
                    }
                }

                @Override
                public void onError(String msg) {
                    if (!srl_refresh.isLoading()) {
                        custome_lists.clear();
                    }
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    if (srl_refresh.isRefreshing()) {
                        srl_refresh.finishRefresh(500);
                    }
                    if (srl_refresh.isLoading()) {
                        srl_refresh.finishLoadMore();
                    }
                    ShowViews(bean, page);

                }
            });
        }
    }

    //公海数据
    public static void inData2(final int page, ActionItem actionItem, List<SelectValueUtils.SelectPool> list,  Context mContext, SmartRefreshLayout smartRefreshLayout) {
        srl_refresh = smartRefreshLayout;
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("p", page);
            if (actionItem != null) {
                parame.put("order_field", actionItem.getDate().toString());
                parame.put("order_type", actionItem.getSort().toString());
            } else {
//                默认按最新的创建的排序
                parame.put("order_field", "create_time");
                parame.put("order_type", "desc");
            }
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    parame.put(list.get(i).getKey(), list.get(i).getValue());
                }
            }

            HttpClient.getInstance().post(mContext, DOMAIN_Resource, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
                @Override
                public void onSuccess(CustomerBean result) {
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            if (page == 1) {
                                bean = null;
                            }
                        }
                        if (result.getList() != null) {
                            bean = result;

                        }
                    } else {
                        AppToast.showToast(result.getInfo());
                    }
                }

                @Override
                public void onError(String msg) {
                    if (!srl_refresh.isLoading()) {
                        custome_lists.clear();
                    }
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    if (srl_refresh.isRefreshing()) {
                        srl_refresh.finishRefresh(500);
                    }
                    if (srl_refresh.isLoading()) {
                        srl_refresh.finishLoadMore();
                    }
                    ShowViews2(bean, page);
                }
            });
        }
    }

//    public static void inData3(final int page, String content, String titles, Context mContext, String url, ActionItem actionItem, List<SelectValueUtils.Select> list, List<SelectValueUtils.By> by, SmartRefreshLayout smartRefreshLayout) {
//        srl_refresh = smartRefreshLayout;
//        if (!TextUtils.isEmpty(App.getToken()) && !TextUtils.isEmpty(content)) {
//
//            Map<String, Object> parame = new HashMap<>();
//            parame.put("token", App.getToken());
//            parame.put("p", page);
//            parame.put("csname", content);
//            if (titles.equals("客户列表")) {
//                url = DOMAIN_Index;
//            } else if (titles.equals("公海列表")) {
//                url = DOMAIN_Resource;
//            }
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
//            if (by != null && by.size() > 0) {
//                for (int i = 0; i < by.size(); i++) {
//                    parame.put(by.get(i).getKey(), by.get(i).getValue());
//                }
//            }
//            HttpClient.getInstance().post(mContext, url, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
//                @Override
//                public void onSuccess(CustomerBean result) {
//                    if (result.getInfo().equals("success")) {
//                        if (!srl_refresh.isLoading()) {
//                            if (page == 1) {
//                                bean = null;
//                            }
//                        }
//                        bean = result;
//
//                    } else {
//                        AppToast.showToast(result.getInfo());
//                    }
//
//
//                }
//
//                @Override
//                public void onError(String msg) {
//                    bean = null;
//
//                    AppToast.showToast(msg);
//                }
//
//                @Override
//                public void onCancelled(Callback.CancelledException var1) {
//                }
//
//                @Override
//                public void onFinished() {
//                    if (srl_refresh.isRefreshing()) {
//                        srl_refresh.finishRefresh(500);
//                    }
//                    if (srl_refresh.isLoading()) {
//                        srl_refresh.finishLoadMore();
//                    }
//                    SearchActivity.ShowView(bean, page);
//
//                }
//            });
//        }
//    }


}
