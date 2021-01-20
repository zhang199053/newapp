package com.yh.Adpter.Genral;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yh.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yh.Dialog.SelectGeneralDialog;
import com.yh.Entity.AddCustomerEntity;
import com.yh.Entity.CallStateEntity;
import com.yh.Entity.CustomerLevelEntity;
import com.yh.Entity.CustomerStateEntity;
import com.yh.Entity.FollowStateEntity;
import com.yh.Entity.MessageStateEntity;
import com.yh.Entity.ProjectStateEntity;
import com.yh.Entity.SexEntity;
import com.yh.R;
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
