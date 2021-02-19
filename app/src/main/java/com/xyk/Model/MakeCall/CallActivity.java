package com.xyk.Model.MakeCall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.xyk.App.App;
import com.xyk.Utils.AppToast;
import com.xyk.Utils.HttpUtils.BaseCallback;
import com.xyk.Utils.HttpUtils.HttpClient;
import com.xyk.Utils.SharedPrefUtil;
import com.xyk.ViewUtils.ProgressDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

import static com.xyk.Utils.UrlUtils.Url.DOMAIN_AXBCALLDJ;
import static com.xyk.Utils.UrlUtils.Url.DOMAIN_Call;
import static com.xyk.Utils.UrlUtils.Url.DOMAIN_CallCMCC;
import static com.xyk.Utils.UrlUtils.Url.DOMAIN_CallAXB;
import static com.xyk.Utils.UrlUtils.Url.dialpanel_phone;

import static com.xyk.Utils.UrlUtils.Url.DOMAIN_updateCutomerField;

/**
 * Administrator  ：zhouyuru
 * 2020/10/23
 * Describe ：拨打电话工具类
 */
public class CallActivity {
    //拨打电话功能
    public static void Call(Activity activity, Context context, String phone, String customer_id,int calltype) {
        String state = SharedPrefUtil.getInstance().getString(SharedPrefUtil.Login_Db_state, "1");
//        更新拨打次数  calltype  1不进行更新拨打次数
        if (calltype!=1){
            updateCutomerField(context, customer_id);
        }
        Log.e("state====/2",state);
        if (state.equals("1")) {
            AXBCall(activity, context, phone);
        }
        if (state.equals("2")) {
            NetWorkCall(context, phone);
        }

        if (state.equals("3")) {
            GPHBCall(activity, context, phone);
        }
        if (state.equals("4")) {
            CMCCCall(context, phone);
        }
        if (state.equals("5")) {
            DialPanel(context, phone);
        }

        if (state.equals("6")) {
            xslCall(context, phone,calltype);
        }

    }


    public static void AXBCall(Activity activity, final Context context, final String phone) {
//        申请拨打权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.CALL_PHONE};
            //验证是否许可权限
            for (String str : permissions) {
                if (context.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        /////vpmuu000

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(context);
            Map<String, Object> parame = new HashMap<>();
            parame.put("mobile", phone);
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(context, DOMAIN_CallAXB, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        String stel=json.getString("data");

                        AppToast.showToast(json.getString("info"));
                        json = new JSONObject(stel);
                        stel=json.getString("x_tel");
                        if (stel.length()==11) {
                            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + stel));
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent2);
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

        //vpmuu111


    }


    //高频回拨
    public static void GPHBCall(Activity activity, final Context context, final String phone) {
//        申请拨打权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.CALL_PHONE};
            //验证是否许可权限
            for (String str : permissions) {
                if (context.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
//        获取本机号码
        final String Hostphone = SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_Host_phone, "");
//        获取转播方式
        final String ZBState = SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_Zb_State, "21");

        if (!TextUtils.isEmpty(Hostphone)) {
            // 先获取客户号码，设置无条件呼叫转移 来电转接给客户
            if (phone.length() >= 5) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:%2A%2A" + ZBState + "%2A" + phone + "%23"));
                context.startActivity(intent);
                //拨打本机号码
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //再拨打本机号码（就是将设置占线时拨打客户手机号，拨打自己的手机号，实现占线效果，呼叫转移给客户）
                        Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Hostphone));
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                    }
                }, 5000);  //5秒后执行拨打电话
            } else {
                AppToast.showToast("号码不合法");
            }
        } else {
            AppToast.showToast("请设置本机号码");
        }
    }


    //DialPanel
    public static void DialPanel(Context context, String phone) {
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(context);
            //
            dialpanel_phone=phone;
            App.startActivity(context, MakeCallActivity.class);
            /*
            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dialpanel_phone));
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
            */

            ProgressDialogUtil.getInstance().stopLoad();
        }
    }

    //cmcc call
    public static void CMCCCall(Context context, String phone) {
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(context);
            Map<String, Object> parame = new HashMap<>();
            parame.put("mobile", phone);
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(context, DOMAIN_CallCMCC, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        AppToast.showToast(json.getString("info"));
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

    public static void xslCall(final Context context, String phone, final int calltype) {
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(context);
            Map<String, Object> parame = new HashMap<>();
            parame.put("mobile", phone);
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(context, DOMAIN_AXBCALLDJ, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    Log.d("TEST", "萧三郎拨号:" + result);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        String data = json.optString("data");//x_tel
                        AppToast.showToast(json.optString("info"));
                        if (!TextUtils.isEmpty(data)) {
                            JSONObject json1 = new JSONObject(data);
                            String stel = json1.optString("x_tel");
                            if (!TextUtils.isEmpty(stel)) {
                              //
                                Log.e("dialpanel_phone==",dialpanel_phone+"////");
                                if (calltype==1){
                                    dialpanel_phone = "";
                                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + stel));
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent2);
                                }else {

                                    App.startActivity(context, MakeCallActivity.class);
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
                    Log.d("TEST", "萧三郎拨号报错:" + msg);
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


    //ctc call
    public static void NetWorkCall(Context context, String phone) {
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(App.getToken())) {
            ProgressDialogUtil.getInstance().startLoad(context);
            Map<String, Object> parame = new HashMap<>();
            parame.put("mobile", phone);
            parame.put("token", App.getToken());
            //http://demo.ymz666.com/vue.php?m=user&a=getCallType
            HttpClient.getInstance().post(context, DOMAIN_Call, parame, new BaseCallback<String>(String.class) {
            //HttpClient.getInstance().post(context, "http://demo.ymz666.com/vue.php?m=user&a=getCallType", parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                        AppToast.showToast(json.getString("info"));
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


    //清除来电转接
    public static void ClearCall(Activity activity, Context context) {
        //android6版本获取动态权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.CALL_PHONE};
            //验证是否许可权限
            for (String str : permissions) {
                if (context.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        final String ZBState = SharedPrefUtil.getInstance().getString(SharedPrefUtil.User_Zb_State, "21");
//           清除设置来电转接
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:%23%23" + ZBState + "%23"));
        context.startActivity(intent);
    }

    //更新拨打次数
    public static void updateCutomerField(Context context, String customer_id) {
        if (!TextUtils.isEmpty(App.getToken()) && !TextUtils.isEmpty(customer_id)) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("customer_id", customer_id);
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(context, DOMAIN_updateCutomerField, parame, new BaseCallback<String>(String.class) {
                @Override
                public void onSuccess(String result) {
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
    }

}
