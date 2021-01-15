package com.ymz.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.ymz.R;

/**
 *
 * @author Administrator
 */
public class MyDialog {

    public static Dialog MyDialogShow(Context context, int layout, Float liagndu) {
        LayoutInflater factory = LayoutInflater.from(context);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();

        lp.alpha = liagndu;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.colorless);
        window.setWindowAnimations(R.style.qiandao);

        return dialog;
    }



    public static Dialog SignDialogShow(Context context, int layout, Float liagndu) {
        LayoutInflater factory = LayoutInflater.from(context);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();

        lp.alpha = liagndu;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.colorless);
        window.setWindowAnimations(R.style.qiandao);

        return dialog;
    }
}
