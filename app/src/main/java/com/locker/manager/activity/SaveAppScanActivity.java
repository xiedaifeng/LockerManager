package com.locker.manager.activity;


import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.utils.DateUtils;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class SaveAppScanActivity extends BaseUrlView implements SerialPortMessageListener {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;


    @Override
    protected int getView() {
        return R.layout.activity_save_app_scan;
    }

    @Override
    public void init() {
//        ivLeft.setVisibility(View.GONE);

//        try {
//            mInstance = SerialPortOpenSDK.getInstance();
//            mInstance.setSerialPort("/dev/ttyS1",9600,1,8,0,0,0);
//            SerialPortOpenSDK.getInstance().initialize(getCtx());
//            String[] devices = SerialPortOpenSDK.getInstance().getDevices();
//            for(String str:devices){
//                Log.e("devices", str);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @OnClick({R.id.iv_left, R.id.tv_hand_save,R.id.tv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                try {

                    SerialPortOpenSDK.getInstance().send( new CommandProtocol.Builder().setCommand("21").setCommandChannel("01").builder().getBytes());

                    SystemClock.sleep(1000);
                   SerialPortOpenSDK.getInstance().send( new CommandProtocol.Builder().setCommand("22").setCommandChannel("01").builder().getBytes());
                    SystemClock.sleep(1000);
                    SerialPortOpenSDK.getInstance().send( new CommandProtocol.Builder().setCommand("25").setCommandChannel("01").builder().getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_hand_save:
                skipActivity(SaveHandActivity.class);
                break;
            case R.id.tv_help:
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialPortOpenSDK.getInstance().regirster(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SerialPortOpenSDK.getInstance().unregirster(this);
    }

    @Override
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
      CommandProtocol commandProtocol=new CommandProtocol.Builder().setBytes(data).parseMessage();
        Log.e("--",commandProtocol.toString());
    }
}
