package com.locker.manager.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http_lib.bean.CreateDeviceQrcodeRequestBean;
import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.CommonGlideUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseUrlView implements SerialPortMessageListener {

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
        setCurrentTime(tvTitle,System.currentTimeMillis());

        mPresenter.createDeviceQrcode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));

        try {
//            new CommandProtocol.Builder().setCommand(CommandProtocol.COMMAND_OPEN).setCommandChannel(5).builder();
            SerialPortOpenSDK.getInstance().send(
                    new CommandProtocol.Builder()
                            .setCommand(CommandProtocol.COMMAND_SELECT_BOX_STATE)
                            .setCommandChannel(1)
                            .builder()
                            .getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),"12");
//
//        mPresenter.getDeviceInfo(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));
//
//        mPresenter.getDeviceBoxInfo(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),"1");
//
//        UpdateDeviceBoxStatusRequestBean requestBean = new UpdateDeviceBoxStatusRequestBean();
//        requestBean.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx());
//        requestBean.boxno = "1";
//        requestBean.openstatus = "open";
//        mPresenter.updateDeviceBoxStatus(requestBean);

//        CreateOrderRequestBean requestBean1 = new CreateOrderRequestBean();
//        requestBean1.cun_phone = "15757829477";
//        requestBean1.qu_phone = "15757829477";
//        requestBean1.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx());
//        requestBean1.boxsize = "big";
//        mPresenter.createOrder(requestBean1);
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
//                    new CommandProtocol.Builder().setCommand(CommandProtocol.COMMAND_OPEN).setCommandChannel(5).builder();
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(5)
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
//        Command command=new Command.Builder().setBytes(data).parse();
//        if (command.getError()==0x01&&command.getCommandFixed_1().equals("30")){
//           String channel= command.getCommmandChannel();
//           String state=command.getCommandState();
//        }

        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
        if(CommandProtocol.COMMAND_SELECT_BOX_STATE == commandProtocol.getCommand()){
            int boxNum = commandProtocol.getData().size();
            mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),boxNum+"");
        }
        if(CommandProtocol.COMMAND_OPEN == commandProtocol.getCommand()){

        }
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == CreateDeviceQrcodeRequestBean.class){
                CommonGlideUtils.showImageView(getCtx(),responseBean.getData(),ivQrcode);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
