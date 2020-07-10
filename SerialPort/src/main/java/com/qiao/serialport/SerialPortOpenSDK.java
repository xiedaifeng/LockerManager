package com.qiao.serialport;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.qiao.serialport.listener.SerialPortMessageListener;
import com.qiao.serialport.listener.SerianPortSDKListener;
import com.qiao.serialport.service.ComBean;
import com.qiao.serialport.service.SerialPortReceiverMessage;
import com.qiao.serialport.service.SerialPortSendMessage;
import com.qiao.serialport.utils.Consts;
import com.tencent.mmkv.MMKV;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPortFinder;

public class SerialPortOpenSDK {

    private static volatile SerialPortOpenSDK instance=null;


    private SerialPortSendMessage serialPortSendMessage=null;
    private MyServiceConnection serviceConnection=null;

    public static SerialPortOpenSDK getInstance() {
        if (instance==null){
            synchronized (SerialPortOpenSDK.class){
                if (instance==null){
                    instance=new SerialPortOpenSDK();
                }
            }
        }
        return instance;
    }
    private Context mContext=null;
    private SerianPortSDKListener listener=null;
    private List<SerialPortMessageListener> listenerList=new ArrayList<>();

    public void setListener(SerianPortSDKListener listener) {
        this.listener = listener;
    }

