package com.xsl.Adpter.Customer;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsl.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xsl.Entity.CustomerContractEntity;
import com.xsl.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerContractAdpter extends BaseCommonAdapter<CustomerContractEntity.ContractList> {

    private TextView tv_state, tv_name, tv_create_time, tv_price, tv_contract_name;
    private LinearLayout ll_state;

    public CustomerContractAdpter(Context mContext, int customer_contract_list, ArrayList<CustomerContractEntity.ContractList> lists) {
        super(mContext, customer_contract_list, lists);
    }

    @Override
    protected void convert(ViewHolder holder, CustomerContractEntity.ContractList contractList, int position) {
        ll_state = holder.itemView.findViewById(R.id.ll_state);
        tv_state = holder.itemView.findViewById(R.id.tv_state);
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_create_time = holder.itemView.findViewById(R.id.tv_create_time);
        tv_price = holder.itemView.findViewById(R.id.tv_price);
        tv_contract_name = holder.itemView.findViewById(R.id.tv_contract_name);
        tv_name.setText(contractList.getNumber());
        tv_create_time.setText(contractList.getCreate_time());
        tv_price.setText("￥:" + contractList.getPrice());
        tv_contract_name.setText("合同名称：" + contractList.getContract_name());
        if (contractList.getIs_checked().equals("1")) {
//            审核通过
            ll_state.setBackgroundResource(R.drawable.kh_details_jd);
            tv_state.setText("通过");
        } else if (contractList.getIs_checked().equals("0")) {
            ll_state.setBackgroundResource(R.drawable.kh_details_jd_ds);
            tv_state.setText("待审");
        } else if (contractList.getIs_checked().equals("3")) {
            ll_state.setBackgroundResource(R.drawable.kh_details_jd_ds);
            tv_state.setText("审批中");
        } else if (contractList.getIs_checked().equals("2")) {
            ll_state.setBackgroundResource(R.drawable.kh_details_jd_jj);
            tv_state.setText("拒绝");

        }
    }
}
