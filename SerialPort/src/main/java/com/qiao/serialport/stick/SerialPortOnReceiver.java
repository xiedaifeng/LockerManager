package com.qiao.serialport.stick;

import com.qiao.serialport.bean.ComBean;

public interface SerialPortOnReceiver {


    public void onDataReceived(ComBean paramComBean)throws Exception;

}
