package com.locker.manager.service;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.bean.UpdatePushRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.PushStateDialogActivity;
import com.locker.manager.activity.sender.SenderDeliverSuccessActivity;
import com.locker.manager.activity.user.UserPickUpSuccessActivity;
import com.locker.manager.app.Constant;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.command.CommandProtocol;
import com.locker.manager.event.NetworkEvent;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class LockerIntentService extends GTIntentService implements SerialPortMessageListener {

    private String boxno;

    public static boolean isDeviceOnLine = false;
    private String mOrder_id;
    private String mType;

    private String mPost_no;

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
        LogUtils.e("onReceiveMessageData:" + payload);
        JSONObject object = JSON.parseObject(payload);
        mOrder_id = object.getString("order_id");
        mType = object.getString("type");
        String device_id = object.getString("device_id");

        if (!TextUtils.equals(device_id, PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context)) || !isDeviceOnLine) {
            return;
        }

        GetOrderInfoRequestBean requestBean = new GetOrderInfoRequestBean();
        requestBean.order_id = mOrder_id;
        HttpClient.request(requestBean, false, new IHttpCallBack() {

            @Override
            public void success(ResponseBean responseBean) {
                try {
                    if (TextUtils.isEmpty(responseBean.getData())) {
                        return;
                    }

                    updatePushState(mOrder_id);

                    OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                    boxno = orderInfoBean.getBoxno();
                    mPost_no = orderInfoBean.getPost_no();
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(Integer.toHexString(Integer.parseInt(boxno)))
                                    .builder()
                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LockerApplication.mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showLongToast("收到平台的推送消息，打开格口：" + boxno);
                    }
                });

//                if (TextUtils.equals("qu", mType)) { //取件推送
//                    if (TextUtils.isEmpty(mPost_no) && TextUtils.equals(mOrder_id, LockerApplication.sQuOrderId)) {
//                        ViewManager.getInstance().finishOthersView(HomeActivity.class);
//                        Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra(Constant.OrderInfoKey, mOrder_id);
//                        LockerApplication.getApplication().startActivity(intent);
//                        LockerApplication.sQuOrderId = null;
//                    } else {
//
//                    }
//                } else if (TextUtils.equals("cun", mType) && TextUtils.equals(mOrder_id, LockerApplication.sOrderId)) {                                                                    //存件推送
//                    ViewManager.getInstance().finishOthersView(HomeActivity.class);
//                    Intent intent = new Intent(LockerApplication.getApplication(), SenderDeliverSuccessActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Constant.OrderInfoKey, mOrder_id);
//                    LockerApplication.getApplication().startActivity(intent);
//                    LockerApplication.sOrderId = null;
//                } else if (TextUtils.equals("tui", mType) && TextUtils.equals(mOrder_id, LockerApplication.sTuiOrderId)) {
//                    Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Constant.OrderInfoKey, mOrder_id);
//                    LockerApplication.getApplication().startActivity(intent);
//                    LockerApplication.sTuiOrderId = null;
//                }
//                ToastUtil.showLongToast("格口号" + boxno + "已打开");
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
        EventBus.getDefault().post(new NetworkEvent(b));
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
        LogUtils.e("onCreate");
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
            String state = commandProtocol.getState();
            LogUtils.e("COMMAND_OPEN");
            String msg = "";

            onOpenBoxSuccessToActivity();

            if (TextUtils.equals("00", state)) {
                msg = "对应的格口:" + boxno + "打开成功";
//                onOpenBoxSuccessToActivity();
            } else if (TextUtils.equals("01", state)) {
                msg = "对应的格口:" + boxno + "打开失败";
//                onOpenBoxFailToActivity(1, boxno);
            } else {
                msg = "未知状态:" + boxno + "打开失败";
//                onOpenBoxFailToActivity(2, boxno);
            }
        }
    }

    private void updatePushState(String order_id) {
        UpdatePushRequestBean updatePushRequestBean = new UpdatePushRequestBean();
        updatePushRequestBean.order_id = order_id;
        HttpClient.request(updatePushRequestBean, false, new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
            }

            @Override
            public void failed(ResponseBean responseBean) {
            }
        });
    }

    private void onOpenBoxSuccessToActivity() {
        if (TextUtils.isEmpty(mOrder_id)) {
            return;
        }
        if (TextUtils.equals("qu", mType)) { //取件推送
            if (TextUtils.isEmpty(mPost_no) && TextUtils.equals(mOrder_id, LockerApplication.sQuOrderId)) {
                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.OrderInfoKey, mOrder_id);
                LockerApplication.getApplication().startActivity(intent);
                LockerApplication.sQuOrderId = null;
            } else {

            }
        } else if (TextUtils.equals("cun", mType) && TextUtils.equals(mOrder_id, LockerApplication.sOrderId)) {                                                                    //存件推送
            ViewManager.getInstance().finishOthersView(HomeActivity.class);
            Intent intent = new Intent(LockerApplication.getApplication(), SenderDeliverSuccessActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.OrderInfoKey, mOrder_id);
            LockerApplication.getApplication().startActivity(intent);
            LockerApplication.sOrderId = null;
        } else if (TextUtils.equals("tui", mType) && TextUtils.equals(mOrder_id, LockerApplication.sTuiOrderId)) {
            Intent intent = new Intent(LockerApplication.getApplication(), UserPickUpSuccessActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.OrderInfoKey, mOrder_id);
            LockerApplication.getApplication().startActivity(intent);
            LockerApplication.sTuiOrderId = null;
        }
        LockerApplication.mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showLongToast("格口号" + boxno + "已打开");
            }
        });
    }


    private void onOpenBoxFailToActivity(int state, String boxNo) {
        Intent intent = new Intent(LockerApplication.getApplication(), PushStateDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.OpenBoxStateKey, state);
        intent.putExtra(Constant.OpenBoxContentKey, boxNo);
        LockerApplication.getApplication().startActivity(intent);
    }

}
