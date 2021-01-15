package com.ymz.Utils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ymz.App.App;
import com.ymz.Utils.HttpUtils.BaseCallback;
import com.ymz.Utils.HttpUtils.HttpClient;

import org.xutils.common.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

//全部错误捕捉器
public class MyCatchException implements Thread.UncaughtExceptionHandler {
    //本类实例
    private static MyCatchException mInstance;
    //系统默认的uncatchException
    private Thread.UncaughtExceptionHandler mDefaultException;

    private Context mContext;

    //保证只有一个实例
    public MyCatchException() {
    }

    //单例模式
    public static MyCatchException getInstance() {
        if (mInstance == null) {
            mInstance = new MyCatchException();
        }
        return mInstance;
    }

    //获取系统默认的异常处理器,并且设置本类为系统默认处理器
    public void init(Context ctx) {
        this.mContext = ctx;
        mDefaultException = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //自定义错误处理器
    private boolean handlerException(Throwable ex) {
        if (ex == null) {  //如果已经处理过这个Exception,则让系统处理器进行后续关闭处理
            return false;
        }

        //获取错误原因
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause!=null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        final String msg= result ;

        new Thread() {
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
              //  Toast.makeText(mContext, "程序出错:" + msg.toString(), Toast.LENGTH_LONG).show();
                //将异常记录到本地的数据库或者文件中.或者直接提交到后台服务器
                Log.e("全局异常",msg);
                fileHttp(msg);
                Looper.loop();
            }

            ;
        }.start();
        return true;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handlerException(ex) && mDefaultException != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultException.uncaughtException(thread, ex);
        } else { //否则自己进行处理
//            try {  //Sleep 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                Log.e("全局异常",e.getMessage());
//                Log.d("2635", "uncaughtException: "+e.getMessage());
//            }catch (Exception e){
//                Log.e("全局异常",e.getMessage());
//                Log.d("2635", "Exception: "+e.getMessage());
//            }
//            //如果不关闭程序,会导致程序无法启动,需要完全结束进程才能重新启动
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
        }

    }



    private void fileHttp(String content) {

        String getAppName=getAppName(App.getAppContext());
        String model=getSystemModel();
        String Version=getSystemVersion();
        String httpApi="http://saas119.ymz666.com/vue.php?m=customer&a=getError";
        Map<String, Object> parame = new HashMap<>();
        parame.put("info",model+"\n"+Version+ "\n"+getAppName+"\n\n======//"+content);
        Log.e("APPException====///",httpApi+"=="+content);
        HttpClient.getInstance().post(App.getAppContext(), httpApi, parame, new BaseCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                Log.d("APPException====", "APPException:" + result);
            }

            @Override
            public void onError(String msg) {
            }

            @Override
            public void onCancelled(Callback.CancelledException var1) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 获取应用程序名称
     */
    private  synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

}
