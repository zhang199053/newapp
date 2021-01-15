package com.xsl.Model.My;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.xsl.App.App;
import com.xsl.App.BaseFragment;
import com.xsl.Dialog.CurrencyDialog;
import com.xsl.Entity.LoginEntity;
import com.xsl.Entity.SystemBalanceEntity;
import com.xsl.Entity.UserEntity;
import com.xsl.Main.MainActivity;
import com.xsl.Model.Login.LoginActivity;
import com.xsl.Model.My.About.AboutusActivity;
import com.xsl.R;
import com.xsl.Utils.ActivityMgr;
import com.xsl.Utils.AppToast;
import com.xsl.Utils.HttpUtils.BaseCallback;
import com.xsl.Utils.HttpUtils.HttpClient;
import com.xsl.Utils.MD5Utils;
import com.xsl.Utils.SelectValueUtils;
import com.xsl.Utils.SharedPrefUtil;
import com.xsl.Utils.UrlUtils.Url;
import com.xsl.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.xsl.Utils.UrlUtils.Url.DOMAIN_Logout;
import static com.xsl.Utils.UrlUtils.Url.DOMAIN_User;
import static com.xsl.Utils.UrlUtils.Url.DOMAIN_getSystemBalance;


/**
 * Administrator  ：zhouyuru
 * 2020/9/23
 * Describe ：我的主页面
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    //    头部信息栏
    private LinearLayout ll_head_information;
    //    通信配置
    private LinearLayout ll_sys_set;

    //    修改密码
    private LinearLayout ll_updata_psd;
    //拨打方式设置
    private LinearLayout ll_set_phone_psd;
    //    退出登陆
    private ImageView ll_exit;
    //    关于我们
    private LinearLayout ll_gywm;
    private LinearLayout ll_statisticl;
    //头像
    private ImageView minefragment_iv_login;
    private TextView tv_name, tv_bm, tv_zw, tv_phone;
    private UserEntity.UsersData data;
    int state;
    String imgUrl = "";
    public static TextView tv_ye;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.my_fragment;
    }

    @Override
    protected void init() {
        ll_head_information = rootView.findViewById(R.id.ll_head_information);
        ll_head_information.setOnClickListener(this);
        ll_sys_set = rootView.findViewById(R.id.ll_sys_set);
        ll_sys_set.setOnClickListener(this);
        ll_updata_psd = rootView.findViewById(R.id.ll_updata_psd);
        ll_updata_psd.setOnClickListener(this);
        ll_exit = rootView.findViewById(R.id.ll_exit);
        ll_exit.setOnClickListener(this);
        minefragment_iv_login = rootView.findViewById(R.id.minefragment_iv_login);
        minefragment_iv_login.setOnClickListener(this);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_bm = rootView.findViewById(R.id.tv_bm);
        tv_zw = rootView.findViewById(R.id.tv_zw);
        tv_phone = rootView.findViewById(R.id.tv_phone);
        ll_gywm = rootView.findViewById(R.id.ll_gywm);
        ll_gywm.setOnClickListener(this);
        ll_set_phone_psd = rootView.findViewById(R.id.ll_set_phone_psd);
        ll_set_phone_psd.setOnClickListener(this);
        tv_ye = rootView.findViewById(R.id.tv_ye);
        tv_ye.setOnClickListener(this);
        ll_statisticl = rootView.findViewById(R.id.ll_statistical);
        ll_statisticl.setOnClickListener(this);

        ShowView();
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minefragment_iv_login:
                //查看头像大图
                if (!TextUtils.isEmpty(Url.ip + SharedPrefUtil.getInstance().getString(SharedPrefUtil.USER_img, ""))) {
                    PhotoDetailActivity.startAction(mContext, imgUrl);
                } else {
                    AppToast.showToast("暂无数据");
                }
                break;
            case R.id.ll_head_information:
                //跳转个人信息详情
                App.startActivity(mContext, PersonalDetailsActivity.class);
                break;
            case R.id.ll_sys_set:
                //通信配置
                App.startActivity(mContext, SysSetActivity.class);
                break;
            case R.id.ll_updata_psd:
                //修改密码
                App.startActivity(mContext, UpdatePsdActivity.class);
                break;
            case R.id.ll_exit:
                //退出登陆
                final CurrencyDialog dialog = new CurrencyDialog(getContext(), "是否退出登录？", "否", "是");
                dialog.show();
                dialog.tv_qd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_gywm:
//                关于我们
                mContext.startActivity(new Intent(mContext, AboutusActivity.class));
                break;
            case R.id.ll_set_phone_psd:
//                设置拨打电话
                mContext.startActivity(new Intent(mContext, SetCallActivity.class));
                break;

            case R.id.ll_statistical:
                startActivity(new Intent(mContext,StatisticalActivity.class));

                break;
            default:
                break;

        }
    }

    private void logout() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            ProgressDialogUtil.getInstance().startLoad(mContext);
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_Logout, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        if (json.getString("info").equals("退出成功！")) {
                            SharedPrefUtil.getInstance().clearUserInfor();
                            SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_SystemBalance, "");
                            ActivityMgr.getInstance().removeAll();
                            SelectValueUtils.list.clear();
                            SelectValueUtils.selectPools.clear();
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            AppToast.showToast("退出登陆");
                        } else {
                            AppToast.showToast(json.getString("info"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
                    AppToast.showToast(msg);

                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    ProgressDialogUtil.getInstance().stopLoad();
                }
            });
        }
    }


    private void ShowView() {
//        加载用户数据
        if (!TextUtils.isEmpty(App.getToken())) {
            imgUrl = "http://" + Url.ip + SharedPrefUtil.getInstance().getString(SharedPrefUtil.USER_img, "");
            tv_name.setText(SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_name, ""));
            tv_bm.setText(SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_department_name, ""));
            tv_zw.setText(SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_role_name, ""));
            tv_phone.setText(SharedPrefUtil.getInstance().getString(SharedPrefUtil.USER_telephone, ""));
            //成功后显示头像
            Glide.with(this)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.mine_qst)
                    .error(R.mipmap.mine_qst)
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(minefragment_iv_login);
        }
//        getSystemBalance();
    }


    //加载系统花费余额
    private void getSystemBalance() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_getSystemBalance, parame, new BaseCallback<SystemBalanceEntity>(SystemBalanceEntity.class) {
                @Override
                public void onSuccess(SystemBalanceEntity result) {
//                设置花费余额
                    String yue = result.getData().getRemain_minutes();
                    if (!TextUtils.isEmpty(yue)) {
                        SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_SystemBalance, result.getData().getRemain_minutes());
//                    设置显示余额
                        if (MyFragment.tv_ye != null) {
                            MyFragment.tv_ye.setText(yue + "分钟");
                        }
//                        提示余额不足
                        Integer integer = Integer.parseInt(yue);
                        if (integer < 0) {
                            final CurrencyDialog dialog = new CurrencyDialog(mContext, "余额不足，请尽快充值，以免影响正常使用！", "确定", "");
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onError(String msg) {
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(org.xutils.common.Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                }
            });
        }
    }

    //更新做题记录
    @Override
    public void onResume() {
        super.onResume();
        ShowView();

    }
}
