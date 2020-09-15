package com.locker.manager.activity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.yidao.module_lib.utils.ToastUtil;

public class NetWorkStateReceiver extends BroadcastReceiver {

    private IOnNetworkCallback onNetworkCallback;

    public void setOnNetworkCallback(IOnNetworkCallback onNetworkCallback) {
        this.onNetworkCallback = onNetworkCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                ToastUtil.showLongToast("WIFI已连接,移动数据已连接");
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                ToastUtil.showLongToast("WIFI已连接,移动数据已断开");
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                ToastUtil.showLongToast( "WIFI已断开,移动数据已连接");
            } else {
                ToastUtil.showLongToast( "WIFI已断开,移动数据已断开");
            }
            if(onNetworkCallback!=null){
                onNetworkCallback.onNetwork((wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()) ? true : false);
            }
//API大于23时使用下面的方式进行网络监听
        }else {

            boolean isConnect = false;

            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());

                if(networkInfo.isConnected()){
                    isConnect = true;
                }
            }

            if(onNetworkCallback!=null){
                onNetworkCallback.onNetwork(isConnect);
            }
            ToastUtil.showLongToast(sb.toString());
        }

    }


    public interface IOnNetworkCallback{
        void onNetwork(boolean connect);
    }
}
