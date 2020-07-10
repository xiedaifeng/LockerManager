// SerialPortSendMessage.aidl
package com.qiao.serialport.service;
import com.qiao.serialport.service.SerialPortReceiverMessage;
// Declare any non-default types here with import statements

interface SerialPortSendMessage {
    /**
        * 串口有五个重要的参数：串口设备名，波特率，检验位，数据位，停止位
        * 其中检验位一般默认位NONE,数据位一般默认为8，停止位默认为1
        *
        * @param path     串口设备的据对路径
        * @param baudrate {@link BAUDRATE}波特率
        * @param stopBits {@link STOPB}停止位
        * @param dataBits {@link DATAB}数据位
        * @param parity   {@link PARITY}校验位
        * @param flowCon  {@link FLOWCON}流控
        * @param flags    O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
        * @return
        */


    void setSerilaPort(String path, int baudrate, int stopBits, int dataBits, int parity, int flowCon, int flags);
    boolean openSerilaPort();
    boolean closeSerilaPort();
    boolean isOpen();
    void sendUartData(in byte[] bs);
    void sendHexUartData(String uartdata);
    void registerReceiveListener(in SerialPortReceiverMessage messageReceiver);
    void unregisterReceiveListener(in SerialPortReceiverMessage messageReceiver);
}
