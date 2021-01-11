package com.cyb.Utils.HttpUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.cyb.App.App;
import com.cyb.Model.Login.LoginActivity;
import com.cyb.Utils.ActivityMgr;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.MD5Utils;
import com.cyb.Utils.SharedPrefUtil;


import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyb.Utils.HttpUtils.DownloaDirectory.DOC_DIRECTORY;
import static org.xutils.x.http;

public class HttpClient {

    private volatile static HttpClient instance;
    /**
     * 正确回掉code
     */
    private final static int ERR_OK = 200;
    /**
     * 网络请求超时
     */
    private final static int CONNECT_TIME_OUT = 1000 * 30;
    /**
     * 网络读写超时
     */
    private final static int READ_TIME_OUT = 1000 * 30;

    private static final String REQUEST_FAILED = "网络请求失败！";
    private static final String UPDOWNLOAD_FAILED = "上传失败！";
    private static final String NETWORK_CONNECTION_FAILED = "网络连接失败！";
    private static final String NETWORK_ERROR = "请求异常！";
    private static final String TAG = "HttpClient";

    private File saveFiles;

    private Gson mGson;

//    private RequestParams mRequest;

    /**
     * 无参构造函数
     */
    private HttpClient() {
        // mGson = GsonBinder.gson;
        mGson = new Gson();
//        mRequest = new RequestParams();
//        //请求超时
//        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
//        //读写超时
//        mRequest.setReadTimeout(READ_TIME_OUT);
//        //断点续传
//        mRequest.setAutoRename(true);
//        //编码格式
//        mRequest.setCharset("UTF-8");
    }

