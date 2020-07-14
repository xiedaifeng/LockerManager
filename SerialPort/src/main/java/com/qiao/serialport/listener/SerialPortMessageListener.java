package com.qiao.serialport.listener;

public interface SerialPortMessageListener {
    public void onMessage(int error,String errorMessage,byte[] data)throws Exception;
}
