package com.ymz.Adpter.Message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ymz.Adpter.AdpterUtils.BaseCommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/9/25
 * Describe ：
 */
public class MessageAdpter extends BaseCommonAdapter {

    public MessageAdpter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
    }
}
