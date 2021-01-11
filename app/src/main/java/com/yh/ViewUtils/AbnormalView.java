package com.yh.ViewUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yh.R;


/**
 * Created by shaowen on 2017/12/28.
 */

public class AbnormalView extends LinearLayout {


    private OnButtonClickListener onBackListen;
    private TypedArray mTypedArray;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 提示图片的id
     */
    private int icon_id;
    /**
     * 是否显示button
     */
    private boolean isshow;
    /**
     * button显示的内容
     */
    private String btn_msg;

    /*总布局*/
    private View rootView;
    /*发现按钮布局*/
    private Button btn_tip;
    /*提示图片布局*/
    private ImageView iv_icon_tip;
    /*提示消息布局*/
    private TextView tv_msg_tip;

    public AbnormalView(Context context) {
        super(context);
        initView(context);
    }


    public AbnormalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.abnormalview);
//        msg = mTypedArray.getString(R.styleable.abnormalview_tipstring);
        isshow = mTypedArray.getBoolean(R.styleable.abnormalview_showbutton, false);
        btn_msg = mTypedArray.getString(R.styleable.abnormalview_tipbutton);
//        drawable = getResources().getDrawable(
//                mTypedArray.getResourceId(R.styleable.abnormalview_tipbutton,
//                        R.mipmap.empty_listen_problem));
        icon_id = mTypedArray.getResourceId(R.styleable.abnormalview_tipicon, R.mipmap.empty_listen_problem);
        mTypedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.abnormal_view, this);

        iv_icon_tip = (ImageView) rootView.findViewById(R.id.iv_icon);
        tv_msg_tip = (TextView) rootView.findViewById(R.id.iv_msg);
        btn_tip = (Button) rootView.findViewById(R.id.btn_discover);
        /*是否显示按钮*/
        if (isshow && !TextUtils.isEmpty(btn_msg)) {
            btn_tip.setVisibility(VISIBLE);
            btn_tip.setText(btn_msg);
            btn_tip.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onBackListen != null) {
                        onBackListen.OnClickListener(view);
                    }
                }
            });
        }

        /*设置显示图片*/
        iv_icon_tip.setImageResource(icon_id);
        /*设置提示消息*/
        tv_msg_tip.setText("暂无数据");

    }

    public void setOnBackListen(OnButtonClickListener onBackListen) {
        this.onBackListen = onBackListen;
    }


    public interface OnButtonClickListener {
        void OnClickListener(View view);
    }

    /**
     * 设置  button 文本
     */
    public void setButtonText(String s) {
        if (TextUtils.isEmpty(s)) {
            btn_tip.setText(s);
        }
    }

    /**
     * 获取  button 文本
     */
    public String getButtonText() {
        String s = btn_tip.getText().toString();
        return TextUtils.isEmpty(s) ? "" : s;
    }

    /**
     * 设置提示文本
     */
    public void setMsText(String s) {
        if (TextUtils.isEmpty(s)) {
            tv_msg_tip.setText(s);
        }
    }


    /**
     * 设置图片
     */
    public void setImageView(int i) {
        if (TextUtils.isEmpty(i + "")) {
            iv_icon_tip.setImageResource(i);
        }
    }
}
