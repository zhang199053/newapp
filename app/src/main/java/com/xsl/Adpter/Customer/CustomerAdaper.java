package com.xsl.Adpter.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xsl.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xsl.App.App;
import com.xsl.Entity.CustomerBean;
import com.xsl.Model.Customer.CustomerDetailsActivity;
import com.xsl.Model.MakeCall.CallActivity;
import com.xsl.R;
import com.xsl.Utils.HideDataUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import static com.xsl.Utils.UrlUtils.Url.dialpanel_phone;

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
    Context mContext;

    public CustomerAdaper(Context mContext, int id, List<CustomerBean.Custome_list> custome_lists, Activity activity) {
        super(mContext, id, custome_lists);
        this.mContext=mContext;
        this.activity = activity;
    }

    @Override
    protected void convert(ViewHolder holder, final CustomerBean.Custome_list bean, int position) {
        tv_name = holder.itemView.findViewById(R.id.tv_name);
        tv_name.setText(bean.getName());
        tv_tel = holder.itemView.findViewById(R.id.tv_tel);
        try {
            if (bean.getCrm_okpkzz().length() == 11) {
                Log.e("srcret==",App.getSecret()+"///");

                if (App.getSecret().equals("0")){
                    tv_tel.setText(bean.getCrm_okpkzz());
                }else if (App.getSecret().equals("1")){
                    String tt = HideDataUtil.bankCardReplaceWithStar(bean.getCrm_okpkzz());
                    tv_tel.setText(tt);
                }


            } else {
                tv_tel.setText(bean.getCrm_okpkzz());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_xm = holder.itemView.findViewById(R.id.tv_xm);
        tv_xm.setText(bean.getIndustry());
        tv_fl = holder.itemView.findViewById(R.id.tv_fl);
        //

        if (!TextUtils.isEmpty(bean.getCustomer_status())){
            if (bean.getCustomer_status().equals("未跟进")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }else  if (bean.getCustomer_status().equals("意向客户")){
                tv_fl.setBackgroundResource(R.drawable.textlabel);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }else  if (bean.getCustomer_status().equals("重点客户")){
                tv_fl.setBackgroundResource(R.drawable.textlabel2);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }else  if (bean.getCustomer_status().equals("未接通")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }else  if (bean.getCustomer_status().equals("意向不大")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }else  if (bean.getCustomer_status().equals("无效客户")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }else  if (bean.getCustomer_status().equals("已成交")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }else  if (bean.getCustomer_status().equals("考察未成交")){
                tv_fl.setBackgroundResource(R.drawable.button_submenu_5);
                tv_fl.setTextColor(ContextCompat.getColor(mContext, R.color.huise));
            }

            tv_fl.setText(bean.getCustomer_status());
        }


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
                dialpanel_phone = bean.getCrm_okpkzz();
                CallActivity.Call(activity, mContext, bean.getCrm_okpkzz(), bean.getCustomer_id(),0);
            }
        });
    }


}
