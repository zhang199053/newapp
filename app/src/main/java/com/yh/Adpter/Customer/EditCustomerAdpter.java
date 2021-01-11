package com.yh.Adpter.Customer;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.Dialog.SelectDateDialog;
import com.yh.Dialog.SelectGeneralDialog;
import com.yh.Entity.AddCustomerEntity;
import com.yh.Entity.CustomerDetailsTwoBean;
import com.yh.Entity.CustomerInput;
import com.yh.R;
import com.yh.Utils.AppToast;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/5
 * Describe ：
 */
public class EditCustomerAdpter extends BaseAdapter {
    private SelectGeneralDialog dialog;
    private SelectDateDialog dateDialog;
    private List<CustomerDetailsTwoBean.Data> datas;
    private Context mContext;
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public EditCustomerAdpter(Context mContext, List<CustomerDetailsTwoBean.Data> data) {
        this.mContext = mContext;
        this.datas = data;
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
        final CustomerDetailsTwoBean.Data bean = datas.get(position);
        convertView = View.inflate(mContext, R.layout.add_customer_item, null);
        convertView.setTag(holder);
        //在Adapter的getView方法中
        holder.tv_name = convertView.findViewById(R.id.tv_name);
        holder.tv_field = convertView.findViewById(R.id.tv_field);
        holder.rv_context = convertView.findViewById(R.id.rv_context);
        holder.iv_zjt = convertView.findViewById(R.id.iv_zjt);
        holder.ll_item = convertView.findViewById(R.id.ll_item);
        holder.v_line = convertView.findViewById(R.id.v_line);

        //        1.条目复用时,若果当前EditText已经添加TextWatcher监听,先将它移除,否则数据错乱
        if (holder.rv_context.getTag() instanceof TextWatcher) {
            holder.rv_context.removeTextChangedListener((TextWatcher) (holder.rv_context.getTag()));
        }
//        字段名标识
        holder.tv_field.setText(bean.getField());
//        字段名
        holder.tv_name.setText(bean.getName());
//        客户信息
        holder.rv_context.setText(bean.getVal());
//存tag值
        holder.rv_context.setTag(position);
        if (bean.getVal() != null) {
            //将光标移至文字末尾
            holder.rv_context.setSelection(bean.getVal().length());
        }
//        客户负责人，客户创建人，客户编号，客户负责人，不显示
        if (bean.getField().equals("owner_role_id") || bean.getField().equals("creator_role_id") || bean.getField().equals("contacts_id") || bean.getField().equals("customer_owner_id")) {
            holder.ll_item.setVisibility(View.GONE);
        } else {
            holder.ll_item.setVisibility(View.VISIBLE);
        }
        if (bean.getForm_type().equals("select")) {
            setEV(holder);
        } else if (bean.getForm_type().equals("datetime")) {
            setEV(holder);
        } else {
            holder.rv_context.setHint("请输入");
            holder.iv_zjt.setVisibility(View.GONE);
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
                String value = (String) holder.tv_field.getTag();
                hashMap.put(position, s.toString());
            }
        });
        // 如果hashMap不为空，就把存储在hashMap中的值设置在editText中
        if (hashMap.get(position) != null) {
            holder.rv_context.setText(hashMap.get(position));
        }
        return convertView;
    }

    private void setEV(Holder holder) {
        holder.rv_context.setHint("请选择");
        holder.iv_zjt.setVisibility(View.VISIBLE);
        holder.rv_context.setCursorVisible(false);
        holder.rv_context.setFocusable(false);
        holder.rv_context.setFocusableInTouchMode(false);
    }

    public class Holder {
        private TextView tv_field;
        private TextView tv_name;
        private EditText rv_context;
        private ImageView iv_zjt;
        private LinearLayout ll_item;
        private View v_line;
    }


}
