package com.yh.Adpter.Genral;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yh.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yh.Adpter.Genral.SelectGenralAdpter;
import com.yh.Dialog.SelectGeneralDialog;
import com.yh.Dialog.SelectGeneralDialogT;
import com.yh.Entity.FollowBean;
import com.yh.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class SelectGenralAdpterT extends BaseCommonAdapter<FollowBean.States> {
    private TextView textView, tv_state_id;
    private TextView rb_name;
    private SelectGeneralDialogT selectGeneralDialog;

    public SelectGenralAdpterT(Context mContext, int id, ArrayList<FollowBean.States> list, TextView textView, SelectGeneralDialogT selectGeneralDialog, TextView tv_state_id) {
        super(mContext, id, list);
        this.textView = textView;
        this.selectGeneralDialog = selectGeneralDialog;
        this.tv_state_id = tv_state_id;
    }

    @Override
    protected void convert(ViewHolder holder, final FollowBean.States states, int position) {
        rb_name = holder.itemView.findViewById(R.id.rb_name);
        rb_name.setText(states.getValue());
        if (!TextUtils.isEmpty(textView.getText())) {
            if (textView.getText().equals(states.getValue()))
                rb_name.setTextColor(mContext.getResources().getColor(R.color.sys_qs));

        }
        rb_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(states.getValue());
                tv_state_id.setText(states.getKey());
                textView.setTextColor(Color.parseColor("#313131"));
                selectGeneralDialog.dismiss();

            }
        });
    }
}
