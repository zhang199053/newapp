package com.ymz.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymz.Adpter.Customer.AddCustomerAdpter;
import com.ymz.Adpter.Genral.SelectGenralAdpter;
import com.ymz.App.BaseDialog;
import com.ymz.Entity.AddCustomerEntity;
import com.ymz.Entity.CallStateEntity;
import com.ymz.Entity.CustomerLevelEntity;
import com.ymz.Entity.CustomerStateEntity;
import com.ymz.Entity.FollowStateEntity;
import com.ymz.Entity.MessageStateEntity;
import com.ymz.Entity.ProjectStateEntity;
import com.ymz.Entity.SexEntity;
import com.ymz.Model.Customer.AddCustomerActivity;
import com.ymz.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/10
 * Describe ：选择通用弹窗
 */
public class SelectGeneralDialog extends BaseDialog {
    private RecyclerView rv_list;
    private EditText textView;
    private TextView ll_bottm01;
    private TextView tv_title;
    private String titles;
    private List<AddCustomerEntity.SelectValue> list = new ArrayList<>();
    public SelectGenralAdpter selectGenralAdpter;

    public SelectGeneralDialog(Context mContext, List<AddCustomerEntity.SelectValue> setting, EditText tv_context, String s) {
        super(mContext);
        this.textView = tv_context;
        this.titles = s;
        this.list = setting;
    }

    @Override
    protected int getContentView() {
        return R.layout.select_general_dialog;
    }

    @Override
    protected void initView() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        ll_bottm01 = findViewById(R.id.ll_bottm01);
        ll_bottm01.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(titles);
        selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, list, textView);
        rv_list.setAdapter(selectGenralAdpter);
        selectGenralAdpter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                textView.setText(list.get(position).getValue());
                textView.setTextColor(Color.parseColor("#313131"));
                String title = textView.getText().toString();
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void SetPosition() {
        setCanceledOnTouchOutside(true);
        //向上弹出
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(null);
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        lp.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bottm01:
                dismiss();
                break;
            default:
                break;
        }
    }
}
