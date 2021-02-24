package com.yhj.Adpter.Genral;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yhj.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yhj.Dialog.SelectGeneralDialog;
import com.yhj.Entity.AddCustomerEntity;
import com.yhj.Entity.CallStateEntity;
import com.yhj.Entity.CustomerLevelEntity;
import com.yhj.Entity.CustomerStateEntity;
import com.yhj.Entity.FollowStateEntity;
import com.yhj.Entity.MessageStateEntity;
import com.yhj.Entity.ProjectStateEntity;
import com.yhj.Entity.SexEntity;
import com.yhj.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/10
 * Describe ：
 */
public class SelectGenralAdpter extends BaseCommonAdapter<AddCustomerEntity.SelectValue> {
    public TextView rb_name;
    private TextView textView;
    public SelectGenralAdpter(Context context, int layoutId, List<AddCustomerEntity.SelectValue> list, TextView textView) {
        super(context, layoutId, list);
        this.textView = textView;
    }

    @Override
    protected void convert(ViewHolder holder, final AddCustomerEntity.SelectValue selectValue, int position) {
        rb_name = holder.getConvertView().findViewById(R.id.rb_name);
        rb_name.setText(selectValue.getValue());
        String title = this.textView.getText().toString();
        if (!TextUtils.isEmpty(textView.getText())) {
            if (title.equals(selectValue.getValue()))
                rb_name.setTextColor(mContext.getResources().getColor(R.color.sys_qs));

        }

    }

}
