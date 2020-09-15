package com.locker.manager.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.locker.manager.R;
import com.locker.manager.adapter.TestAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.command.CommandProtocol;
import com.locker.manager.dialog.SerialPopwindow;
import com.locker.manager.task.LockerManagerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseUrlView implements SerialPortMessageListener {

    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mBoxNo;

    @Override
    protected int getView() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {

        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 5));
        TestAdapter adapter = new TestAdapter(getCtx(),20);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemCallBack(new OnItemCallBack<String>() {
            @Override
            public void onItemClick(int position, String boxno, int... i) {
                mBoxNo = boxno;
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
            }
        });

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
        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
        if (CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()) {
            final String state = commandProtocol.getState();
            LogUtils.e("COMMAND_OPEN");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.equals("00", state)) {
                        ToastUtil.showLongToast("对应的格口" + mBoxNo + "打开成功");
                    } else if (TextUtils.equals("01", state)) {
                        ToastUtil.showLongToast("对应的格口" + mBoxNo + "打开失败");
                    } else {
                        ToastUtil.showLongToast("未知状态：" + new String(commandProtocol.getBytes()));
                    }
                }
            });
        }
    }
}
