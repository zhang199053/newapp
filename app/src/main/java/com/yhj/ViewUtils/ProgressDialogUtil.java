package com.yhj.ViewUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yhj.R;

import java.lang.ref.SoftReference;


/**
 * Created by Huang Mingfei on 2017/9/20 0020.
 */


@SuppressLint("NewApi")
public class ProgressDialogUtil {
    private ProgressDialog progressDialog;
    private TextView title;
    private ProgressBar progressImageView;

    private SoftReference reference;

    private static ProgressDialogUtil single = null;

    public static ProgressDialogUtil getInstance() {
        if (single == null) {
            synchronized (ProgressDialogUtil.class) {
                if (single == null) {
                    single = new ProgressDialogUtil();
                }
            }
        }
        return single;
    }

    /**
     * @param con
     * @方法说明:
     * @方法名称:startLoad
     * @返回值:void
     */
    public void startLoad(Context con) {
        reference = new SoftReference<>(con);
        if (reference == null) {
            return;
        }
        if (progressDialog != null) {
            stopLoad();
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(con, R.style.loading_dialog);
            View proView = DialogUtils.LoadXmlView(con, R.layout.loading);
            progressDialog.dismiss();
            if (!progressDialog.isShowing()) {
                try {
                    progressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            progressDialog.setContentView(proView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            progressImageView = proView.findViewById(R.id.loading_animato);
//            title = proView.findViewById(R.id.loading_title);
//            View loadingGroup = proView.findViewById(R.id.loading_group);
//            if (DialogUtils.isEmpty(message)) {
//                title.setVisibility(View.GONE);
////                loadingGroup.setBackgroundColor(con.getResources()
////                        .getColor(R.color.colorless));
//            } else {
//                title.setText(message);
//                title.setVisibility(View.VISIBLE);
//            }


        }
        // closeLockScreen();
    }

    /**
     * @param message
     * @方法说明:设置加载内容
     * @方法名称:setLoadMessage
     * @返回值:void
     */
    public void setLoadMessage(String message) {
        if (title != null && !DialogUtils.isEmpty(message)) {
            title.setVisibility(View.VISIBLE);
            title.setText(message);
        } else {
            title.setVisibility(View.GONE);
        }
    }

    /**
     * @方法说明:禁止加载条转动的时候去点击系统返回键
     * @方法名称:closeCancelable
     * @返回值:void
     */
    public void closeCancelable() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
        }
    }

    /**
     * @方法说明:允许加载条转动的时候去点击系统返回键
     * @方法名称:openCancelable
     * @返回值:void
     */
    public void openCancelable() {
        if (progressDialog != null) {
            progressDialog.setCancelable(true);
        }
    }

    /**
     * @方法说明：允许点击对话框触摸消失
     * @方法名称：openLockScreen
     * @返回值：void
     */
    public void openLockScreen() {
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }

    /**
     * @方法说明：禁止点击对话框触摸消失
     * @方法名称：closeLockScreen
     * @返回值：void
     */
    public void closeLockScreen() {
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * @方法说明:停止加载
     * @方法名称:stopLoad
     * @返回值:void
     */
    public void stopLoad() {
        if (progressDialog != null && progressDialog.isShowing()) {
            if (reference != null) {
                progressDialog.dismiss();
            }
        }
        if (progressDialog != null) {
            progressDialog = null;
        }
        if (progressImageView != null) {
            if (progressImageView.isActivated()) {
                progressImageView.clearAnimation();
            }
            progressImageView = null;
        }
        if (title != null) {
            title = null;
        }
    }

    public void destoryDialog() {
        stopLoad();
        reference = null;
    }
}