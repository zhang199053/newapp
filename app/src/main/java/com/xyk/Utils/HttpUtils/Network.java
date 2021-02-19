package com.xyk.Utils.HttpUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.xutils.common.util.LogUtil;

/**
 * Des：
 *
 * @author zhouyuru
 * @date 2018/4/20
 */

public class Network {

    //连接管理
    private static ConnectivityManager connectivityManager;
    /**
     * 检测网络是否可用。
     *
     * @return 是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        LogUtil.d("检测网络是否可用...");
        final NetType type = getNetworkType(context);
        if (type != NetType.NONE) {
            final NetworkInfo ni = getConnectivityManager(context).getActiveNetworkInfo();
            return (ni != null && ni.isConnectedOrConnecting());
        }
        return false;
    }
    /**
     * 获取当前网络类型。
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static NetType getNetworkType(Context context) {
        NetType type = NetType.NONE;
        if (getConnectivityManager(context) == null) {
            return type;
        }
        final NetworkInfo networkInfo =getConnectivityManager(context).getActiveNetworkInfo();
        if (networkInfo == null) {
            return type;
        }

        final int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            final String extraInfo = networkInfo.getExtraInfo();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(extraInfo) && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(extraInfo, "cmnet")) {
                type = NetType.CNNET;
            } else {
                type = NetType.CNWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            type = NetType.WIFI;
        }
        LogUtil.d("获取当前网络类型..."+type);
        return type;
    }

    /**
     * 网络类型。
     *
     * @author jeasonyoung
     * @since 2015年9月2日
     */
    public enum NetType {
        /**
         * 无网络。
         */
        NONE,
        /**
         * WIFI.
         */
        WIFI,
        /**
         * CNWAP.
         */
        CNWAP,
        /**
         * CNNET.
         */
        CNNET
    }
    //获取连接管理
    private static ConnectivityManager getConnectivityManager(Context context) {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            LogUtil.d("从系统服务中加载连接管理器...");
        }
        return connectivityManager;
    }
}
