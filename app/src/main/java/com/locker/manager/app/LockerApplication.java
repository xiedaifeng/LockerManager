package com.locker.manager.app;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.locker.manager.task.ImageDownLoader;
import com.locker.manager.task.LockerManagerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.crashreport.CrashReport;
import com.yidao.module_lib.base.BaseApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;


public class LockerApplication extends BaseApplication {


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

            Context context = getApplicationContext();
// 获取当前包名
            String packageName = context.getPackageName();
// 获取当前进程名
            String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
            CrashReport.initCrashReport(context, "f92e8e8e4f", false, strategy);
//            CrashReport.initCrashReport(getApplicationContext(), "f92e8e8e4f", false);
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


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
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
}
