package com.locker.manager.task;


import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.example.http_lib.utils.UserCacheHelper;
import com.qiao.launch.starter.task.Task;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerianPortSDKListener;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

public class LockerManagerTask extends Task {

    private String path;

    public LockerManagerTask() {
        this.path = UserCacheHelper.getSerial();
    }

    public LockerManagerTask(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        try {
            String serialPath = TextUtils.isEmpty(path) ? "/dev/ttyS2" : path;
            if (TextUtils.isEmpty(path)) {
                SerialPortOpenSDK.getInstance().setSerialPort(serialPath, 19200, 1, 8, 0, 0, 0);
                SerialPortOpenSDK.getInstance().initialize(mContext);
                SerialPortOpenSDK.getInstance().setListener(new SerianPortSDKListener() {
                    @Override
                    public void initListener(int error, String errorMessage) {
                        LogUtils.e("initListener:" + error + ",errorMessage:" + errorMessage);
                        if (error == 0x01) {

                        }
                    }
                });
            } else {
                SerialPortOpenSDK.getInstance().setSerialPort(serialPath, 19200, 1, 8, 0, 0, 0);
                SerialPortOpenSDK.getInstance().reStartInitialize(mContext);
                SerialPortOpenSDK.getInstance().setListener(new SerianPortSDKListener() {
                    @Override
                    public void initListener(int error, String errorMessage) {
                        LogUtils.e("initListener:" + error + ",errorMessage:" + errorMessage);
                        if (error == 0x01) {
                            UserCacheHelper.setSerial(serialPath);
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                Toast toast = Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                                Looper.loop();
                            }
                        }).start();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
