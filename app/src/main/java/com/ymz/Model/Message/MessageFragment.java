package com.ymz.Model.Message;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ymz.Adpter.Message.MessageAdpter;
import com.ymz.App.BaseFragment;
import com.ymz.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/9/25
 * Describe ：消息页面
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rv_message;
    private MessageAdpter adpter;
    private List<String> data = new ArrayList<>();
    private TextView tv;

    @Override
    protected int setContentView() {
        return R.layout.message_fragment;
    }

    @Override
    protected void init() {
        rv_message = rootView.findViewById(R.id.rv_message);
        rv_message.setLayoutManager(new LinearLayoutManager(mContext));
        adpter = new MessageAdpter(mContext, R.layout.message_item, data);
        rv_message.setAdapter(adpter);
        tv = rootView.findViewById(R.id.tv);
        tv.setOnClickListener(this);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                break;
            default:
                break;

        }
    }
}
