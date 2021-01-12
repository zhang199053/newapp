package com.xsl.Adpter.Genral;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xsl.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xsl.Dialog.SelectGeneralDialog;
import com.xsl.Entity.AddCustomerEntity;
import com.xsl.Entity.CallStateEntity;
import com.xsl.Entity.CustomerLevelEntity;
import com.xsl.Entity.CustomerStateEntity;
import com.xsl.Entity.FollowStateEntity;
import com.xsl.Entity.MessageStateEntity;
import com.xsl.Entity.ProjectStateEntity;
import com.xsl.Entity.SexEntity;
import com.xsl.R;
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
