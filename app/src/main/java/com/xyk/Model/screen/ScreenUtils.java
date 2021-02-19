package com.xyk.Model.screen;

/**
 * Administrator  ：zhouyuru
 * 2020/11/9
 * Describe ：筛选工具类
 */
public class ScreenUtils {

//    public static void inData(final int page, ActionItem actionItem, List<SelectValueUtils.Select> list, List<SelectValueUtils.By> by) {
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
//            if (by != null && by.size() > 0) {
//                for (int i = 0; i < by.size(); i++) {
//                    parame.put(by.get(i).getKey(), by.get(i).getValue());
//                }
//            }
//            HttpClient.getInstance().post(mContext, DOMAIN_Index, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
//                @Override
//                public void onSuccess(CustomerBean result) {
//                    if (result.getInfo().equals("success")) {
//                        if (!srl_refresh.isLoading()) {
//                            if (page == 1) {
//                                custome_lists.clear();
//                            }
//                            fields_list.clear();
//                            scene_list.clear();
//                        }
//                        if (result.getList() != null) {
//                            custome_lists.addAll(result.getList());
//                            fields_list.addAll(result.getFields_list());
//                            scene_list.addAll(result.getScene_list());
//                        }
//                    } else {
//                        AppToast.showToast(result.getInfo());
//                    }
//                }
//
//                @Override
//                public void onError(String msg) {
//                    if (!srl_refresh.isLoading()) {
//                        custome_lists.clear();
//                    }
//                    AppToast.showToast(msg);
//                }
//
//                @Override
//                public void onCancelled(Callback.CancelledException var1) {
//                }
//
//                @Override
//                public void onFinished() {
//                    ShowView();
//                }
//            });
//        }
//    }
//
//    private static void ShowView() {
//        if (srl_refresh.isRefreshing()) {
//            srl_refresh.finishRefresh(500);
//        }
//
//        if (srl_refresh.isLoading()) {
//            srl_refresh.finishLoadMore();
//        }
//        if (custome_lists != null && custome_lists.size() > 0) {
//            customerAdaper.notifyDataSetChanged();
//            av_nodata.setVisibility(View.GONE);
//            rv_list.setVisibility(View.VISIBLE);
//        } else {
//            av_nodata.setVisibility(View.VISIBLE);
//            rv_list.setVisibility(View.GONE);
//        }
//    }
}
