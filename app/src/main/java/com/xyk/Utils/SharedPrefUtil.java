package com.xyk.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xyk.App.App;
import com.xyk.BuildConfig;
import com.xyk.Entity.LoginEntity;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Huang Mingfei on 2017/10/11 0011.
 */

public class SharedPrefUtil {
    /**
     * 用户头像
     */
    public static final String USER_img = BuildConfig.APPLICATION_ID + "img";

    /**
     * 用户名
     */
    public static final String USER_session_id = BuildConfig.APPLICATION_ID + "session_id";

    /**
     * 用户昵称
     */
    public static final String USER_token = BuildConfig.APPLICATION_ID + "token";

    public static final String USER_admin = BuildConfig.APPLICATION_ID + "admin";

    /**
     * 用户 头像
     */
    public static final String User_role_id = BuildConfig.APPLICATION_ID + "role_id";
    //    手机标识唯一标识

    public static final String User_role_name = BuildConfig.APPLICATION_ID + "role_name";
    /**
     * 用户真实名
     */
    public static final String User_name = BuildConfig.APPLICATION_ID + "name";
    /**
     * 用户手机号
     */
    public static final String USER_sex = BuildConfig.APPLICATION_ID + "sex";
    /**
     * 用户 balance
     */
    public static final String USER_telephone = BuildConfig.APPLICATION_ID + "telephone";
    /**
     * 用户 token
     */
    public static final String USER_department_id = BuildConfig.APPLICATION_ID + "department_id";

    /**
     * 是否认证
     */
    public static final String User_department_name = BuildConfig.APPLICATION_ID + "department_name";
    /**
     * 保存登陆名字
     */
    public static final String User_system_name = BuildConfig.APPLICATION_ID + "system_name";
    /**
     * 脱敏状态
     */
    public static final String User_pho_secret = BuildConfig.APPLICATION_ID + "app_secret";
    /**
     * 保存登陆ip
     */
    public static final String Loing_Ip = BuildConfig.APPLICATION_ID + "loginIp";
    /**
     * 是否登录过
     */
    public static final String IS_LOGIN = BuildConfig.APPLICATION_ID + "islogin";
    // 保存登陆密码

    public static final String Login_Psd = BuildConfig.APPLICATION_ID + "loginPsd";
    public static final String Login_Name = BuildConfig.APPLICATION_ID + "loginName";
    //    是否记住密码
    public static final String Login_Jz = BuildConfig.APPLICATION_ID + "login_jz";

    //    拨打方式
    public static final String Login_Db_state = BuildConfig.APPLICATION_ID + "user_db_state";

    //    本机号码
    public static final String User_Host_phone = BuildConfig.APPLICATION_ID + "user_host_phone";

    //    设置转播方式
    public static final String User_Zb_State = BuildConfig.APPLICATION_ID + "user_zb_state";

    //    设置系统余额
    public static final String User_SystemBalance = BuildConfig.APPLICATION_ID + "user_system_balance";


    public static String getUser_Zb_State() {
        return User_Zb_State;
    }

    public static String getUser_Host_phone() {
        return User_Host_phone;
    }

    public static String getLogin_Db_state() {
        return Login_Db_state;
    }

    public static String getLogin_Jz() {
        return Login_Jz;
    }

    public static String getUser_SystemBalance() {
        return User_SystemBalance;
    }

    private static SharedPrefUtil instance;
    private static SharedPreferences sharedPrefs;
    private Gson gson;

