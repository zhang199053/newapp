package com.xsl.ViewUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsl.R;

/**
 * Created by shaowen on 2017/12/28.
 */

public class NavigationBar extends RelativeLayout {
    private OnBackListen onBackListen;
    private TypedArray mTypedArray;
    private String mString;
    private View mInflate;
    private TextView mTvTile;
    private View mViewById;
    private int mBackColor;
    private View mRl;

    public NavigationBar(Context context) {
        super(context);
        initView(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.navigation);
        mString = mTypedArray.getString(R.styleable.navigation_title);
        mBackColor = mTypedArray.getColor(R.styleable.navigation_back, Color.parseColor("#ffffff"));
        mTypedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        mInflate = LayoutInflater.from(context).inflate(R.layout.navigation_title, this);
        mTvTile = mInflate.findViewById(R.id.tv_title);
        mTvTile.setText(mString);

        mRl = mInflate.findViewById(R.id.rl);
        mRl.setBackgroundColor(mBackColor);
        mRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackListen != null) {
                    onBackListen.back();
                }
            }
        });
        mViewById = mInflate.findViewById(R.id.iv_back);
//        mViewById.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onBackListen != null) {
//                    onBackListen.back();
//                }
//            }
//        });
    }

    public void setTile(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTile.setText(title);
        }
    }

    public void setOnBackListen(OnBackListen onBackListen) {

        this.onBackListen = onBackListen;
    }

    public interface OnBackListen {
        void back();
    }
}
