package com.xyk.Adpter.Genral;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xyk.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xyk.Dialog.SelectGeneralDialog;
import com.xyk.Entity.AddCustomerEntity;
import com.xyk.Entity.CallStateEntity;
import com.xyk.Entity.CustomerLevelEntity;
import com.xyk.Entity.CustomerStateEntity;
import com.xyk.Entity.FollowStateEntity;
import com.xyk.Entity.MessageStateEntity;
import com.xyk.Entity.ProjectStateEntity;
import com.xyk.Entity.SexEntity;
import com.xyk.R;
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
