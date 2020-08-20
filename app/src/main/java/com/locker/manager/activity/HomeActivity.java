package com.locker.manager.activity;


import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.CreateDeviceQrcodeRequestBean;
import com.example.http_lib.bean.HotPhoneRequestBean;
import com.example.http_lib.bean.SystemNoticeRequestBean;
import com.example.http_lib.response.NoticeBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.CommandNewProtocol;
import com.locker.manager.command.CommandProtocol;
import com.locker.manager.dialog.SaveOverTimeDialog;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.squareup.picasso.Picasso;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseUrlView implements SerialPortMessageListener {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_phone_tip)
    TextView tvPhoneTip;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_mac)
    TextView tvMac;
    @BindView(R.id.filpper)
    ViewFlipper filpper;

    @Override
    protected int getView() {
        return R.layout.activity_save_app_scan;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle, System.currentTimeMillis());

        ivLeft.setVisibility(View.VISIBLE);

        tvMac.setText(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));
        mPresenter.createDeviceQrcode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));
        mPresenter.hotPhone();
        mPresenter.getSystemNotice();
//        mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),   "14");

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SerialPortOpenSDK.getInstance().send(
//                            new CommandProtocol.Builder()
//                                    .setCommand(CommandProtocol.COMMAND_SELECT_BOX_STATE)
//                                    .setCommandChannel(1)
//                                    .builder()
//                                    .getBytes());
//                    setTip("查询指令发送成功");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 500);

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

    @OnClick({R.id.iv_left, R.id.tv_hand_save, R.id.tv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                try {
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(1)
                                    .builder()
                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    SerialPortOpenSDK.getInstance().send(
//                            new CommandProtocol.Builder()
//                                    .setCommand(CommandProtocol.COMMAND_SELECT_BOX_STATE)
//                                    .setCommandChannel(1)
//                                    .builder()
//                                    .getBytes());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                byte[] bytes = new CommandNewProtocol.Builder().setCommand(CommandNewProtocol.COMMAND_OPEN).setCommandChannel(1).builder().getBytes();
//                try {
//                    SerialPortOpenSDK.getInstance().send(bytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                byte[] bytes1 = new CommandNewProtocol.Builder().setCommand(CommandNewProtocol.COMMAND_SELECT_BOX_STATE).setCommandChannel(1).builder().getBytes();
//                try {
//                    SerialPortOpenSDK.getInstance().send(bytes1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

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
        if (CommandProtocol.COMMAND_SELECT_DEPOSIT_STATE == commandProtocol.getCommand()) {
            int boxNum = commandProtocol.getData().size();
            LogUtils.e("boxNum:" + boxNum);
//            mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), boxNum + "");
        }
        if (CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()) {
            LogUtils.e("COMMAND_OPEN");
        }
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == CreateDeviceQrcodeRequestBean.class) {
                Picasso.with(this).load(responseBean.getData().replace("https", "http")).into(ivQrcode);
            }
            if (requestCls == HotPhoneRequestBean.class) {
                UserCacheHelper.setHotPhone(responseBean.getData());
                tvPhoneTip.setText(String.format("客服热线：%s", responseBean.getData()));
            }
            if (requestCls == SystemNoticeRequestBean.class) {
                List<NoticeBean> noticeBeans = JSON.parseArray(responseBean.getData(), NoticeBean.class);

                for(int i=0;i<noticeBeans.size();i++){
                    View view = LayoutInflater.from(getCtx()).inflate(R.layout.item_flipper, null);
                    TextView notice = view.findViewById(R.id.tv_notice);
                    notice.setText(noticeBeans.get(i).getTitle());
                    filpper.addView(view);
                }

//                if (noticeBeans != null && noticeBeans.size() > 0) {
//                    tvNotice.setText(noticeBeans.get(0).getTitle());
//                }
            }
        }
    }
}
