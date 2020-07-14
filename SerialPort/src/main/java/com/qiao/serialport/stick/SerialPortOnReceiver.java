package com.qiao.serialport.stick;

import com.qiao.serialport.service.ComBean;

public interface SerialPortOnReceiver {


    public void onDataReceived(ComBean paramComBean)throws Exception;

}
