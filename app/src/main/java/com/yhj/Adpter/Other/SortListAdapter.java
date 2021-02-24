package com.yhj.Adpter.Other;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yhj.R;

import java.util.List;


public class SortListAdapter extends RecyclerView.Adapter<SortListAdapter.MyViewHolder> {
    private Context context;
    private List<String> lists;
    private OnItemClickListener mItemClickListener;
    private int type;

    private int clickTemp = -1;

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    public void setOnItemClickListener(Context context, SortListAdapter.OnItemClickListener itemClickListener) {
        this.context = context;
        this.mItemClickListener = itemClickListener;
    }

    //item的回调接口
    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public SortListAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.sort_item, parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(lists.get(position));

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(lists.get(position));
            }
        });

//        if (clickTemp == position) {
//            holder.tv.setBackgroundResource(R.color.beijing1);
//        } else {
//            holder.tv.setBackgroundResource(R.color.white);
//
//        }


    }


    @Override
    public int getItemCount() {
        return lists.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }

    }


}

