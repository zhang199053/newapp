package com.yh.Model.Crm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yh.App.BaseFragment;
import com.yh.R;

/**
 * Administrator  ：zhouyuru
 * 2020/9/23
 * Describe ：我的主页面
 */
public class CrmFragment extends BaseFragment implements View.OnClickListener {
    private Button btBd;
    private TextView tv_tel;

    @Override
    protected int setContentView() {
        return R.layout.crm_fragment;
    }

    @Override
    protected void init() {
        btBd = rootView.findViewById(R.id.bt_bd);
        btBd.setOnClickListener(this);
        tv_tel = rootView.findViewById(R.id.tv_tel);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bd:
                //拨打电话
                callPhone(tv_tel.getText().toString());
                break;
            default:
                break;

        }
    }

    //拨打电话
    public void callPhone(String phoneNum) {
        //android6版本获取动态权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.CALL_PHONE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        //如果需要手动拨号将Intent.ACTION_CALL改为Intent.ACTION_DIAL（跳转到拨号界面，用户手动点击拨打）
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
