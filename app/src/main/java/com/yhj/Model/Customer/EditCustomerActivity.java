package com.yhj.Model.Customer;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yhj.Adpter.Customer.AddCustomerAdpter;
import com.yhj.Adpter.Customer.EditCustomerAdpter;
import com.yhj.App.App;
import com.yhj.App.BaseActivity;
import com.yhj.Dialog.SelectDateDialog;
import com.yhj.Entity.AddCustomerEntity;
import com.yhj.Entity.CustomerDetailsTwoBean;
import com.yhj.Entity.CustomerInput;
import com.yhj.R;
import com.yhj.Utils.AppToast;
import com.yhj.Utils.HttpUtils.BaseCallback;
import com.yhj.Utils.HttpUtils.HttpClient;
import com.yhj.ViewUtils.AbnormalView;
import com.yhj.ViewUtils.NavigationBar;
import com.yhj.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Edit;

/**
 * Administrator  ：zhouyuru
 * 2020/11/5
 * Describe ：
 */
public class EditCustomerActivity extends BaseActivity {
    //    提交
    private ImageView bt_add;
    private EditCustomerAdpter editCustomerAdpter;
    private ListView rv_list;
    private List<CustomerDetailsTwoBean.Data> data = new ArrayList<>();
    private NavigationBar nb_agre;
    private List<CustomerInput.Inputs> list = new ArrayList<>();
    private String SName;

    @Override
    protected int getContentView() {
        return R.layout.edit_custmoer_activity;
    }

    @Override
    protected void initView() {
        data = (List<CustomerDetailsTwoBean.Data>) getIntent().getSerializableExtra("data");
        nb_agre = findViewById(R.id.nb_agre);
        nb_agre.setOnBackListen(this);
        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        rv_list = findViewById(R.id.rv_list);
        editCustomerAdpter = new EditCustomerAdpter(mContext, data);
        rv_list.setAdapter(editCustomerAdpter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                if (list.size() > 0) {
                    list.clear();
                }
                //    获取全部的子数量
                for (int i = 0; i < rv_list.getCount(); i++) {
                    LinearLayout relativeLayout = (LinearLayout) rv_list.getAdapter().getView(i, null, null);//RelativeLayout是listview的item父布局
//                    输入内容
                    EditText et = (EditText) relativeLayout.findViewById(R.id.rv_context);// 从layout中获得控件,根据其id
//                    字段名字
                    TextView name = (TextView) relativeLayout.findViewById(R.id.tv_name);// 从layout中获得控件,根据其id
                    //                    字段名
                    TextView tv_field = (TextView) relativeLayout.findViewById(R.id.tv_field);// 从layout中获得控件,根据其id
//                    记录是否验证
                    System.out.println("pos" + i + "字段id" + tv_field.getText() + "字段名称" + name.getText().toString() + "输入内容" + et.getText());
                    //保存用户数据
                    if (tv_field.getText().equals("owner_role_id") || tv_field.getText().equals("creator_role_id") || tv_field.getText().equals("contacts_id") || tv_field.getText().equals("customer_owner_id")) {
                    } else {
                        CustomerInput.Inputs bean = new CustomerInput.Inputs();
                        bean.setTv_field(tv_field.getText().toString());
                        bean.setName(name.getText().toString());
                        bean.setValue(et.getText().toString());
                        list.add(bean);
//                        设置编辑之后的名字，我存了下来 SNAme
                        if (bean.getTv_field().equals("name")) {
                            SName = bean.getValue();
                        }
                    }
                }
                if (check()) {
                    Edit();
                }
                break;
            default:
                break;
        }
    }

    private boolean check() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//                判断姓名是否验证
                if (list.get(i).getTv_field().equals("name") || list.get(i).getTv_field().equals("crm_okpkzz")) {
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

    //修改客户
    public void Edit() {
        String customer_id = CustomerDetailsActivity.customer_id;
        if (list.size() > 0 && !TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            ProgressDialogUtil.getInstance().startLoad(mContext);
            parame.put("token", App.getToken());
            parame.put("id", customer_id);
            for (int i = 0; i < list.size(); i++) {
                parame.put(list.get(i).getTv_field(), list.get(i).getValue());
            }
            HttpClient.getInstance().post(mContext, DOMAIN_Edit, parame, new BaseCallback<String>(String.class) {
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
        }
    }


}

