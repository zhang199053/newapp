package com.cyb.Adpter.Customer;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyb.Dialog.SelectDateDialog;
import com.cyb.Dialog.SelectGeneralDialog;
import com.cyb.Entity.AddCustomerEntity;
import com.cyb.Entity.CustomerInput;
import com.cyb.R;

import java.util.HashMap;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/2
 * Describe ：
 */
public class AddCustomerAdpter extends BaseAdapter {
    private SelectGeneralDialog dialog;
    private SelectDateDialog dateDialog;
    private List<AddCustomerEntity.Customer_list> datas;
    private Context mContext;
    public  HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public AddCustomerAdpter(Context context, List<AddCustomerEntity.Customer_list> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        final AddCustomerEntity.Customer_list bean = datas.get(position);
        convertView = View.inflate(mContext, R.layout.add_customer_item, null);
        convertView.setTag(holder);
        holder.tv_name = convertView.findViewById(R.id.tv_name);
        holder.tv_field = convertView.findViewById(R.id.tv_field);
        holder.rv_context = convertView.findViewById(R.id.rv_context);
        holder.iv_zjt = convertView.findViewById(R.id.iv_zjt);
        holder.is_validate = convertView.findViewById(R.id.is_validate);
//在Adapter的getView方法中
        if (holder.rv_context.getTag() instanceof TextWatcher) {
            holder.rv_context.removeTextChangedListener((TextWatcher) (holder.rv_context.getTag()));
        }
//        字段名标识
        holder.tv_field.setText(bean.getField());
//        记录是否验证
        holder.is_validate.setText(bean.getIs_validate());
//        字段名
        holder.tv_name.setText(bean.getName());
        //存tag值
        holder.rv_context.setTag(position);
//        输入框
        if (bean.getSetting().size() == 0 && !bean.getForm_type().equals("datetime")) {
//            字段名hint提示
            holder.rv_context.setHint(bean.getInput_tips());
            holder.iv_zjt.setVisibility(View.GONE);
            if (bean.getForm_type().equals("text")) {
                holder.rv_context.setInputType(InputType.TYPE_CLASS_TEXT);
            }
//            //            设置最大长度
//
//            if (!TextUtils.isEmpty(bean.getMax_length())) {
//                int lent = Integer.parseInt(bean.getMax_length());
//                holder.rv_context.setMaxLines(lent);
//            }

//            选择框
        } else if (bean.getForm_type().equals("select") && bean.getSetting().size() > 0) {
            setEV(holder, bean);

            //时间选择器
        } else if (bean.getForm_type().equals("datetime")) {
            setEV(holder, bean);
        }
        holder.rv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getForm_type().equals("select") && bean.getSetting().size() > 0 || bean.getForm_type().equals("datetime")) {
                    if (bean.getField().equals("crm_geppdq")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();
                    }
                    if (bean.getField().equals("crm_ayccve")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();

                    }
                    if (bean.getField().equals("industry")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();

                    }
                    if (bean.getField().equals("customer_status")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();
                    }
                    if (bean.getField().equals("origin")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();
                    }
                    if (bean.getField().equals("grade")) {
                        dialog = new SelectGeneralDialog(mContext, bean.getSetting(), holder.rv_context, "请选择" + bean.getName());
                        dialog.show();
                    }
                    if (bean.getField().equals("nextstep_time")) {
                        dateDialog = new SelectDateDialog(mContext, holder.rv_context);
                        dateDialog.show();
                    }
                }

            }
        });
        holder.rv_context.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(position, s.toString());
            }
        });
        // 如果hashMap不为空，就把存储在hashMap中的值设置在editText中
        if (hashMap.get(position) != null) {
            holder.rv_context.setText(hashMap.get(position));
        }
        return convertView;
    }

    private void setEV(Holder holder, AddCustomerEntity.Customer_list bean) {
        if (TextUtils.isEmpty(bean.getInput_tips())) {
            holder.rv_context.setHint("请选择");
        } else {
            holder.rv_context.setHint(bean.getInput_tips());
        }
        holder.iv_zjt.setVisibility(View.VISIBLE);
//可点击，不可编辑
        holder.rv_context.setCursorVisible(false);
        holder.rv_context.setFocusable(false);
        holder.rv_context.setFocusableInTouchMode(false);
    }

    public class Holder {
        private TextView tv_field;
        private TextView tv_name;
        private EditText rv_context;
        private ImageView iv_zjt;
        private TextView is_validate;
    }

}
