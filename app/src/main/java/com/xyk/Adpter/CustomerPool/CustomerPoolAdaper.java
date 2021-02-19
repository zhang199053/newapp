package com.xyk.Adpter.CustomerPool;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyk.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xyk.App.App;
import com.xyk.Entity.CustomerBean;
import com.xyk.Model.CustomerPool.CustomerPoolDetailsActivity;
import com.xyk.Model.CustomerPool.CustomerPoolListFragment;
import com.xyk.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/28
 * Describe ：
 */
public class CustomerPoolAdaper extends BaseCommonAdapter<CustomerBean.Custome_list> {
    TextView tv_name, tv_xm, tv_fl;
    LinearLayout ll_ss;

    public CustomerPoolAdaper(Context context, int layoutId, List<CustomerBean.Custome_list> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final CustomerBean.Custome_list bean, final int position) {
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_name.setText(bean.getName());
        tv_xm = holder.itemView.findViewById(R.id.tv_xm);
        tv_xm.setText(bean.getIndustry());
        tv_fl = holder.itemView.findViewById(R.id.tv_fl);
        tv_fl.setText(bean.getCustomer_status());
        ll_ss = holder.itemView.findViewById(R.id.ll_ss);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomerPoolDetailsActivity.class).putExtra("customer_id", bean.getCustomer_id()).putExtra("name", bean.getName()).putExtra("create_time", bean.getCreate_time()).putExtra("pos", position);
                App.startActivity(mContext, intent);
            }
        });
        ll_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerPoolListFragment.logout(bean.getCustomer_id(), position, null);

            }
        });
    }
}
