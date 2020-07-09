package com.locker.manager.task;


import android.util.Log;

import com.qiao.launch.starter.task.Task;
import com.qiao.serialport.SerialPortOpenSDK;

public class LockerTask extends Task {
    @Override
    public void run() {
        try {
            SerialPortOpenSDK.getInstance().initialize(mContext);
            String [] strings=SerialPortOpenSDK.getInstance().getDevices();
            Log.e("locker", strings.toString()+"---");
            if (strings!=null&&strings.length>0){

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
