package com.cyb.Model.Customer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.cyb.Adpter.Customer.AddCustomerAdpter;
import com.cyb.App.App;
import com.cyb.App.BaseActivity;
import com.cyb.Dialog.SelectDateDialog;
import com.cyb.Dialog.SelectGeneralDialog;
import com.cyb.Entity.AddCustomerEntity;
import com.cyb.Entity.CallStateEntity;
import com.cyb.Entity.CustomerInput;
import com.cyb.Entity.CustomerLevelEntity;
import com.cyb.Entity.CustomerStateEntity;
import com.cyb.Entity.MessageStateEntity;
import com.cyb.Entity.ProjectStateEntity;
import com.cyb.Entity.SexEntity;
import com.cyb.R;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.HttpUtils.BaseCallback;
import com.cyb.Utils.HttpUtils.HttpClient;
import com.cyb.ViewUtils.AbnormalView;
import com.cyb.ViewUtils.NavigationBar;
import com.cyb.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyb.Utils.UrlUtils.Url.DOMAIN_Add;
import static com.cyb.Utils.UrlUtils.Url.DOMAIN_Call;
import static com.cyb.Utils.UrlUtils.Url.DOMAIN_Edit;
import static com.cyb.Utils.UrlUtils.Url.DOMAIN_Fields;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：添加客户页面
 */
public class AddCustomerActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    NavigationBar nb_agre;
    //    提交
    private ImageView bt_add;
    private ArrayList<AddCustomerEntity.Customer_list> lists = new ArrayList<>();
    private AddCustomerAdpter addCustomerAdpter;
    private ListView rv_list;
    AbnormalView av_nodata;
    private String pho;
    private List<CustomerInput.Inputs> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.add_custmoer_activity;
    }

    @Override
    protected void initView() {
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        av_nodata = findViewById(R.id.av_nodata);
        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        rv_list = findViewById(R.id.rv_list);
        pho=getIntent().getStringExtra("pho");
        addCustomerAdpter = new AddCustomerAdpter(mContext, lists,pho);
        rv_list.setAdapter(addCustomerAdpter);
        ShowInit();
    }

    //获取添加客户字段
    private void ShowInit() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("params", "{\"module\":\"customer\",\"action\":\"add\"}");
            HttpClient.getInstance().post(mContext, DOMAIN_Fields, parame, new BaseCallback<AddCustomerEntity>(AddCustomerEntity.class) {
                @Override
                public void onSuccess(AddCustomerEntity result) {
                    if (result.getInfo().equals("success")) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            lists.addAll(result.getData());
                            addCustomerAdpter.notifyDataSetChanged();
                            rv_list.setVisibility(View.VISIBLE);
                            av_nodata.setVisibility(View.GONE);
                        } else {
                            rv_list.setVisibility(View.GONE);
                            av_nodata.setVisibility(View.VISIBLE);
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
                }
            });
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                if (list.size() > 0) {
                    list.clear();
                }
                //                获取全部的子数量
                for (int i = 0; i < rv_list.getCount(); i++) {
                    LinearLayout relativeLayout = (LinearLayout) rv_list.getAdapter().getView(i, null, null);//RelativeLayout是listview的item父布局
//                    输入内容
                    EditText et = (EditText) relativeLayout.findViewById(R.id.rv_context);// 从layout中获得控件,根据其id
//                    字段名字
                    TextView name = (TextView) relativeLayout.findViewById(R.id.tv_name);// 从layout中获得控件,根据其id
                    //                    字段名
                    TextView tv_field = (TextView) relativeLayout.findViewById(R.id.tv_field);// 从layout中获得控件,根据其id
//                    记录是否验证
                    TextView is_validate = (TextView) relativeLayout.findViewById(R.id.is_validate);// 从layout中获得控件,根据其id
                    System.out.println("pos" + i + "字段id" + tv_field.getText() + "字段名称" + name.getText().toString() + "输入内容" + et.getText() + "是否为空" + is_validate.getText().toString());
                    //保存用户数据
                    CustomerInput.Inputs bean = new CustomerInput.Inputs();
                    bean.setTv_field(tv_field.getText().toString());
                    bean.setName(name.getText().toString());
                    bean.setValue(et.getText().toString());
                    bean.setIs_validate(is_validate.getText().toString());

                    list.add(bean);
                }
                if (check()) {
                    AddCustomer();
                }
            default:
                break;
        }
    }

    private boolean check() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//                判断是否验证
                if (list.get(i).getIs_validate().equals("1")) {
//                    值为空
                    if (TextUtils.isEmpty(list.get(i).getValue())) {
//                        提示不能为空
                        AppToast.showToast(list.get(i).getName() + "不能为空");
                        return false;
                    }
                }

            }
        } else {
            AppToast.showToast("请填写客户数据！");
        }
        return true;
    }

    //添加客户
    public void AddCustomer() {
        if (!TextUtils.isEmpty(App.getToken()) && list.size() > 0) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            for (int i = 0; i < list.size(); i++) {
                parame.put(list.get(i).getTv_field(), list.get(i).getValue());
            }
            ProgressDialogUtil.getInstance().startLoad(mContext);
            HttpClient.getInstance().post(mContext, DOMAIN_Add, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        AppToast.showToast(json.getString("info"));
                        finish();
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
                }
            });
        } else {
            AppToast.showToast("请先登陆");
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
    }
}
