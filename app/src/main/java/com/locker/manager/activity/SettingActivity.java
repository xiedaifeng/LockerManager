package com.locker.manager.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.dialog.SerialPopwindow;
import com.locker.manager.task.LockerManagerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.qiao.serialport.listener.SerianPortSDKListener;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseUrlView implements SerialPortMessageListener {

    @BindView(R.id.tv_input)
    TextView tvInput;

    @Override
    protected int getView() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.iv_left,R.id.tv_show,R.id.tv_connect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_show:
                new SerialPopwindow().show(getCtx(),tvInput);
                break;
            case R.id.tv_connect:
                if(isFastClick()){
                    return;
                }
                String serial = tvInput.getText().toString();
                if(TextUtils.isEmpty(serial)){
                    ToastUtil.showLongToast("请先选择相应的串口号");
                    return;
                }

                TaskDispatcher.createInstance().addTask(new LockerManagerTask(serial)).start();
                SerialPortOpenSDK.getInstance().setListener(new SerianPortSDKListener() {
                    @Override
                    public void initListener(int error, String errorMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showLongToast(errorMessage);
                            }
                        });
                    }
                });
                break;
        }
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

    @Override
    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {

    }
}
