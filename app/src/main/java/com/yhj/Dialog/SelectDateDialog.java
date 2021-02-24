package com.yhj.Dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yhj.App.BaseDialog;
import com.yhj.R;

import java.util.Calendar;

/**
 * Administrator  ：zhouyuru
 * 2020/10/9
 * Describe ：选择日期弹日期
 */
public class SelectDateDialog extends BaseDialog {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private TextView text;
    private TextView bt_bd;
    private int y;
    private int m;
    private int d;
    private int h;
    private int f;
    private String Stringy;
    private String Stringm;
    private String Stringd;
    private String Stringh;
    private String Stringf;

    public SelectDateDialog(Context context, TextView textView) {
        super(context);
        this.text = textView;
    }

    @Override
    protected int getContentView() {
        return R.layout.select_data_dialog;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        datePicker = (DatePicker) findViewById(R.id.datepicker);
        timePicker = (TimePicker) findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        bt_bd = findViewById(R.id.bt_bd);
        bt_bd.setOnClickListener(this);
//        日历控件，获取当前时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
    }

    @Override
    protected void SetPosition() {
        setCanceledOnTouchOutside(true);
        //向上弹出
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(null);
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        lp.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(lp);
    }

    //获取表盘时间
    @TargetApi(Build.VERSION_CODES.M)
    public String getData() {
        y = datePicker.getYear();
        m = datePicker.getMonth() + 1;
        if (m < 10) {
            Stringm = "0" + m;
        } else {
            Stringm = m + "";
        }
        d = datePicker.getDayOfMonth();
        if (d < 10) {
            Stringd = "0" + d;
        } else {
            Stringd = d + "";
        }
        h = timePicker.getHour();
        if (h < 10) {
            Stringh = "0" + h;
        } else {
            Stringh = h + "";
        }
        f = timePicker.getMinute();
        if (f < 10) {
            Stringf = "0" + f;
        } else {
            Stringf = f + "";
        }
        String data = y + "-" + Stringm + "-" + Stringd + " " + Stringh + ":" + Stringf;
        return data;
    }

    //设置时间
    @TargetApi(Build.VERSION_CODES.M)
    public void setData(TextView textView) {
        textView.setText(getData());
        textView.setTextColor(Color.parseColor("#313131"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bd:
                setData(text);
                dismiss();
                break;
            default:
                break;

        }
    }
}
