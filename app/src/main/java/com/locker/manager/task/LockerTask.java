package com.locker.manager.task;


import android.util.Log;

import com.qiao.launch.starter.task.Task;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerianPortSDKListener;
import com.yidao.module_lib.utils.LogUtils;

public class LockerTask extends Task {
    @Override
    public void run() {
        try {
           SerialPortOpenSDK.getInstance().initialize(mContext);
            SerialPortOpenSDK.getInstance().setListener(new SerianPortSDKListener() {
                @Override
                public void initListener(int error, String errorMessage) {
                    LogUtils.e("initListener:"+error+",errorMessage:"+errorMessage);
                    if (error==0x01){
                        SerialPortOpenSDK.getInstance().setSerialPort("/dev/ttyS2",9600,1,8,0,0,0);
                    }

                }
            });
            String [] strings=SerialPortOpenSDK.getInstance().getDevices();

            if (strings!=null&&strings.length>0){
              for (String s:strings){
                  Log.e("locker", s+"");
              }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
