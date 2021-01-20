package com.yh.Dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yh.Adpter.Genral.SelectGenralAdpterT;
import com.yh.App.BaseDialog;
import com.yh.Entity.FollowBean;
import com.yh.R;

import java.util.ArrayList;

/**
 * Administrator  ：zhouyuru
 * 2020/10/10
 * Describe ：跟进日志通用弹窗
 */
public class SelectGeneralDialogT extends BaseDialog {
    private SelectGenralAdpterT selectGenralAdpter;
    private ArrayList<FollowBean.States> list = new ArrayList<>();
    private RecyclerView rv_list;
    private TextView textView, tv_state_id;
    private TextView ll_bottm01;
    private TextView tv_title;
    private String titles;

    public SelectGeneralDialogT(Context context, ArrayList<FollowBean.States> list, TextView tv, String title, TextView tv_state_id) {
        super(context);
        this.list = list;
        this.textView = tv;
        this.titles = title;
        this.tv_state_id = tv_state_id;
    }

    @Override
    protected int getContentView() {
        return R.layout.select_general_dialogt;
    }

    @Override
    protected void initView() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        ll_bottm01 = findViewById(R.id.ll_bottm01);
        ll_bottm01.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(titles);
        selectGenralAdpter = new SelectGenralAdpterT(mContext, R.layout.select_general_item, list, textView, this, tv_state_id);
        rv_list.setAdapter(selectGenralAdpter);
        selectGenralAdpter.notifyDataSetChanged();
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

//        if (object instanceof CallStateEntity) {
//            CallStateEntity callStateEntity = new CallStateEntity();
//            callStateEntity.setId("1");
//            callStateEntity.setCallState("未接通");
//            CallStateEntity callStateEntity2 = new CallStateEntity();
//            callStateEntity2.setId("2");
//            callStateEntity2.setCallState("已接");
//            CallStateEntity callStateEntity3 = new CallStateEntity();
//            callStateEntity3.setId("3");
//            callStateEntity3.setCallState("空号/停机/关机");
//            callStateEntities.add(callStateEntity);
//            callStateEntities.add(callStateEntity2);
//            callStateEntities.add(callStateEntity3);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, callStateEntities, textView, this);
//
//        }
//        if (object instanceof SexEntity) {
//            SexEntity sexEntity = new SexEntity();
//            sexEntity.setId("1");
//            sexEntity.setSex("男");
//            SexEntity sexEntity1 = new SexEntity();
//            sexEntity1.setId("2");
//            sexEntity1.setSex("女");
//            sexEntities.add(sexEntity);
//            sexEntities.add(sexEntity1);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, sexEntities, textView, this);
//
//        }
//        if (object instanceof ProjectStateEntity) {
//            ProjectStateEntity projectStateEntity = new ProjectStateEntity();
//            projectStateEntity.setId("1");
//            projectStateEntity.setProjectState("项目一");
//            ProjectStateEntity projectStateEntity1 = new ProjectStateEntity();
//            projectStateEntity1.setId("2");
//            projectStateEntity1.setProjectState("项目二");
//            projectStateEntities.add(projectStateEntity);
//            projectStateEntities.add(projectStateEntity1);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, projectStateEntities, textView, this);
//        }
//        if (object instanceof CustomerStateEntity) {
//            CustomerStateEntity customerStateEntity = new CustomerStateEntity();
//            customerStateEntity.setId("1");
//            customerStateEntity.setCustomerState("未跟进");
//            CustomerStateEntity customerStateEntity2 = new CustomerStateEntity();
//            customerStateEntity2.setId("2");
//            customerStateEntity2.setCustomerState("意向客户");
//            CustomerStateEntity customerStateEntity3 = new CustomerStateEntity();
//            customerStateEntity3.setId("3");
//            customerStateEntity3.setCustomerState("重点客户");
//            CustomerStateEntity customerStateEntity1 = new CustomerStateEntity();
//            customerStateEntity1.setId("4");
//            customerStateEntity1.setCustomerState("未接通");
//            CustomerStateEntity customerStateEntity4 = new CustomerStateEntity();
//            customerStateEntity4.setId("5");
//            customerStateEntity4.setCustomerState("意向不大");
//            CustomerStateEntity customerStateEntity5 = new CustomerStateEntity();
//            customerStateEntity5.setId("6");
//            customerStateEntity5.setCustomerState("无效客户");
//            CustomerStateEntity customerStateEntity6 = new CustomerStateEntity();
//            customerStateEntity6.setId("7");
//            customerStateEntity6.setCustomerState("已成交");
//            CustomerStateEntity customerStateEntity7 = new CustomerStateEntity();
//            customerStateEntity7.setId("8");
//            customerStateEntity7.setCustomerState("考察未成交");
//            customerStateEntities.add(customerStateEntity);
//            customerStateEntities.add(customerStateEntity2);
//            customerStateEntities.add(customerStateEntity1);
//            customerStateEntities.add(customerStateEntity3);
//            customerStateEntities.add(customerStateEntity4);
//            customerStateEntities.add(customerStateEntity5);
//            customerStateEntities.add(customerStateEntity6);
//            customerStateEntities.add(customerStateEntity7);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, customerStateEntities, textView, this);
//        }
//        if (object instanceof MessageStateEntity) {
//            MessageStateEntity messageStateEntity = new MessageStateEntity();
//            messageStateEntity.setId("1");
//            messageStateEntity.setMessageState("百度推广");
//            MessageStateEntity messageStateEntity1 = new MessageStateEntity();
//            messageStateEntity1.setId("2");
//            messageStateEntity1.setMessageState("腾讯课堂");
//            messageStateEntities.add(messageStateEntity);
//            messageStateEntities.add(messageStateEntity1);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, messageStateEntities, textView, this);
//        }
//        if (object instanceof CustomerLevelEntity) {
//            CustomerLevelEntity customerLevelEntity = new CustomerLevelEntity();
//            customerLevelEntity.setId("1");
//            customerLevelEntity.setCustomerLevel("1级");
//            CustomerLevelEntity customerLevelEntity1 = new CustomerLevelEntity();
//            customerLevelEntity1.setId("2");
//            customerLevelEntity1.setCustomerLevel("2级");
//            customerLevelEntities.add(customerLevelEntity);
//            customerLevelEntities.add(customerLevelEntity1);
//            selectGenralAdpter = new SelectGenralAdpter(mContext, R.layout.select_general_item, customerLevelEntities, textView, this);
//        }
//        if (object instanceof FollowStateEntity) {
//            FollowStateEntity callStateEntity = new FollowStateEntity();
//            callStateEntity.setId("1");
//            callStateEntity.setFollowState("已加微信");
//            FollowStateEntity callStateEntity2 = new FollowStateEntity();
//            callStateEntity2.setId("2");
//            callStateEntity2.setFollowState("已拨电话");
//            FollowStateEntity callStateEntity3 = new FollowStateEntity();
//            callStateEntity3.setId("3");
//            callStateEntity3.setFollowState("空号/停机/关机");
//            followStateEntities.add(callStateEntity);
//            followStateEntities.add(callStateEntity2);
//            followStateEntities.add(callStateEntity3);
//
//        }


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
