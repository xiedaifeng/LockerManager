package com.locker.manager.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.Command;
import com.locker.manager.command.CommandProtocol;
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        SerialPortOpenSDK.getInstance().unregirster(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialPortOpenSDK.getInstance().regirster(this);
    }

    @OnClick({R.id.iv_left, R.id.tv_hand_save,R.id.tv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                try {
                    new CommandProtocol.Builder().setCommand(CommandProtocol.COMMAND_OPEN).setCommandChannel(5).builder();
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(1)
                                    .builder()
                                    .getBytes());
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
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
        Command command=new Command.Builder().setBytes(data).parse();
        if (command.getError()==0x01&&command.getCommandFixed_1().equals("30")){
           String channel= command.getCommmandChannel();
           String state=command.getCommandState();
        }

    }
}
