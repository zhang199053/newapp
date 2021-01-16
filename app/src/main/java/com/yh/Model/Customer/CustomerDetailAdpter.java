package com.yh.Model.Customer;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yh.Entity.CustomerDetailsTwoBean;
import com.yh.R;
import com.yh.Utils.HideDataUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import static com.yh.Model.Customer.CustomerDetailsActivity.SetName;

/**
 * Administrator  ：zhouyuru
 * 2020/10/29
 * Describe ：
 */
public class CustomerDetailAdpter extends BaseCommonAdapter<CustomerDetailsTwoBean.Data> {
    TextView tv_tv_fzr_title, tv_fzr;
    LinearLayout ll_fzr;
    TextView tv_name;
    String phone;
    public String Name;

    public CustomerDetailAdpter(Context mContext, int id, List<CustomerDetailsTwoBean.Data> data, TextView tv_name) {
        super(mContext, id, data);
        this.tv_name = tv_name;
    }

    @Override
    protected void convert(ViewHolder holder, final CustomerDetailsTwoBean.Data data, int position) {
        tv_tv_fzr_title = holder.itemView.findViewById(R.id.tv_tv_fzr_title);
        tv_fzr = holder.itemView.findViewById(R.id.tv_fzr);
        ll_fzr = holder.itemView.findViewById(R.id.ll_fzr);
        //客户id，客户创建人id，客户等级
        if (data.getField().equals("contacts_id") || data.getField().equals("customer_owner_id")) {
            ll_fzr.setVisibility(View.GONE);
        } else {
            ll_fzr.setVisibility(View.VISIBLE);
        }
        tv_tv_fzr_title.setText(data.getName() + "：");

        if (!TextUtils.isEmpty(data.getName())){
            if (data.getName().equals("电话")){
                if (!TextUtils.isEmpty(data.getVal())){
                    String pho= HideDataUtil.bankCardReplaceWithStar(data.getVal());
                    tv_fzr.setText(pho);
                }
            }else {
                tv_fzr.setText(data.getVal());
            }
        }


        if (data.getField().equals("name")) {
//            就是这里，获取修改后的名字的值，给tv——name重新赋值
            Name = data.getVal();
            String name3 = tv_name.getText().toString();
            if (!name3.equals(Name)) {
                SetName(Name);
            }
        }
        if (data.getField().equals("crm_okpkzz")) {
            phone = data.getVal();
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