    /**
     * Double Check 单例模式
     *
     * @return
     */
    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();

                }
            }
        }
        return instance;
    }


    /**
     * 异步get请求
     *
     * @param url
     * @param maps
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable get(Context context, String url, Map<String, Object> maps, final BaseCallback<T> listener) {
        return get(context, url, maps, TAG, listener);
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable get(Context context, String url, final BaseCallback<T> listener) {

        return get(context, url, new HashMap<String, Object>(), TAG, listener);
    }

    /**
     * 异步get请求Playauth
     *
     * @param url
     * @param listener
     * @return
     */

    public <T> Callback.Cancelable getPlayauth(Context context, String url, Map<String, Object> maps, final BaseCallback<T> listener) {
        return getPlayauth(context, url, maps, TAG, listener);
    }

    /**
     * 异步get请求Playauth
     *
     * @param url
     * @param maps
     * @param tag
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable getPlayauth(final Context context, final String url, final Map<String, Object> maps, String tag, final BaseCallback<T> listener) {
        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            listener.onError(NETWORK_CONNECTION_FAILED);
            listener.onFinished();
            return null;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        //重置参数
        mRequest.setUri(url);
        mRequest.setMultipart(false);
        mRequest.setMethod(HttpMethod.GET);
        mRequest.clearParams();
        if (!maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                mRequest.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = http().get(mRequest, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.d("返回数据\n" + result + "\n" + "请求url\n" + url + "\n" + "请求参数\n" + maps.toString());
                try {
                    if (!TextUtils.isEmpty(result)) {
                        if (listener.mClass == String.class) {
                            listener.onSuccess((T) result);
                        } else {
                            JSONObject json = new JSONObject(result);
                            T obj = mGson.fromJson(result, listener.mClass);
                            listener.onSuccess(obj);
                        }
                    } else {
                        listener.onError(REQUEST_FAILED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(NETWORK_ERROR);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("异常\n" + ex.getMessage());
                listener.onError(NETWORK_ERROR);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("异常\n" + cex.getMessage());
                listener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }
        });

        return cancelable;
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param maps
     * @param tag
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable get(final Context context, final String url, final Map<String, Object> maps, String tag, final BaseCallback<T> listener) {
        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            listener.onError(NETWORK_CONNECTION_FAILED);
            listener.onFinished();
            return null;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        //重置参数
        mRequest.setUri(url);
        mRequest.setMultipart(false);
        mRequest.setMethod(HttpMethod.GET);
        mRequest.clearParams();


        if (!maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                mRequest.addParameter(entry.getKey(), entry.getValue());
            }

        }

        Callback.Cancelable cancelable = http().get(mRequest, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.d("返回数据\n" + result + "\n" + "请求url\n" + url + "\n" + "请求参数\n" + maps.toString());
                try {
                    if (!TextUtils.isEmpty(result)) {
                        if (listener.mClass == String.class) {
                            listener.onSuccess((T) result);
                        } else {
                            JSONObject json = new JSONObject(result);
                            if (json.has("info") && json.getString("info") == "success") {
                                T obj = mGson.fromJson(result, listener.mClass);
                                listener.onSuccess(obj);
                            } else if (json.has("code") && json.getInt("code") == 403) {
//                                在别的设备上登录或token过期
                                LoginFailed(context, 403);
                            } else {
                                listener.onError(json.getString("msg"));
                            }
                        }
                    } else {
                        listener.onError(REQUEST_FAILED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(NETWORK_ERROR);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("异常\n" + ex.getMessage());
                listener.onError(NETWORK_ERROR);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("异常\n" + cex.getMessage());
                listener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }
        });

        return cancelable;
    }


    /**
     * 异步post请求，去除请求标志，默认为TAG
     *
     * @param url
     * @param maps
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable post(Context context, String url, Map<String, Object> maps, final BaseCallback<T> listener) {
        return post(context, url, maps, TAG, listener);
    }

    /**
     * 异步post请求
     *
     * @param context
     * @param url
     * @param maps
     * @param tag
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable post(final Context context, final String url, final Map<String, Object> maps, String tag, final BaseCallback<T> listener) {
        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            listener.onError(NETWORK_CONNECTION_FAILED);
            listener.onFinished();
            return null;
        }

        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        mRequest.setUri(url);
        mRequest.setMultipart(false);
        mRequest.setMethod(HttpMethod.POST);
        mRequest.clearParams();
        if (maps != null) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                //剔除参数值为NULL的参数
                if (entry.getValue() == null) {
                    continue;
                }
                mRequest.addBodyParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        LogUtil.d("----------请求url--------------" + mRequest.toString());
        try {
            if (null != mRequest.getRequestBody()) {
                LogUtil.d("----------请求url的body--------------" + mRequest.getRequestBody().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Callback.Cancelable post = http().post(mRequest, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                LogUtil.d("返回数据\n" + result);
                try {
                    if (!TextUtils.isEmpty(result)) {
                        if (listener.mClass == String.class) {
                            listener.onSuccess((T) result);
                        } else {
                            JSONObject json = new JSONObject(result);
                            if (json.getInt("status") == 1) {
                                T obj = mGson.fromJson(result, listener.mClass);
                                listener.onSuccess(obj);
                            } else if (json.getInt("status") == -1) {
                                {
                                    LoginFailed(context, -1);
//                                    -1未登录
                                }
                            } else {
                                listener.onError(json.getString("info"));
                            }
                        }
                    } else {
                        listener.onError(REQUEST_FAILED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(NETWORK_ERROR);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("异常\n" + ex.getMessage());
                listener.onError(NETWORK_ERROR);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("异常\n" + cex.getMessage());
                listener.onCancelled(cex);
            }

            // 不管成功或者失败最后都会回调该接口
            @Override
            public void onFinished() {
                listener.onFinished();
            }
        });

        return post;
    }

    /**
     * 403登录失效   400缺少token
     */
    public void LoginFailed(Context context, int code) {
        if (code == -1) {
            //别的设备登录过,跳到登录界面
            //退出登录  清除用户登录信息
            SharedPrefUtil.getInstance().clearUserInfor();
            ActivityMgr.getInstance().removeAll();
            AppToast.showToast("请重新登陆");
            if (context != null) {
                context.startActivity(new Intent(context, LoginActivity.class));
            } else {
                AppToast.showToast("上下文空");
            }

        }

    }


    /**
     * 同步post请求
     *
     * @param context
     * @param url
     * @param maps
     * @return
     */
    public String postSync(final Context context, String url, Map<String, Object> maps) throws Throwable {

        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            return null;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        mRequest.setUri(url);
        mRequest.setMultipart(false);
        mRequest.setMethod(HttpMethod.POST);
        mRequest.clearParams();


        if (!maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                //剔除参数值为NULL的参数
                if (entry.getValue() == null) {
                    continue;
                }
                mRequest.addBodyParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        LogUtil.d("=======http-request-url::::::" + url);
        LogUtil.d("=======http-request-parameters::::::" + mRequest.toString());
        return x.http().postSync(mRequest, String.class);

    }

    /**
     * 同步get请求
     *
     * @param context
     * @param url
     * @param maps
     * @return
     */
    public String getSync(final Context context, String url, Map<String, Object> maps) throws Throwable {

        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            return null;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        mRequest.setUri(url);
        mRequest.setMultipart(false);
        mRequest.setMethod(HttpMethod.GET);
        mRequest.clearParams();


        if (!maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                //剔除参数值为NULL的参数
                if (entry.getValue() == null) {
                    continue;
                }
                mRequest.addBodyParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        LogUtil.d("=======http-request-url::::::" + url);
        LogUtil.d("=======http-request-parameters::::::" + mRequest.toString());
        return x.http().getSync(mRequest, String.class);

    }


    /**
     * 文件上传
     *
     * @param url
     * @param maps
     * @param file
     * @param requestCode
     * @param listener
     * @return
     */
    public <T> Callback.Cancelable upLoadFile(String url, Map<String, Object> maps, Map<String, File> file,
                                              final String requestCode, final UpLoadCallback<T> listener) {
        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            listener.onError(new Throwable(UPDOWNLOAD_FAILED), requestCode);
            listener.onFinished();
            return null;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        mRequest.setUri(url);
        mRequest.setMethod(HttpMethod.POST);
        mRequest.clearParams();

        if (!maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                mRequest.addBodyParameter(entry.getKey(), entry.getValue().toString());
            }
        }


        if (file != null && !maps.isEmpty()) {
            for (Map.Entry<String, File> entry : file.entrySet()) {
                mRequest.addBodyParameter(entry.getKey(), entry.getValue().getAbsoluteFile());
            }
        }

        LogUtil.d("=======http-request-url::::::" + url);
        LogUtil.d("=======http-request-parameters::::::" + mRequest.toString());

        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        mRequest.setMultipart(true);//这个是标示上传的文件内容的,

        Callback.Cancelable upLoad = http().post(mRequest, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.d("返回数据\n" + result);
                try {
                    if (!TextUtils.isEmpty(result)) {
                        if (listener.mClass == String.class) {
                            listener.onSuccess((T) result);

                        } else {
                            JSONObject json = new JSONObject(result);
                            if (json.has("code") && json.getInt("code") == ERR_OK) {
                                T obj = mGson.fromJson(result, listener.mClass);
                                listener.onSuccess(obj);
                            } else {
                                listener.onError(new Throwable(UPDOWNLOAD_FAILED), requestCode);
                            }
                        }
                    } else {
                        listener.onError(new Throwable(UPDOWNLOAD_FAILED), requestCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(new Throwable(UPDOWNLOAD_FAILED), requestCode);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex, requestCode);
                LogUtil.i("=======---onError---======:" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                listener.onCancelled(cex);
                LogUtil.i("=======---onCancelled---======:" + cex.toString());
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }

            @Override
            public void onWaiting() {
                listener.onWaiting();
                LogUtil.i("=======---onWaiting---======:");
            }

            @Override
            public void onStarted() {
                listener.onStarted();
                LogUtil.i("=======---onStarted---======:");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                listener.onLoading(total, current, isDownloading, requestCode);
                LogUtil.i("=======---onLoading---======:"
                        + "total:" + total
                        + "---current:" + current
                        + "---isDownloading:" + isDownloading
                );
            }
        });

        return upLoad;
    }

    /**
     * 文件下载
     *
     * @param url
     * @param filepath
     * @param requestCode
     * @param listener
     */
    public void downLoadFile(String url, String filepath, String name, final String requestCode, final DownLoadCallback listener) {

        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            return;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        mRequest.setUri(url);
        mRequest.setMethod(HttpMethod.GET);
        mRequest.clearParams();
        //设置文件保存的路径
        mRequest.setSaveFilePath(createPath(filepath, name));


        http().get(mRequest, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                listener.onSuccess(result, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex, isOnCallback, requestCode);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                listener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }

            @Override
            public void onWaiting() {
                listener.onWaiting();
            }

            @Override
            public void onStarted() {
                listener.onStarted();
            }

            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                listener.onLoading(total, current, isDownloading, requestCode);
            }
        });

    }

    /**
     * 文件下载
     *
     * @param url
     * @param name
     * @param requestCode
     * @param listener
     */
    public void downLoadFile(String url, String name, final String requestCode, final DownLoadCallback listener) {

        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            return;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        //断点续传
        mRequest.setAutoRename(true);
        mRequest.setUri(url);
        mRequest.setMethod(HttpMethod.GET);
        //设置文件保存的路径

        mRequest.setSaveFilePath(createPath(null, name));
        mRequest.clearParams();


        http().get(mRequest, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                listener.onSuccess(result, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex, isOnCallback, requestCode);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                listener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }

            @Override
            public void onWaiting() {
                listener.onWaiting();
            }

            @Override
            public void onStarted() {
                listener.onStarted();
            }

            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                listener.onLoading(total, current, isDownloading, requestCode);
            }
        });

    }

    /**
     * 创建下载存储根目录。
     *
     * @param download_files_dir 文件目录后缀。
     * @param name               文件名称。
     */
    public String createPath(String download_files_dir, String name) {
        LogUtil.d("createPath:... ");
//        保存至sd上
        saveFiles = Environment.getExternalStorageDirectory();
//        String currentUserId = SharedPrefUtil.getInstance().getUserId();
        //如果SD卡不存在，则检查内部存储
        if (saveFiles == null || !saveFiles.exists()) {
            LogUtil.d("createPath:未检测到SD卡,将使用内部存储! ");
            saveFiles = Environment.getDataDirectory();
        }
        final Context context = App.getAppContext();
        saveFiles = new File(saveFiles
                + File.separator + context.getPackageName()
//                + File.separator + MD5Utils.md5Hex(currentUserId)
                + File.separator + (TextUtils.isEmpty(download_files_dir) ? DOC_DIRECTORY : download_files_dir)
                + File.separator + name);
//        String s = saveFiles + File.separator + name;
        LogUtil.d("存储路径:" + saveFiles.getAbsolutePath());
        //下载文件
        return saveFiles.getAbsolutePath();
    }


    public List<String> loadRoot(String docDirectory) {
        saveFiles = Environment.getExternalStorageDirectory();
        String currentUserId = SharedPrefUtil.getInstance().getUSER_admin();
        if (saveFiles == null || !saveFiles.exists()) {//如果SD卡不存在，则检查内部存储
            saveFiles = Environment.getDataDirectory();
        }
        final Context context = App.getAppContext();
        saveFiles = new File(saveFiles
                + File.separator + context.getPackageName()
                + File.separator + MD5Utils.md5Hex(currentUserId)
                + File.separator + docDirectory
        );


        LogUtil.d("loadFinishedFiles: ...");
        final List<String> list = new ArrayList<>();
        //检查文件目录是否存在
        if (this.saveFiles != null && this.saveFiles.exists()) {
            //加载目录下全部文件
            final File[] allFiles = this.saveFiles.listFiles();
            if (allFiles != null && allFiles.length > 0) {
                for (File file : allFiles) {
                    //排除非文件
                    if (!file.isFile()) {
                        continue;
                    }
                    final String fName = file.getName();
                    //添加到列表
                    list.add(fName);
                }
            }
        }

        return list;


    }

    /**
     * 文件下载
     *
     * @param url
     * @param name
     * @param requestCode
     * @param listener
     */
    public void downLoad(String url, String name, final String requestCode, final DownLoadCallback listener) {

        if (!Network.isNetworkConnected(App.getAppContext())) {
            AppToast.showToast(NETWORK_CONNECTION_FAILED);
            return;
        }
        RequestParams mRequest = new RequestParams();
        //请求超时
        mRequest.setConnectTimeout(CONNECT_TIME_OUT);
        //读写超时
        mRequest.setReadTimeout(READ_TIME_OUT);
        mRequest.setUri(url);
        mRequest.setMethod(HttpMethod.GET);
        //设置文件保存的路径
        try {
            String[] split = url.split("\\.");
            mRequest.setSaveFilePath(createPath(null, name) + "." + split[split.length - 1]);
        } catch (Exception e) {
            e.getMessage();
        }
        mRequest.clearParams();
        http().get(mRequest, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                listener.onSuccess(result, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex, isOnCallback, requestCode);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                listener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }

            @Override
            public void onWaiting() {
                listener.onWaiting();
            }

            @Override
            public void onStarted() {
                listener.onStarted();
            }

            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                listener.onLoading(total, current, isDownloading, requestCode);
            }
        });

    }


}