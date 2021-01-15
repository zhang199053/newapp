package com.ymz.App;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.ymz.Main.MainActivity;
import com.ymz.Model.Login.LoginActivity;
import com.ymz.Utils.MyCatchException;
import com.ymz.Utils.SharedPrefUtil;
import org.xutils.x;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Des：初始化类
 *
 * @author zhouyuru
 * @date 2018/4/2
 */
public class App extends Application {
    //是否提示余额不够！
    public static boolean isLogin = false;
    private static App instance;
    public static Context applicationContext;
    /**
     * logcat   是否开启日志
     */
    public static boolean logcat = true;

    /**
     * 获取全局app实例
     *
     * @return
     */
    public static App getInstance() {
        return instance;
    }

    /**
     * 获取全局app实例
     *
     * @return
     */
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    /**
     * 突破应用方法数65535的一个限制（继承MultiDexApplication且重写attachBaseContext加入 MultiDex.install(this)）
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        MyCatchException mException= MyCatchException.getInstance();
        mException.init(getApplicationContext());  //注册

        //xutils初始化
        x.Ext.init(this);
        //xutils是否开启日志
        x.Ext.setDebug(true);
        firstRun();
//        开启bugly
//        增加上报进程控制
        Bugly();
        MultiDex.install(this);
    }

    private void Bugly() {
// 获取当前包名
        String packageName = applicationContext.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(applicationContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "0d319201ed", false);
    }

    //是否登陆,是否跳转登陆页面
    private void firstRun() {
        String token = App.getToken();
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
        }
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param mContext
     * @param intent
     */
    public static void startActivity(Context mContext, Intent intent) {
        if (!TextUtils.isEmpty(App.getToken())) {
            mContext.startActivity(intent);
        } else {
            intent.setClass(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }

    /**
     * 区分 游客模式时
     *
     * @param mContext
     * @param mClass
     */
    public static void startActivity(Context mContext, Class<?> mClass) {
        if (!TextUtils.isEmpty(App.getToken())) {
            mContext.startActivity(new Intent(mContext, mClass));
        } else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    /**
     * 获取当前用户手机号
     *
     * @return
     */
    public static String getUserPhone() {
        return SharedPrefUtil.getInstance().getString(SharedPrefUtil.USER_telephone);
    }

    /**
     * 获取当前用户TOKEN
     *
     * @return
     */
    public static String getToken() {
        return SharedPrefUtil.getInstance().getString(SharedPrefUtil.USER_token);
    }

    /**
     * 获取当前用户角色id
     *
     * @return
     */
    public static String getRoleID() {
        return SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_role_id);
    }

    /**
     * 是否登录过
     *
     * @return
     */
    public static boolean islogin() {
        return SharedPrefUtil.getInstance().getBool(SharedPrefUtil.IS_LOGIN, false);
    }

}
