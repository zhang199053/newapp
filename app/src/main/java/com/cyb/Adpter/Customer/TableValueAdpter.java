package com.cyb.Adpter.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyb.Entity.CustomerBean;
import com.cyb.R;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.SelectValueUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/9
 * Describe ：其他筛选类型
 */
public class TableValueAdpter extends BaseAdapter {
    private Context mContext;
    //    字段名字
    private String name;
    //    选择框
    private List<CustomerBean.SelectValues> setting = new ArrayList<>();
    //    当前所属页面名字
    private String string;
    //    当前pos
    private int pos = 0;

    public TableValueAdpter(Context mContext, List<CustomerBean.SelectValues> setting, String field, String titles) {
        this.mContext = mContext;
        this.setting = setting;
        this.name = field;
        this.string = titles;
    }

    public TableValueAdpter() {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return setting != null ? setting.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return setting.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        final CustomerBean.SelectValues bean = setting.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.table_value_item, null);
        holder.txt_title = convertView.findViewById(R.id.txt_title);
        holder.ll_value = convertView.findViewById(R.id.ll_value);
        holder.iv_img = convertView.findViewById(R.id.iv_img);
//        展示item的内容
        holder.txt_title.setText(bean.getValue());
//        如果当前为客户列表页面
        if (string.equals("客户列表")) {
            if (SelectValueUtils.list.size() > 0) {
//                改变已选中的样式
                for (int i = 0; i < SelectValueUtils.list.size(); i++) {
                    if (SelectValueUtils.list.get(i).getValue().equals(bean.getKey()) && SelectValueUtils.list.get(i).getKey().equals(name)) {
                        holder.ll_value.setSelected(true);
                        holder.ll_value.setBackgroundResource(R.drawable.ll_khxq_xz_back);
                        holder.txt_title.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                        holder.iv_img.setVisibility(View.VISIBLE);
                    }
                }
            } else {
//                未选中样式置空
                holder.ll_value.setSelected(false);
                holder.ll_value.setBackgroundResource(R.drawable.ll_khxq_back);
                holder.txt_title.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.iv_img.setVisibility(View.GONE);

            }
        } else if (string.equals("公海列表")) {
            if (SelectValueUtils.selectPools.size() > 0) {
                for (int i = 0; i < SelectValueUtils.selectPools.size(); i++) {
                    if (SelectValueUtils.selectPools.get(i).getValue().equals(bean.getKey()) && SelectValueUtils.selectPools.get(i).getKey().equals(name)) {
                        holder.ll_value.setSelected(true);
                        holder.ll_value.setBackgroundResource(R.drawable.ll_khxq_xz_back);
                        holder.txt_title.setTextColor(mContext.getResources().getColor(R.color.sys_qs));
                        holder.iv_img.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                holder.ll_value.setSelected(false);
                holder.ll_value.setBackgroundResource(R.drawable.ll_khxq_back);
                holder.txt_title.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.iv_img.setVisibility(View.GONE);
            }
        }
        holder.ll_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string.equals("客户列表")) {
//                    判断类型时候存在，存在先添加
                    if (holder.ll_value.isSelected()) {
                        if (SelectValueUtils.list.size() > 0) {
                            for (int i = 0; i < SelectValueUtils.list.size(); i++) {
                                if (SelectValueUtils.list.get(i).getKey().equals(name)) {
                                    SelectValueUtils.list.remove(i);
                                }
                            }
                        } else {
//                    添加客户类型
                            SelectValueUtils.Select select = new SelectValueUtils.Select();
                            select.setKey(name);
                            select.setValue(bean.getKey());
                            SelectValueUtils.list.add(select);
                        }
                    } else {
                        if (SelectValueUtils.list.size() > 0) {
                            for (int i = 0; i < SelectValueUtils.list.size(); i++) {
                                if (SelectValueUtils.list.get(i).getKey().equals(name)) {
                                    SelectValueUtils.list.remove(i);
                                }
                            }
                        }
                        SelectValueUtils.Select select = new SelectValueUtils.Select();
                        select.setKey(name);
                        select.setValue(bean.getKey());
                        SelectValueUtils.list.add(select);
                    }
                    notifyDataSetChanged();

//                    判断类型时候存在，存在先添加
                } else if (string.equals("公海列表")) {

                    if (holder.ll_value.isSelected()) {
                        if (SelectValueUtils.selectPools.size() > 0) {
                            for (int i = 0; i < SelectValueUtils.selectPools.size(); i++) {
                                if (SelectValueUtils.selectPools.get(i).getKey().equals(name)) {
                                    SelectValueUtils.selectPools.remove(i);
                                }
                            }
                        } else {
                            //                    添加客户类型
                            SelectValueUtils.SelectPool select = new SelectValueUtils.SelectPool();
                            select.setKey(name);
                            select.setValue(bean.getValue());
                            SelectValueUtils.selectPools.add(select);
                        }
                    } else {
                        if (SelectValueUtils.selectPools.size() > 0) {
                            for (int i = 0; i < SelectValueUtils.selectPools.size(); i++) {
                                if (SelectValueUtils.selectPools.get(i).getKey().equals(name)) {
                                    SelectValueUtils.selectPools.remove(i);
                                }
                            }
                        }
//                    添加客户类型
                        SelectValueUtils.SelectPool select = new SelectValueUtils.SelectPool();
                        select.setKey(name);
                        select.setValue(bean.getValue());
                        SelectValueUtils.selectPools.add(select);
                    }
                    notifyDataSetChanged();
                }


            }
        });
        return convertView;
    }

//    public void Refresh() {
//        if (string.equals("客户列表")) {
//            SelectValueUtils.list.clear();
//        } else if (string.equals("公海列表")) {
//            SelectValueUtils.selectPools.clear();
//        }
//        notifyDataSetChanged();
//    }

    public class Holder {
        private TextView txt_title;
        private RelativeLayout ll_value;
        private ImageView iv_img;
    }
}
