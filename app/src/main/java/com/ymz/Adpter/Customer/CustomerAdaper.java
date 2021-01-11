package com.ymz.Adpter.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymz.Adpter.AdpterUtils.BaseCommonAdapter;
import com.ymz.App.App;
import com.ymz.Entity.CustomerBean;
import com.ymz.Model.Customer.CustomerDetailsActivity;
import com.ymz.Model.MakeCall.CallActivity;
import com.ymz.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import static com.ymz.Utils.UrlUtils.Url.dialpanel_phone;

/**
 * Administrator  ：zhouyuru
 * 2020/10/28
 * Describe ：
 */
public class CustomerAdaper extends BaseCommonAdapter<CustomerBean.Custome_list> {
    TextView tv_name, tv_xm, tv_fl, tv_num, tv_tel;
    //    拨打电话
    LinearLayout ll_call;
    Activity activity;

    public CustomerAdaper(Context mContext, int id, List<CustomerBean.Custome_list> custome_lists, Activity activity) {
        super(mContext, id, custome_lists);
        this.activity = activity;
    }

    @Override
    protected void convert(ViewHolder holder, final CustomerBean.Custome_list bean, int position) {
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_name.setText(bean.getName());
        tv_tel = holder.itemView.findViewById(R.id.tv_tel);
        try {
            if (bean.getCrm_okpkzz().length() == 11) {
                String tt = bean.getCrm_okpkzz().substring(0, 3) + "****" + bean.getCrm_okpkzz().substring(8);
                tv_tel.setText(tt);
            } else {
                tv_tel.setText(bean.getCrm_okpkzz());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_xm = holder.itemView.findViewById(R.id.tv_xm);
        tv_xm.setText(bean.getIndustry());
        tv_fl = holder.itemView.findViewById(R.id.tv_fl);
        tv_fl.setText(bean.getCustomer_status());
        tv_num = holder.itemView.findViewById(R.id.tv_num);
        tv_num.setText("拨打次数：" + bean.getDial_count());
        ll_call = holder.itemView.findViewById(R.id.ll_call);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomerDetailsActivity.class).putExtra("customer_id", bean.getCustomer_id()).putExtra("name", bean.getName()).putExtra("create_time", bean.getCreate_time());
                App.startActivity(mContext, intent);

            }
        });
        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "拨号数据:" + bean.toString());
                CallActivity.Call(activity, mContext, bean.getCrm_okpkzz(), bean.getCustomer_id());
            }
        });
    }


}
