package com.xyk.Adpter.Customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xyk.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xyk.Entity.CustomerLogEntity;
import com.xyk.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerLogAdpter extends BaseCommonAdapter<CustomerLogEntity.LogList> {
    private TextView tv_name, tv_context, status_name, nextstep_time, create_date;

    public CustomerLogAdpter(Context context, int layoutId, List<CustomerLogEntity.LogList> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CustomerLogEntity.LogList logList, int position) {
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_context = holder.itemView.findViewById(R.id.tv_context);
        status_name = holder.itemView.findViewById(R.id.status_name);
        nextstep_time = holder.itemView.findViewById(R.id.nextstep_time);
        create_date = holder.itemView.findViewById(R.id.create_date);
        tv_name.setText(logList.getOwner().getUser_name());
        tv_context.setText(logList.getContent());
        status_name.setText("跟进类型：" + logList.getStatus_name());
        nextstep_time.setText("下次联系时间：" + logList.getNextstep_time());
        create_date.setText(logList.getCreate_date());
    }
}
