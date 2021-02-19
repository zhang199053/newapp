package com.xyk.Model.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xyk.App.App;
import com.xyk.App.BaseActivity;
import com.xyk.Dialog.CurrencyDialog;
import com.xyk.Entity.IpBean;
import com.xyk.Entity.LoginEntity;
import com.xyk.Entity.SystemBalanceEntity;
import com.xyk.Main.MainActivity;
import com.xyk.Model.My.MyFragment;
import com.xyk.R;
import com.xyk.Utils.ActivityMgr;
import com.xyk.Utils.AppToast;
import com.xyk.Utils.HttpUtils.BaseCallback;
import com.xyk.Utils.HttpUtils.HttpClient;
import com.xyk.Utils.MD5Utils;
import com.xyk.Utils.SharedPrefUtil;
import com.xyk.Utils.UrlUtils.Url;
import com.xyk.ViewUtils.ProgressDialogUtil;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xyk.Utils.UrlUtils.Url.DOMAIN_AppIp;
import static com.xyk.Utils.UrlUtils.Url.DOMAIN_getSystemBalance;


/**
 * Des：登录Activity
 *
 * @author zhouyuru
 * @date 2018/4/3
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private LinearLayout ll_ip;
    private LinearLayout ll_zh;
    private LinearLayout ll_mm;
    /**
     * 账号编辑框
     */
    private EditText loginEtZh;
    /**
     * 验证码框
     */
    private EditText loginEtCode;
    /**
     * 服务器地址
     */
    private AutoCompleteTextView acTextView;
    private ArrayAdapter<String> adapter;
    private ImageView bt_login;
    /**
     * 账号
     */
    private String mZh;
    /**
     * 密码
     */
    private String mPwd;
    //ip
    private String ip;
    //    是否记录密码
    private CheckBox mCheckBox_01;
    private List<IpBean.IP> ips = new ArrayList<IpBean.IP>();
    private List<String> ipz = new ArrayList<String>();


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void getAllIP() {
        HttpClient.getInstance().post(mContext, DOMAIN_AppIp, null, new BaseCallback<IpBean>(IpBean.class) {
            @Override
            public void onSuccess(IpBean result) {
                if (result.getInfo().equals("success")) {
                    ips.addAll(result.getData());
                    initIP();
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
            }
        });
    }

    private void initIP() {
        if (ips != null && ips.size() > 0) {
            for (int i = 0; i < ips.size(); i++) {
                if (!TextUtils.isEmpty(ips.get(i).getDomain_name())) {
                    ipz.add(ips.get(i).getDomain_name());
                }
            }
            //vpmuu1
            //ipz.clear();
            //ipz.add("www.2cr8o0.cn");
            //ipz.add("www.mashangyingxiao.cn");

            if (ipz != null && ipz.size() > 0) {
                adapter = new ArrayAdapter<String>(mContext, R.layout.login_ip_item, ipz);
//        将适配器与当前控件绑定
                acTextView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void initView() {
        ll_zh = findViewById(R.id.ll_zh);
        ll_zh.setOnClickListener(this);
        ll_ip = findViewById(R.id.ll_ip);
        ll_ip.setOnClickListener(this);
        ll_mm = findViewById(R.id.ll_mm);
        ll_mm.setOnClickListener(this);
        //初始化控件，返回类型view强制转换成AutoCompleteTextView
        acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        //设置弹出列表高度总高度
        //        acTextView.setDropDownHeight (100);
        //设置弹出列表与输入框直接距离
        acTextView.setDropDownVerticalOffset(0);
        //设置下拉框颜色
//        acTextView.setDropDownBackgroundResource(R.color.colorAccent);
        //添加适配器，并初始化数据源，用来匹配文本框输入的内容
        acTextView.setOnFocusChangeListener(this);
        acTextView.setOnClickListener(this);
        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        loginEtZh = findViewById(R.id.login_et_zh);
        loginEtCode = findViewById(R.id.login_et_pwd);
        mCheckBox_01 = findViewById(R.id.mCheckBox_01);
        getAllIP();
        ShowLogin();
    }

    private void ShowLogin() {
        ip = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Loing_Ip);
        mZh = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Login_Name);
        mPwd = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Login_Psd);
        boolean Jz = SharedPrefUtil.getInstance().getBool(SharedPrefUtil.Login_Jz);
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(mZh) && !TextUtils.isEmpty(mPwd)) {
            acTextView.setVisibility(View.VISIBLE);
            loginEtZh.setVisibility(View.VISIBLE);
            loginEtCode.setVisibility(View.VISIBLE);
            acTextView.setText(ip);
            loginEtZh.setText(mZh);
            loginEtCode.setText(mPwd);
            if (Jz) {
                mCheckBox_01.setChecked(true);
            } else {
                mCheckBox_01.setChecked(false);

            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        AutoCompleteTextView view = (AutoCompleteTextView) v;
        if (hasFocus) {
            if (view != null) {
                view.showDropDown();
            }
        }
    }

    @Override
    public void onClick(View v) {
        //View点击事件使用
        switch (v.getId()) {
            case R.id.ll_ip:
                acTextView.requestFocus();
                break;
            case R.id.ll_zh:
                loginEtZh.requestFocus();
                break;
            case R.id.ll_mm:
                loginEtCode.requestFocus();
                break;
            case R.id.autoCompleteTextView1:
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                view.showDropDown();
                break;
            case R.id.bt_login:
                if (check()) {
                    if (mCheckBox_01.isChecked()) {
                        SharedPrefUtil.getInstance().setLoginSys(ip, mZh, mPwd, true);
                    } else {
                        SharedPrefUtil.getInstance().setLoginSys("", "", "", false);
                    }
                    Login(mZh, mPwd);
                }
                break;
            default:
                break;
        }
    }

    private void Login(String mZh, String mPwd) {
        if (!TextUtils.isEmpty(mZh) && !TextUtils.isEmpty(mPwd)) {
            ProgressDialogUtil.getInstance().startLoad(this);
            Map<String, Object> parame = new HashMap<>();
            parame.put("name", mZh);
            parame.put("password", MD5Utils.MD5(mPwd));
            Url.setIp(ip);
            String url = Url.DOMAIN_Login;
            HttpClient.getInstance().post(mContext, Url.DOMAIN_Login, parame, new BaseCallback<LoginEntity>(LoginEntity.class) {
                @Override
                public void onSuccess(LoginEntity result) {
                    if (result.getInfo().equals("success")) {
                        //登录成功保存持久化用户数据
                        LoginEntity data = result;
                        SharedPrefUtil.getInstance().setUserInfor(data.getImg(), data.getSession_id(), data.getToken(), data.getAdmin() + "", data.getRole_id(), data.getName(), data.getSex(), data.getTelephone(), data.getDepartment_id(), data.getDepartment_name(), data.getRole_name(), data.getSystem_name(),data.getIs_app_secret());
                        //保存是否登录过
                        SharedPrefUtil.getInstance().putBool(SharedPrefUtil.IS_LOGIN, true);
//                        获取账户余额
//                        getSystemBalance();
                        if (isTaskRoot()) {
                            //        是最后一个存活的页面，跳转到主页面
                            startActivity(new Intent(mContext, MainActivity.class));
                            //结束前面所有的activity
                            ActivityMgr.getInstance().clearActivity();
                        } else {
                            //前面还有存活的页面，就关闭登陆页面
                            LoginActivity.this.finish();
                        }
                    } else {
                        AppToast.showToast(result.getInfo());
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

    private void getSystemBalance() {
        if (!TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_getSystemBalance, parame, new BaseCallback<SystemBalanceEntity>(SystemBalanceEntity.class) {
                @Override
                public void onSuccess(SystemBalanceEntity result) {
//                设置花费余额
                    String yue = result.getData().getRemain_minutes();
                    SharedPrefUtil.getInstance().putString(SharedPrefUtil.User_SystemBalance, result.getData().getRemain_minutes());
//                    设置显示余额
                    MyFragment.tv_ye.setText(yue + "分钟");
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

    /**
     * 校验账号密码是否符合
     */
    public boolean check() {
        ip = acTextView.getText().toString().trim();
        mZh = loginEtZh.getText().toString().trim();
        mPwd = loginEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(ip)) {
            AppToast.showToast("服务器不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mZh)) {
            AppToast.showToast("账号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mPwd)) {
            AppToast.showToast("密码不能为空");
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
    }

}

