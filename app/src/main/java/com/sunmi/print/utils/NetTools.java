package com.sunmi.print.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

public class NetTools {

    public static final int WAP = -5;
    public static final int OK = 1;
    public static final int DISABLE = -1;
    public static final int FAIL = -2;
    /**
     * 未开启移动网络或WLAN
     */
    public static final int NOINFO = -3;
    public static final int EXCEPTION = -4;

    // public static final int REQUEST_CODE_NET_SETTING = 9999;

    /** * 判断是否为合法IP * @return the ip */
    // public static boolean isLegalIp(String ipAddress) {
    // String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.
    // (\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    // Pattern pattern = Pattern.compile(ip);
    // Matcher matcher = pattern.matcher(ipAddress);
    // return matcher.matches();
    // }

    /**
     * 通过路径获得上一层路径,出现异常或者其他情况给出根目录 gyl
     */
    public static String getParentPath(String path) {
        String parentPath = null;
        if (!TextUtils.isEmpty(path)) {
            int lastIndex = path.lastIndexOf("/");
            parentPath = path.substring(0, lastIndex + 1);
        }
        return parentPath;
    }

    /** * 判断是否为合法IP * @return the ip */
    // public boolean isboolIp(String ipAddress) {
    // String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.
    // (\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    // Pattern pattern = Pattern.compile(ip);
    // Matcher matcher = pattern.matcher(ipAddress);
    // return matcher.matches();
    // }


    /**
     * 检测当前是否有可用网络
     *
     * @return 1 可用 -1 不可用 0wap网络 -2连接测试网站失败 -3无网络信息 -4异常
     */
    public static int checkNetConnection(Context context) {
        try {
            ConnectivityManager mConnManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnManager.getActiveNetworkInfo();

            if (mNetworkInfo == null) {// 检测是否有可用网络信息
                return NOINFO;
            }
            // Debug.d("netTypeName()=" + mNetworkInfo.getTypeName() +
            // " netExtraInfo()="
            // + mNetworkInfo.getExtraInfo() + " netisConnected()=" +
            // mNetworkInfo.isConnected());
            if (!mNetworkInfo.isConnected()) {
                return DISABLE;// 网络没有连接
            }
            // 检测是否是中CMWAP网络,先判断是否为空
            if (!TextUtils.isEmpty(mNetworkInfo.getExtraInfo())
                && mNetworkInfo.getExtraInfo().toLowerCase().indexOf("wap") > 0) {
                return WAP;
            }
            return OK;

        } catch (Exception e) {
            return EXCEPTION;
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    /**
     * 判断是否可联网
     */
    public static boolean canNetworking(Context context) {
//        int netState = checkNetConnection(context);
        return isNetworkAvailable(context);
    }


}
