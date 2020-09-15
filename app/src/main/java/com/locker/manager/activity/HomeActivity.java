package com.locker.manager.activity;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.bean.CreateDeviceQrcodeRequestBean;
import com.example.http_lib.bean.DeviceInfoRequestBean;
import com.example.http_lib.bean.HotPhoneRequestBean;
import com.example.http_lib.bean.SystemNoticeRequestBean;
import com.example.http_lib.response.NoticeBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.event.NetworkEvent;
import com.locker.manager.manager.VibratorManager;
import com.squareup.picasso.Picasso;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.PermissionManager;
import com.yidao.module_lib.utils.PhoneInfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseUrlView {

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
    @BindView(R.id.iv_place_qrcode)
    ImageView ivPlaceQrcode;


    @Override
    protected int getView() {
        return R.layout.activity_save_app_scan;
    }

    @Override
    public void init() {

        PermissionManager.getInstance().requestPermissions(this);
        PermissionManager.getInstance().setIPermissionLiatener(new PermissionManager.IPermissionListener() {
            @Override
            public void getPermissionSuccess() {

            }
        });


        setCurrentTime(tvTitle, System.currentTimeMillis());

        ivLeft.setVisibility(View.GONE);

        initNetData();


        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    private void initNetData(){
        String mac = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx());
        tvMac.setText(mac);
        mPresenter.createDeviceQrcode(mac);
        mPresenter.hotPhone();
        mPresenter.getSystemNotice();
        mPresenter.getDeviceInfo(mac);
    }


    @OnClick({R.id.iv_left, R.id.tv_hand_save, R.id.tv_help,R.id.iv_place_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:


//                TaskDispatcher.createInstance().addTask(new LockerManagerTask("/dev/ttyS3")).start();

//                skipActivity(SettingActivity.class);

//                try {
//                    SerialPortOpenSDK.getInstance().send(
//                            new CommandProtocol.Builder()
//                                    .setCommand(CommandProtocol.COMMAND_OPEN)
//                                    .setCommandChannel(1)
//                                    .builder()
//                                    .getBytes());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                CrashReport.testJavaCrash();

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
                if(!isDeviceOnLine()){
                    return;
                }
                skipActivity(SaveHandActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
            case R.id.tv_help:
                skipActivity(SaveHelpActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
            case R.id.iv_place_qrcode:
                mPresenter.createDeviceQrcode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));
                break;
        }
    }

//    @Override
//    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
//        Command command=new Command.Builder().setBytes(data).parse();
//        if (command.getError()==0x01&&command.getCommandFixed_1().equals("30")){
//           String channel= command.getCommmandChannel();
//           String state=command.getCommandState();
//        }
//
//        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
//        if (CommandProtocol.COMMAND_SELECT_DEPOSIT_STATE == commandProtocol.getCommand()) {
//            int boxNum = commandProtocol.getData().size();
//            LogUtils.e("boxNum:" + boxNum);
////            mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), boxNum + "");
//        }
//        if (CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()) {
//            LogUtils.e("COMMAND_OPEN");
//        }
//    }


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
            }
            if(requestCls == DeviceInfoRequestBean.class){
                JSONObject object = JSON.parseObject(responseBean.getData());
                String device_address = object.getString("device_address");
                if(!TextUtils.isEmpty(device_address)){
                    tvMac.setText(device_address);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkMessage(NetworkEvent event) {
        if(event.isOnline){
            initNetData();
        }
    }

}
