package com.locker.manager.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.sender.SenderDeliverSuccessActivity;
import com.locker.manager.activity.user.UserPickUpSuccessActivity;
import com.locker.manager.app.Constant;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

public class LockerIntentService extends GTIntentService implements SerialPortMessageListener {

    private String boxno;

    public static boolean isDeviceOnLine = false;

    @Override
    public void onReceiveServicePid(Context context, int i) {
        LogUtils.e("onReceiveServicePid:" + i);
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String cid) {
        LogUtils.e("onReceiveClientId:" + cid);
        CreateDeviceRequestBean requestBean = new CreateDeviceRequestBean();
        requestBean.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getApplicationContext());
        requestBean.getui_cid = cid;
        LogUtils.e("CreateDeviceRequestBean:" + requestBean.device_id);
        HttpClient.request(requestBean, false, new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
                LogUtils.e("success:createDevice");
            }

            @Override
            public void failed(ResponseBean responseBean) {
                LogUtils.e("failed:createDevice");
            }
        });

        PushManager.getInstance().bindAlias(context, PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context));
    }

    // 处理透传消息{"order_id":"xx"}
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String payload = new String(gtTransmitMessage.getPayload());
        LogUtils.e("onReceiveMessageData:"+ payload);
        JSONObject object = JSON.parseObject(payload);
        String order_id = object.getString("order_id");
        String type = object.getString("type");
        String device_id = object.getString("device_id");

        if(!TextUtils.equals(device_id,PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context))){
            return;
        }

        GetOrderInfoRequestBean requestBean = new GetOrderInfoRequestBean();
        requestBean.order_id = order_id;
        HttpClient.request(requestBean, false, new IHttpCallBack() {

            @Override
            public void success(ResponseBean responseBean) {
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                boxno = orderInfoBean.getBoxno();
                try {
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(Integer.toHexString(Integer.parseInt(boxno)))
                                    .builder()
                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String post_no = orderInfoBean.getPost_no();
                if (TextUtils.equals("qu", type)) { //取件推送
                    if(TextUtils.isEmpty(post_no) && TextUtils.equals(order_id,LockerApplication.sQuOrderId)){
                        ViewManager.getInstance().finishOthersView(HomeActivity.class);
                        Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constant.OrderInfoKey, order_id);
                        LockerApplication.getApplication().startActivity(intent);
                        LockerApplication.sQuOrderId = null;
                    } else {

                    }
                } else if(TextUtils.equals("cun",type) && TextUtils.equals(order_id,LockerApplication.sOrderId)){                                                                    //存件推送
                    ViewManager.getInstance().finishOthersView(HomeActivity.class);
                    Intent intent = new Intent(LockerApplication.getApplication(), SenderDeliverSuccessActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.OrderInfoKey, order_id);
                    LockerApplication.getApplication().startActivity(intent);
                    LockerApplication.sOrderId = null;
                } else if(TextUtils.equals("tui",type) && TextUtils.equals(order_id,LockerApplication.sTuiOrderId)){
                    Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.OrderInfoKey, order_id);
                    LockerApplication.getApplication().startActivity(intent);
                    LockerApplication.sTuiOrderId = null;
                }
                ToastUtil.showLongToast("格口号" + boxno + "已打开");
            }

            @Override
            public void failed(ResponseBean responseBean) {
                ToastUtil.showShortToast(responseBean.getMessage());
            }
        });
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        LogUtils.e("onReceiveOnlineState:" + b);
        isDeviceOnLine = b;

        if(!b){
//            ToastUtil.showLongToast("当前网络已断开，请先检查网络状况");
        }
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        LogUtils.e("onReceiveCommandResult:" + gtCmdMessage.toString());
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e("onNotificationMessageArrived:" + gtNotificationMessage.toString());
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e("onNotificationMessageClicked:" + gtNotificationMessage.toString());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SerialPortOpenSDK.getInstance().regirster(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");
        SerialPortOpenSDK.getInstance().unregirster(this);
    }

    @Override
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
        if (CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()) {
            LogUtils.e("COMMAND_OPEN");
//            if (!TextUtils.isEmpty(boxno)) {
//                UpdateDeviceBoxStatusRequestBean requestBean = new UpdateDeviceBoxStatusRequestBean();
//                requestBean.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(LockerApplication.getApplication());
//                requestBean.boxno = boxno;
//                requestBean.openstatus = "open";
//                HttpClient.request(requestBean, false, new IHttpCallBack() {
//                    @Override
//                    public void success(ResponseBean responseBean) {
//                    }
//
//                    @Override
//                    public void failed(ResponseBean responseBean) {
//                    }
//                });
//            }
        }
    }
}
