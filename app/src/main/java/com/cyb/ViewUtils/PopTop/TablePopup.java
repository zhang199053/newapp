package com.cyb.ViewUtils.PopTop;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cyb.Adpter.Customer.TableAdpter;
import com.cyb.Entity.CustomerBean;
import com.cyb.Model.Customer.CustomerListFragment;
import com.cyb.Model.CustomerPool.CustomerPoolListFragment;
import com.cyb.R;
import com.cyb.Utils.SelectValueUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cyb.ViewUtils.numToPxUtils.dip2px;

/**
 * Administrator  ：zhouyuru
 * 2020/10/23
 * Describe ：筛选框页面
 */
public class TablePopup extends PopupWindow {
    private Context mContext;
    private ImageView iv_close;

    // 筛选框list
    private RecyclerView mListView;

    //筛选框数据
    private List<CustomerBean.Fields_list> inUseFields = new ArrayList<CustomerBean.Fields_list>();
    // 筛选框适配器
    private TableAdpter tableAdpter;
    //    确定；取消
    private LinearLayout ll_qd, ll_qx;
    //    当前fragmentName
    private String titles;

    //初始化
    public TablePopup(Context context, List<CustomerBean.Fields_list> fields_list, List<CustomerBean.Scene_list> scene_list, String title) {
        this.mContext = context;
        //筛选框数据融合 inUseFields
        if (scene_list != null && scene_list.size() > 0) {
            CustomerBean.Fields_list fieldsList = new CustomerBean.Fields_list();
            fieldsList.setName("客户类型");
            fieldsList.setField("by");
            List<CustomerBean.SelectValues> selectValuesList = new ArrayList<CustomerBean.SelectValues>();
            for (CustomerBean.Scene_list sceneList : scene_list) {
                CustomerBean.SelectValues selectValues = new CustomerBean.SelectValues();
                selectValues.setKey(sceneList.getBy());
                selectValues.setValue(sceneList.getCut_name());
                selectValuesList.add(selectValues);
            }
            fieldsList.setSetting(selectValuesList);
            inUseFields.add(fieldsList);
        }
        inUseFields.addAll(fields_list);
        this.titles = title;
        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);
        // 设置弹窗的宽度和高度
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹窗的布局界面
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.table_popup, null));
        mListView = (RecyclerView) getContentView().findViewById(R.id.gv_list);
//        设置线性布局
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
//        适配器
        tableAdpter = new TableAdpter(mContext, R.layout.layout_table_item_pop, inUseFields, titles);
//        绑定适配器
        mListView.setAdapter(tableAdpter);
//        关闭icon'
        iv_close = (ImageView) getContentView().findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        确定
        ll_qd = (LinearLayout) getContentView().findViewById(R.id.ll_qd);
        ll_qd.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         dismiss();
                                         if (titles.equals("客户列表")) {
                                             CustomerListFragment.initData(1);

                                         } else if (titles.equals("公海列表")) {
                                             CustomerPoolListFragment.initData(1);
                                         }
                                     }
                                 }
        );
//取消
        ll_qx = (LinearLayout) getContentView().findViewById(R.id.ll_qx);
        ll_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableAdpter.Refresh();
            }
        });

    }


}
