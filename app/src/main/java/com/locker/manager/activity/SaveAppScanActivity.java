package com.locker.manager.activity;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.Command;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.qiao.serialport.utils.ByteUtil;
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
        ivLeft.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_left, R.id.tv_hand_save,R.id.tv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:

                try {

                    SerialPortOpenSDK.getInstance().send(
                            new Command.Builder().setCommand(Command.Consts.COMMAND_WRITE).setCommmandChannel(Command.Consts.COMMAND_1).build().getBytes());

                    SystemClock.sleep(500);


                    SerialPortOpenSDK.getInstance().send(new Command.Builder().setCommand(Command.Consts.COMMAND_READ).setCommmandChannel(Command.Consts.COMMAND_1).build().getBytes());

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
        SerialPortOpenSDK.getInstance().regirster(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        SerialPortOpenSDK.getInstance().unregirster(this);
        super.onPause();
    }

    @Override
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {

        /**
         * 数据解析
         */
        Command command=new Command.Builder().setBytes(data).parse();
        Log.e(command.getCommmandChannel(),command.getCommandState());


    }
}
