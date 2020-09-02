package com.locker.manager.task;


import com.qiao.launch.starter.task.Task;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerianPortSDKListener;
import com.yidao.module_lib.utils.LogUtils;

public class LockerManagerTask extends Task {
    @Override
    public void run() {
        try {
            SerialPortOpenSDK.getInstance().setSerialPort("/dev/ttyS2",19200,1,8,0,0,0);
            SerialPortOpenSDK.getInstance().initialize(mContext);
            SerialPortOpenSDK.getInstance().setListener(new SerianPortSDKListener() {
                @Override
                public void initListener(int error, String errorMessage) {
                   LogUtils.e("initListener:"+error+",errorMessage:"+errorMessage);
                    if (error==0x01){
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
