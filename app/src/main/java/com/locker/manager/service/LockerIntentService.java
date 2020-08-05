package com.locker.manager.service;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.locker.manager.activity.user.UserPickUpSuccessActivity;
import com.locker.manager.app.Constant;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

public class LockerIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int i) {
        LogUtils.e("onReceiveServicePid:"+i);

    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String s) {
        LogUtils.e("onReceiveClientId:"+s);
        PushManager.getInstance().bindAlias(context, PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context).replace(":",""));
    }

    // 处理透传消息{"order_id":"xx"}
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String content = gtTransmitMessage.getMessageId()+gtTransmitMessage.getPayloadId()+gtTransmitMessage.getTaskId();
        String payload = new String(gtTransmitMessage.getPayload());
        LogUtils.e("onReceiveMessageData:"+content+","+payload);
        JSONObject object = JSON.parseObject(payload);
        String order_id = object.getString("order_id");

        GetOrderInfoRequestBean requestBean = new GetOrderInfoRequestBean();
        requestBean.order_id = order_id;
        HttpClient.request(requestBean, false, new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                String boxno = orderInfoBean.getBoxno();
                try {
                    new CommandProtocol.Builder().setCommand(CommandProtocol.COMMAND_OPEN).setCommandChannel(boxno).builder();
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(boxno)
                                    .builder()
                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtil.showLongToast("格口号"+boxno+"已打开");
            }
            @Override
            public void failed(ResponseBean responseBean) {
                ToastUtil.showShortToast(responseBean.getMessage());
            }
        });

//        Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.OrderInfoKey,order_id);
//        LockerApplication.getApplication().startActivity(intent);
    }
    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        LogUtils.e("onReceiveOnlineState:"+b);
    }
    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        LogUtils.e("onReceiveCommandResult:"+gtCmdMessage.toString());
    }
    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e("onNotificationMessageArrived:"+gtNotificationMessage.toString());
    }
    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtils.e("onNotificationMessageClicked:"+gtNotificationMessage.toString());
    }
}
