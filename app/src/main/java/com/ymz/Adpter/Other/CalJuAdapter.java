package com.ymz.Adpter.Other;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymz.Entity.ConversationEntity;
import com.ymz.Entity.Statisticalbean;
import com.ymz.R;
import com.ymz.Utils.HideDataUtil;

import java.util.List;


public class CalJuAdapter extends RecyclerView.Adapter<CalJuAdapter.MyViewHolder> {
    private Context context;
    private List<ConversationEntity.DataBeanX.DataBean> lists;



    private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(Context context, OnItemClickListener itemClickListener) {
        this.context = context;
        this.mItemClickListener = itemClickListener;

    }

    //item的回调接口
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }


    public CalJuAdapter(Context context, List<ConversationEntity.DataBeanX.DataBean> lists) {
        this.context = context;
        this.lists = lists;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.calljl_item, parent,
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

        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_time.setText(lists.get(position).getCreated_time());
        holder.tv_number.setText(lists.get(position).getRealname());
        holder.tv_tel.setText(lists.get(position).getDuration()+"秒");
       // holder.tv_num.setText("电话号码："+lists.get(position).getDestination_number());
        holder.addertv.setText("录音地址:"+lists.get(position).getFile_path());
        if (lists.get(position).getIs_connected().equals("0")){
            holder.tv_pass.setText("通话状态：未接通");
        }else {
            holder.tv_pass.setText("通话状态：接通");
        }

        if (!TextUtils.isEmpty(lists.get(position).getDestination_number())){
            holder.tv_num.setText("电话号码："+HideDataUtil.bankCardReplaceWithStar(lists.get(position).getDestination_number()));
        }
}

    @Override
    public int getItemCount() {
        return lists.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_time,tv_num,tv_tel,tv_pass,tv_number,addertv;
        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            tv_pass = (TextView) view.findViewById(R.id.tv_pass);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            addertv = (TextView) view.findViewById(R.id.addertv);
        }

    }


}
