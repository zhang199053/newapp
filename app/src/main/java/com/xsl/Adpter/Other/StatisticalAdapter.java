package com.xsl.Adpter.Other;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xsl.Entity.Statisticalbean;
import com.xsl.R;

import java.util.List;


public class StatisticalAdapter extends RecyclerView.Adapter<StatisticalAdapter.MyViewHolder> {
    private Context context;
    private List<Statisticalbean.DataBeanX.DataBean> lists;



    private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(Context context, OnItemClickListener itemClickListener) {
        this.context = context;
        this.mItemClickListener = itemClickListener;

    }

    //item的回调接口
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }


    public StatisticalAdapter(Context context, List<Statisticalbean.DataBeanX.DataBean> lists) {
        this.context = context;
        this.lists = lists;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.statistical_item, parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mItemClickListener.onItemClick(position);
//            }
//        });

        holder.tv_name.setText(lists.get(position).getFull_name());
        holder.tv_time.setText(lists.get(position).getCall_date());
        holder.tv_number.setText(lists.get(position).getTel_total());
        holder.tv_tel.setText(lists.get(position).getTotal_minutes_per());
        holder.tv_pass.setText("接通率："+lists.get(position).getConnected_data());
        holder.tv_num.setText("接通总数："+lists.get(position).getConnected_total());
}

    @Override
    public int getItemCount() {
        return lists.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_time,tv_num,tv_tel,tv_pass,tv_number;
        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            tv_pass = (TextView) view.findViewById(R.id.tv_pass);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
        }

    }


}