    private SharedPrefUtil(Context context) {
        sharedPrefs = context.getSharedPreferences("EXAMW", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static SharedPrefUtil getInstance() {
        if (sharedPrefs == null) {
            instance = new SharedPrefUtil(App.getAppContext());
        }
        return instance;
    }

    public String getString(String key, String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    public String getString(String key) {
        return sharedPrefs.getString(key, "");
    }

    public boolean getBool(String key) {
        return sharedPrefs.getBoolean(key, false);
    }

    public boolean getBool(String key, boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    public Float getFloat(String key) {
        return sharedPrefs.getFloat(key, 0);
    }

    public Float getFloat(String key, float defaultValue) {
        return sharedPrefs.getFloat(key, defaultValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void putBool(String key, boolean value) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPrefs.getLong(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 记录登陆信息
     */
    public void setLoginSys(String loing_Ip, String login_name, String login_psd, boolean isJz) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(Loing_Ip, loing_Ip);
        edit.putString(Login_Name, login_name);
        edit.putString(Login_Psd, login_psd);
        edit.putBoolean(Login_Jz, isJz);
        edit.commit();
    }

    //    "img": "./Uploads/head/thumb_5caf434b67fc59813.png",
//            "session_id": "fcd9qf6qt7jjjs09ohpr242ui1",
//            "token": "9dabe2e6fdb43fa8ef33ee1ac02e0457",
//            "admin": 1,
//            "role_id": "1",
//            "name": "李浩",
//            "sex": "1",
//            "telephone": "13656235523",
//            "department_id": "1",
//            "department_name": "经理",
//            "role_name": "总管理员",
//            "system_name": "crm",
//            "info": "success",
//            "status": 1
    public void setUserInfor(String img, String session_id, String token, String admin, String role_id, String name, String sex, String telephone, String department_id, String department_name, String role_name, String system_name,String pho_secret) {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(USER_img, img);
        edit.putString(USER_session_id, session_id);
        edit.putString(USER_token, token);
        edit.putString(USER_admin, admin);
        edit.putString(User_role_id, role_id);
        edit.putString(User_name, name);
        edit.putString(USER_sex, sex);
        edit.putString(USER_telephone, telephone);
        edit.putString(USER_department_id, department_id);
        edit.putString(User_department_name, department_name);
        edit.putString(User_role_name, role_name);
        edit.putString(User_system_name, system_name);
        edit.putString(User_pho_secret, pho_secret);
        edit.commit();
    }


    /**
     * 清除登录信息
     */
    public void clearUserInfor() {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.remove(USER_img);
        edit.remove(USER_session_id);
        edit.remove(USER_token);
        edit.remove(USER_admin);
        edit.remove(User_role_id);
        edit.remove(User_name);
        edit.remove(USER_sex);
        edit.remove(USER_telephone);
        edit.remove(USER_department_id);
        edit.remove(User_department_name);
        edit.remove(User_role_name);
        edit.remove(User_system_name);
        edit.remove(IS_LOGIN);
        edit.commit();
    }

    public static String getUSER_img() {
        return USER_img;
    }

    public static String getUSER_session_id() {
        return USER_session_id;
    }

    public static String getUSER_token() {
        return USER_token;
    }

    public static String getUSER_admin() {
        return USER_admin;
    }

    public static String getUser_role_id() {
        return User_role_id;
    }

    public static String getUser_role_name() {
        return User_role_name;
    }

    public static String getUser_name() {
        return User_name;
    }

    public static String getUSER_sex() {
        return USER_sex;
    }

    public static String getUSER_telephone() {
        return USER_telephone;
    }

    public static String getUSER_department_id() {
        return USER_department_id;
    }

    public static String getUser_department_name() {
        return User_department_name;
    }

    public static String getUser_system_name() {
        return User_system_name;
    }

    public static String getLoing_Ip() {
        return Loing_Ip;
    }

    public static String getIsLogin() {
        return IS_LOGIN;
    }

    public static String getLogin_Psd() {
        return Login_Psd;
    }

    public static String getLogin_Name() {
        return Login_Name;
    }

    /**
     * // 用于写入用户信息
     *
     * @param
     * @param cls
     */
    public void write(String key, Object cls) {
        //转换成json数据，再保存
        String strJson = gson.toJson(cls);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, strJson);
        editor.commit();
    }

    public <T> T read(String key, Class<T> cls) {

        String strJson = sharedPrefs.getString(key, null);
        if (strJson == null) {

            return null;
        }
        T t = gson.fromJson(strJson, cls);
        return t;
    }

    /**
     * 保存对象集合
     *
     * @param key   对象集合的key
     * @param value 对象集合
     */
    public <T> void saveList(String key, List<T> value) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        if (null == value || value.size() == 0) {
            editor.putString(key, "");
        } else {
            //转换成json数据，再保存
            String strJson = gson.toJson(value);
            editor.putString(key, strJson);
        }
        editor.commit();
    }


    /**
     * 读取对象集合
     *
     * @param key 对象集合的key
     */
    public <T> List<T> readList(String key, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        String strJson = sharedPrefs.getString(key, null);
        if (TextUtils.isEmpty(strJson)) {
            return list;
        } else {
            try {
                JsonArray arry = new JsonParser().parse(strJson).getAsJsonArray();
                for (JsonElement jsonElement : arry) {
                    list.add(gson.fromJson(jsonElement, cls));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }
            return list;
        }
    }


    /**
     * 往key中保存的对象集合添加对象
     *
     * @param key   对象集合的key
     * @param value 待添加对象
     */
    public <T> void addList(String key, T value, Class<T> cls) {
        List<T> data = readList(key, cls);
        if (value == null || data.contains(value)) {
            return;
        } else {
            data.add(value);
        }
        saveList(key, data);
    }

    /**
     * 往key中保存的对象集合添加对象
     *
     * @param key   对象集合key
     * @param value 待删除对象
     * @return 是否删除成功
     */
    public <T> boolean removeList(String key, T value, Class<T> cls) {
        List<T> data = readList(key, cls);
        if (value == null || data.contains(value)) {
            return false;
        }
        data.remove(value);
        saveList(key, data);
        return true;
    }

    /**
     * 往key中保存的对象集合移除对象集合
     *
     * @param key   对象集合key
     * @param value 待删除对象
     * @return 是否删除成功
     */
    public <T> boolean removeList(String key, List<T> value, Class<T> cls) {
        List<T> data = readList(key, cls);
        if (value == null || value.size() == 0) {
            return false;
        }
        for (int i = 0; i < value.size(); i++) {
            if (data.contains(value.get(i))) {
                data.remove(value.get(i));
            }
        }
        saveList(key, data);
        return true;
    }


}