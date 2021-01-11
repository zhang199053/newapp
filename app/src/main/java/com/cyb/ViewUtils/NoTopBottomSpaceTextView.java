package com.cyb.ViewUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

import com.cyb.R;

/**
 * Administrator  ：zhouyuru
 * 2020/10/23
 * Describe ：
 */


public class NoTopBottomSpaceTextView extends AppCompatTextView {
    public NoTopBottomSpaceTextView(Context context) {

        super(context);

        init();

    }

    public NoTopBottomSpaceTextView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        init();

    }

    public NoTopBottomSpaceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {

        setGravity(getGravity() | Gravity.CENTER_VERTICAL);

        setIncludeFontPadding(false);

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Paint.FontMetrics fm = getPaint().getFontMetrics();

        if (fm != null) {

            int padding = (int) Math.abs(fm.bottom - fm.descent);

            int width = getMeasuredWidth();

            int height = getMeasuredHeight();

            if (getText().toString().contains("g")

                    || getText().toString().contains("y")

                    || getText().toString().contains("p")) {

                setMeasuredDimension(width, height - padding);

                if (getScrollY() != (int) (fm.ascent - fm.top)) {

                    setScrollY((int) (fm.ascent - fm.top));

                }

                setScrollY((int) Math.abs(fm.top - fm.ascent));

            } else {

                setMeasuredDimension(width, height - padding - (int) (getTextSize() * 0.1));

                setScrollY((int) Math.abs(fm.top - fm.ascent) - (int) (getTextSize() * 0.1));

            }

        }

    }

}


