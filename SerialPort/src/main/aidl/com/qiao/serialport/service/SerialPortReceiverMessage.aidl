// SerialPortReceiverMessage.aidl
package com.qiao.serialport.service;

// Declare any non-default types here with import statements

interface SerialPortReceiverMessage {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void onSerialPortReceiverByte(in byte [] bs,int len);

   void onSerialPortReceiverHexString(String uartdata);
}
