package com.ymz.ViewUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Administrator  ：zhouyuru
 * 2020/11/9
 * Describe ：
 */
public class AllShowGridView extends GridView {
//    public AllShowGridView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public AllShowGridView(Context context) {
//        super(context);
//    }
//
//    public AllShowGridView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }

//
//    @Override
//    public void AllShowGridView(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
    public AllShowGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AllShowGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllShowGridView(Context context) {
        this(context, null);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
//
//    public AllShowGridView(Context context) {
//        super(context);
//    }
//
//    public AllShowGridView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
