package com.xyk.Adpter.Customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyk.Adpter.AdpterUtils.BaseCommonAdapter;
import com.xyk.Entity.CustomerBean;
import com.xyk.R;
import com.xyk.Utils.SelectValueUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/4
 * Describe ：
 */
public class TableAdpter extends BaseCommonAdapter {
    //    筛选框
    private List<CustomerBean.Fields_list> fields_list = new ArrayList<CustomerBean.Fields_list>();
    //    list文字标题
    private TextView txt_title;
    //    网格框
    private GridView ll_list;
    //    当前所属页面
    private String titles;
    //    适配器
    private TableValueAdpter mAdapter = new TableValueAdpter();

    public TableAdpter(Context mContext, int id, List<CustomerBean.Fields_list> fields_list, String title) {
        super(mContext, id, fields_list);
        this.fields_list = fields_list;
        this.titles = title;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        txt_title = holder.itemView.findViewById(R.id.txt_title);
        ll_list = holder.itemView.findViewById(R.id.ll_list);
        mAdapter = new TableValueAdpter(mContext, fields_list.get(position).getSetting(), fields_list.get(position).getField(), titles);
        ll_list.setAdapter(mAdapter);
        if (fields_list.get(position).getSetting() != null && fields_list.get(position).getSetting().size() > 0) {
            txt_title.setText(fields_list.get(position).getName());
            txt_title.setVisibility(View.VISIBLE);
        } else {
            txt_title.setText("");
            txt_title.setVisibility(View.GONE);
        }
    }

    public void Refresh() {
        if (mAdapter != null) {
            if (titles.equals("客户列表")) {
                SelectValueUtils.list.clear();
            } else if (titles.equals("公海列表")) {
                SelectValueUtils.selectPools.clear();
            }
            notifyDataSetChanged();
        }
    }
}
