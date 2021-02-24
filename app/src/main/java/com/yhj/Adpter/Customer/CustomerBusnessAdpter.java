package com.yhj.Adpter.Customer;

import android.content.Context;
import android.widget.TextView;

import com.yhj.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yhj.Entity.CustomerBusinessEntity;
import com.yhj.Entity.CustomerContractEntity;
import com.yhj.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerBusnessAdpter extends BaseCommonAdapter<CustomerBusinessEntity.BusinessList> {
    private TextView tv_status_id, tv_name, tv_status_name, tv_create_time, tv_final_price;

    public CustomerBusnessAdpter(Context mContext, int customer_business_list, ArrayList<CustomerBusinessEntity.BusinessList> lists) {
        super(mContext, customer_business_list, lists);

    }

    @Override
    protected void convert(ViewHolder holder, CustomerBusinessEntity.BusinessList businessList, int position) {
        tv_status_id = holder.itemView.findViewById(R.id.tv_status_id);
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_status_name = holder.itemView.findViewById(R.id.tv_status_name);
        tv_create_time = holder.itemView.findViewById(R.id.tv_create_time);
        tv_final_price = holder.itemView.findViewById(R.id.tv_final_price);
        tv_status_id.setText(businessList.getSchedule() + "%");
        tv_name.setText(businessList.getName());
        tv_status_name.setText(businessList.getStatus_name());
        tv_create_time.setText(businessList.getCreate_time());
        tv_final_price.setText("￥:" + businessList.getFinal_price() );
    }
}
