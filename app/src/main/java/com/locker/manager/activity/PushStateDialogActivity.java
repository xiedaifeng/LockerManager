package com.locker.manager.activity;


import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.http_lib.utils.UserCacheHelper;
import com.locker.manager.R;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.CommandProtocol;
import com.locker.manager.dialog.CallPhoneDialog;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class PushStateDialogActivity extends BaseUrlView implements SerialPortMessageListener {


    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private String mBoxNo;

    @Override
    protected int getView() {
        return R.layout.activity_push_state;
    }

    @Override
    public void init() {

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6);   //高度设置为屏幕的1.0---去除titlebar高度
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);

        // 1 为打开失败  2 为其他情况
        int state = getIntent().getIntExtra(Constant.OpenBoxStateKey,1);
        mBoxNo = getIntent().getStringExtra(Constant.OpenBoxContentKey);

        if(state == 1){
            tvTip.setText("对应的格口:" + mBoxNo + "打开失败");
        } else if(state == 2){
            tvTip.setText("未知状态:" + mBoxNo + "打开失败");
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

    @OnClick({R.id.tv_exit, R.id.tv_cancel,R.id.tv_hot_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:
                if(TextUtils.isEmpty(mBoxNo)){
                    return;
                }
                try {
                    SerialPortOpenSDK.getInstance().send(
                            new CommandProtocol.Builder()
                                    .setCommand(CommandProtocol.COMMAND_OPEN)
                                    .setCommandChannel(Integer.toHexString(Integer.parseInt(mBoxNo)))
                                    .builder()
                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_cancel:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_hot_phone:
                String hotPhone = UserCacheHelper.getHotPhone();
                if(TextUtils.isEmpty(hotPhone)){
                    hotPhone = "4001172518";
                }
                CallPhoneDialog dialog = new CallPhoneDialog(getCtx(),hotPhone);
                dialog.show();
                break;
        }
    }

    @Override
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
        if (CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()) {
            final String state = commandProtocol.getState();
            LogUtils.e("COMMAND_OPEN");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.equals("00", state)) {
                        ToastUtil.showLongToast("对应的格口" + mBoxNo + "打开成功");
                        ViewManager.getInstance().finishView();
                    } else if (TextUtils.equals("01", state)) {
                        ToastUtil.showLongToast("对应的格口" + mBoxNo + "打开失败");
                    } else {
                        if(commandProtocol.getBytes() == null){
                            ToastUtil.showLongToast("未知状态：串口异常请联系客服");
                        } else {
                            ToastUtil.showLongToast("未知状态：串口异常请联系客服 " + new String(commandProtocol.getBytes()));
                        }
                    }
                }
            });
        }
    }
}
