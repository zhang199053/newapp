package com.yh.Adpter.TrafficMeasurement;

import android.content.Context;

import com.yh.Adpter.AdpterUtils.BaseCommonAdapter;
import com.yh.Entity.ConversationEntity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/27
 * Describe ：
 */
public class TrafficMeasurementAdpter extends BaseCommonAdapter<ConversationEntity.Datas> {
    public TrafficMeasurementAdpter(Context context, int layoutId, List<ConversationEntity.Datas> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ConversationEntity.Datas datas, int position) {

    }
}