    public void initialize(Context mContex)throws Exception{
        this.mContext=mContex;
        MMKV.initialize(mContext);
        Consts.Utils.packageName=mContext.getPackageName();
        Intent intent=new Intent();
        intent.setAction(mContext.getPackageName()+Consts.Utils.ACTION);
        intent.setPackage(mContext.getPackageName());
        if (serviceConnection==null){
            serviceConnection=new MyServiceConnection();
        }
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    public void regirster(SerialPortMessageListener listener){
        if (listenerList!=null&&!listenerList.contains(listener)){
            synchronized (SerialPortOpenSDK.class){
                listenerList.add(listener);
            }

        }
    }
    public void unRegirster(SerialPortMessageListener listener){
        if (listenerList!=null&&listenerList.contains(listener)){
            synchronized (SerialPortOpenSDK.class){
                listenerList.remove(listener);
            }
        }
    }

    /**
     * 获取所有串口相等路径
     * @return
     */
    public String [] getDevices(){
        SerialPortFinder serialPortFinder=new SerialPortFinder();
        return serialPortFinder.getAllDevices();
    }

    /**
     * 获取所有串口的全路径
     * @return
     */
    public String [] getDevicesPath(){
        SerialPortFinder serialPortFinder=new SerialPortFinder();
        return serialPortFinder.getAllDevicesPath();
    }


    /**
     * 设置串口属性
     * @param path
     * @param baudrate
     * @param stopBits
     * @param dataBits
     * @param parity
     * @param flowCon
     * @param flags
     */
    private String path;
    private int baudrate;
    private int stopBits;
    private int dataBits;
    private int parity;
    private int flowCon;
    private int flags;

    public void setSerialPort(String path,
                              int baudrate,
                              int stopBits,
                              int dataBits,
                              int parity,
                              int flowCon,
                              int flags){
        this.path=path;
        this.baudrate=baudrate;
        this.stopBits=stopBits;
        this.dataBits=dataBits;
        this.parity=parity;
        this.flowCon=flowCon;
        this.flags=flags;
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putString(Consts.Utils.PATH, path);
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putInt(Consts.Utils.BAUDRATE, baudrate);
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putInt(Consts.Utils.STOPBITS, stopBits);
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putInt(Consts.Utils.DATABITS, dataBits);
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putInt(Consts.Utils.FLOWCON, flowCon);
        MMKV.mmkvWithID("serialport", MMKV.MULTI_PROCESS_MODE).putInt(Consts.Utils.FLAGS, flags);
        MMKV.defaultMMKV().putString(Consts.Utils.PATH, path);
        MMKV.defaultMMKV().putInt(Consts.Utils.BAUDRATE, baudrate);
        MMKV.defaultMMKV().putInt(Consts.Utils.STOPBITS, stopBits);
        MMKV.defaultMMKV().putInt(Consts.Utils.DATABITS, dataBits);
        MMKV.defaultMMKV().putInt(Consts.Utils.FLOWCON, flowCon);
        MMKV.defaultMMKV().putInt(Consts.Utils.FLAGS, flags);
        if (serialPortSendMessage!=null){
            try {
                serialPortSendMessage.setSerilaPort(
                        MMKV.defaultMMKV().getString(Consts.Utils.PATH, "dev/ttyS1"),
                        MMKV.defaultMMKV().getInt(Consts.Utils.BAUDRATE,9600),
                        MMKV.defaultMMKV().getInt(Consts.Utils.STOPBITS,1),
                        MMKV.defaultMMKV().getInt(Consts.Utils.DATABITS,8),
                        MMKV.defaultMMKV().getInt(Consts.Utils.PARITY,0),
                        MMKV.defaultMMKV().getInt(Consts.Utils.FLOWCON,0),
                        MMKV.defaultMMKV().getInt(Consts.Utils.FLAGS,0));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean openSerialPort() throws Exception{
        if (serialPortSendMessage!=null){
            if (serialPortSendMessage.isOpen()){
                return true;
            }
           return serialPortSendMessage.openSerilaPort();
        }
        return false;
    }
    public boolean closeSerialPort() throws Exception{
        if (serialPortSendMessage!=null){
            if (!serialPortSendMessage.isOpen()){
                return true;
            }
            return serialPortSendMessage.closeSerilaPort();
        }
        return false;
    }


    public void send(byte[] bs)throws Exception{
        if (serialPortSendMessage!=null){
            if (serialPortSendMessage.isOpen()){
                serialPortSendMessage.sendUartData(bs);
                return;
            }
            if (listenerList!=null){
                for (SerialPortMessageListener listener: listenerList){
                    listener.onMessage(0x0B, "串口打开失败", "");
                }
            }
            return;
        }
        if (listenerList!=null){
            for (SerialPortMessageListener listener: listenerList){
                listener.onMessage(0x0A, "未初始化", "");
            }
        }
    }
    public void send(String hexString)throws Exception{
        if (serialPortSendMessage!=null){
            if (serialPortSendMessage.isOpen()){
                serialPortSendMessage.sendHexUartData(hexString);
                return;
            }
            if (listenerList!=null){
                for (SerialPortMessageListener listener: listenerList){
                    listener.onMessage(0x0B, "串口未打开", "");
                }
            }
            return;
        }
        if (listenerList!=null){
            for (SerialPortMessageListener listener: listenerList){
                listener.onMessage(0x0A, "未初始化", "");
            }
        }
    }


    SerialPortReceiverMessage messageReceiver=new SerialPortReceiverMessage.Stub() {


        @Override
        public void onSerialPortReceiver(ComBean bean) throws RemoteException {
            if (listenerList!=null){
                for (SerialPortMessageListener listener: listenerList){
                    try {
                        listener.onMessage(0x01, "success", new String(bean.bRec,"UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    };

    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (serialPortSendMessage != null) {
                serialPortSendMessage.asBinder().unlinkToDeath(this, 0);
                serialPortSendMessage = null;
            }
            Intent intent=new Intent();
            intent.setPackage(mContext.getPackageName());
            intent.setAction(mContext.getPackageName()+Consts.Utils.ACTION);
            mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {

                serialPortSendMessage = SerialPortSendMessage.Stub.asInterface(service);
                //设置Binder死亡监听
                serialPortSendMessage.asBinder().linkToDeath(deathRecipient, 0);
                //把接收消息的回调接口注册到服务端
                serialPortSendMessage.registerReceiveListener(messageReceiver);
                if (listener!=null){
                    listener.initListener(0x01, "初始化成功");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (listener!=null){
                listener.initListener(0x02, "远端服务断开连接");
            }
        }

    }
}


