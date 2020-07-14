// SerialPortReceiverMessage.aidl
package com.qiao.serialport.service;

import com.qiao.serialport.service.ComBean;

interface SerialPortReceiverMessage {
   void onSerialPortReceiver(in ComBean bean);
}
