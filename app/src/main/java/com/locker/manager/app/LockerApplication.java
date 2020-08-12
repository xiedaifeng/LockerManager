package com.locker.manager.app;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.locker.manager.task.ImageDownLoader;
import com.locker.manager.task.LockerManagerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.squareup.picasso.Picasso;
import com.yidao.module_lib.base.BaseApplication;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.glide.UnsafeOkHttpClient;
import com.yidao.module_lib.utils.CommonGlideUtils;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;

import java.io.InputStream;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;


public class LockerApplication extends BaseApplication {

    public static String sOrderId = null;

    public static String sOpenCode = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if(isMainProcess()){

            TaskDispatcher.init(this);
            TaskDispatcher.createInstance().addTask(new LockerManagerTask()).start();

            PushManager.getInstance().initialize(this);
            PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
                @Override
                public void log(String s) {
                    Log.i("PUSH_LOG",s);
                }
            });


            OkHttpClient client = new OkHttpClient.Builder()
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .build();
            Picasso.setSingletonInstance(new Picasso.Builder(this).
                    downloader(new ImageDownLoader(client)).loggingEnabled(true)
                    .build());
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
