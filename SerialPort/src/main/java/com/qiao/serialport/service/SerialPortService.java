package com.qiao.serialport.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.qiao.serialport.SerialHelper;
import com.qiao.serialport.bean.ComBean;
import com.qiao.serialport.stick.SerialPortOnReceiver;
import com.qiao.serialport.utils.Consts;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;

public class SerialPortService extends Service{

    private AtomicBoolean serviceStop = new AtomicBoolean(false);

    private RemoteCallbackList<SerialPortReceiverMessage> listenerList = new RemoteCallbackList<>();

    private SerialHelper serialHelper=null;

    IBinder iBinder=new SerialPortSendMessage.Stub() {
        @Override
        public void setSerilaPort(String path, int baudrate, int stopBits, int dataBits, int parity, int flowCon, int flags) throws RemoteException {
            if (serialHelper==null){
                serialHelper=new SerialHelper();
            }
            serialHelper.setListener(serialPortOnReceiver);
            serialHelper.setsPort(path);
            serialHelper.setiBaudRate(baudrate);
            serialHelper.setStopBits(stopBits);
            serialHelper.setDataBits(dataBits);
            serialHelper.setParity(parity);
            serialHelper.setFlowCon(flowCon);
            serialHelper.setFlags(flags);
        }

        @Override
        public boolean openSerilaPort() throws RemoteException {
            if (serialHelper!=null){
                try {
                     serialHelper.open();
                     return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public boolean closeSerilaPort() throws RemoteException {
            if (serialHelper!=null){
                try {
                    serialHelper.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public void sendUartData(byte[] bs) throws RemoteException {
            if (serialHelper!=null){
                try {
                    serialHelper.send(bs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void sendHexUartData(String uartdata) throws RemoteException {
            if (serialHelper!=null){
                try {
                    serialHelper.sendHex(uartdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void registerReceiveListener(SerialPortReceiverMessage messageReceiver) throws RemoteException {
            listenerList.register(messageReceiver);
        }

        @Override
        public void unregisterReceiveListener(SerialPortReceiverMessage messageReceiver) throws RemoteException {
            listenerList.unregister(messageReceiver);
        }
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (packageName == null || !packageName.startsWith(Consts.Utils.packageName)) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (checkCallingOrSelfPermission(Consts.Utils.packageName+Consts.Utils.PERMISSION) == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return iBinder;
    }
    SerialPortOnReceiver serialPortOnReceiver=new SerialPortOnReceiver() {
        @Override
        public void onDataReceived(ComBean comBean) throws Exception {
            final int listenerCount = listenerList.beginBroadcast();
            for (int i = 0; i < listenerCount; i++) {
                SerialPortReceiverMessage messageReceiver = listenerList.getBroadcastItem(i);
                if (messageReceiver != null) {
                    try {
                        messageReceiver.onSerialPortReceiverByte(comBean.bRec,comBean.bRec.length);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            listenerList.finishBroadcast();
        }
    };

}
