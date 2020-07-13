package com.locker.manager.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.utils.DateUtils;
import com.yidao.module_lib.utils.LogUtils;

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
                    SerialPortOpenSDK.getInstance().regirster(this);
                    SerialPortOpenSDK.getInstance().send("57 4B 4C 59 08 01 86 86".replace(" ",""));
                    SerialPortOpenSDK.getInstance().send("0B0040FF7A000B0000004933C3A36B".replace(" ",""));
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
    public void onMessage(int error, String errorMessage, String data) throws Exception {
        ToastUtil.showShortToast(errorMessage);
    }
}
