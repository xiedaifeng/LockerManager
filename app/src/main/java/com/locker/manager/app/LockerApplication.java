package com.locker.manager.app;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.locker.manager.task.LockerManagerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.yidao.module_lib.base.BaseApplication;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;


public class LockerApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if(isMainProcess()){

            TaskDispatcher.init(this);
            TaskDispatcher.createInstance().addTask(new LockerManagerTask()).start();


            CreateDeviceRequestBean requestBean = new CreateDeviceRequestBean();
            requestBean.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getApplicationContext());
            LogUtils.e("CreateDeviceRequestBean:"+requestBean.device_id);
            HttpClient.request(requestBean, false, new IHttpCallBack() {
                @Override
                public void success(ResponseBean responseBean) {
                    LogUtils.e("success:createDevice");
                }
                @Override
                public void failed(ResponseBean responseBean) {
                    LogUtils.e("failed:createDevice");
                }
            });


            PushManager.getInstance().initialize(this);
            PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
                @Override
                public void log(String s) {
                    Log.i("PUSH_LOG",s);
                }
            });
        }
    }

    /**
     * 获取当前进程名
     */
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public boolean isMainProcess() {
        return getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }
}
