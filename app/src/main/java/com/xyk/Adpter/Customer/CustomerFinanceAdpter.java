package com.xyk.Adpter.Customer;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyk.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xyk.Entity.CustomerFinancetEntity;
import com.xyk.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerFinanceAdpter extends BaseCommonAdapter<CustomerFinancetEntity.FinancetList> {
    private TextView status_name, tv_name, pay_time, money, receipt_account;
    private LinearLayout ll_state;

    public CustomerFinanceAdpter(Context mContext, int customer_finance_list, ArrayList<CustomerFinancetEntity.FinancetList> lists) {
        super(mContext, customer_finance_list, lists);

    }

    @Override
    protected void convert(ViewHolder holder, CustomerFinancetEntity.FinancetList financetList, int position) {
        ll_state = holder.itemView.findViewById(R.id.ll_state);
        status_name = holder.itemView.findViewById(R.id.status_name);
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        pay_time = holder.itemView.findViewById(R.id.pay_time);
        money = holder.itemView.findViewById(R.id.money);
        receipt_account = holder.itemView.findViewById(R.id.receipt_account);
        status_name.setText(financetList.getStatus_name());
        tv_name.setText(financetList.getName());
        pay_time.setText(financetList.getPay_time());
        money.setText("￥:" + financetList.getMoney());
        receipt_account.setText("合同编号：" + financetList.getContract_number());
        if (financetList.getStatus_name().equals("待审")) {
//           待审
            ll_state.setBackgroundResource(R.drawable.kh_details_jd_ds);
        }
        if (financetList.getStatus_name().equals("通过")) {
//           待审
            ll_state.setBackgroundResource(R.drawable.kh_details_jd);
        }
        if (financetList.getStatus_name().equals("驳回")) {
//           待审
            ll_state.setBackgroundResource(R.drawable.kh_details_jd_jj);
        }
    }
}
