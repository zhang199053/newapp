package com.cyb.Model.Customer;

import android.content.Context;
import android.text.TextUtils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.cyb.App.App;
import com.cyb.Entity.CustomerBean;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.HttpUtils.BaseCallback;
import com.cyb.Utils.HttpUtils.HttpClient;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyb.Utils.UrlUtils.Url.DOMAIN_Index;

/**
 * Administrator  ：zhouyuru
 * 2020/10/28
 * Describe ：
 */
public class LoadCustomerUtils {
    private static List<CustomerBean.Custome_list> lists = new ArrayList<>();
    private static SmartRefreshLayout refresh;

    public static List<CustomerBean.Custome_list> initData(Context context, int page, String date, String sort, String condition, String by, SmartRefreshLayout srl_refresh) {
        if (!TextUtils.isEmpty(App.getToken()) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(sort)) {
            refresh = srl_refresh;
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("p", page);
            parame.put("order_field", date);
            parame.put("order_type", sort);
            if (!TextUtils.isEmpty(condition)) {
                parame.put("csname", condition);
            }
            if (!TextUtils.isEmpty(by)) {
                parame.put("by", by);

            }
            HttpClient.getInstance().post(context, DOMAIN_Index, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
                @Override
                public void onSuccess(CustomerBean result) {
                    if (result.getInfo().equals("success")) {
                        if (!refresh.isLoading()) {
                            lists.clear();
                        }
                        if (result.getList() != null) {
                            lists.addAll(result.getList());
                        }
                    } else {
                        AppToast.showToast(result.getInfo());
                    }


                }

                @Override
                public void onError(String msg) {
                    lists.clear();
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                }
            });
        }
        return lists;
    }

}