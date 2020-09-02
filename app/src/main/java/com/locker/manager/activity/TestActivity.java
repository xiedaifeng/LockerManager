package com.locker.manager.activity;


import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.locker.manager.R;
import com.locker.manager.adapter.TestAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.command.Command;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class TestActivity extends BaseUrlView implements SerialPortMessageListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mBoxNo;

    @Override
    protected int getView() {
        return R.layout.activity_test;
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 5));
        TestAdapter adapter = new TestAdapter(getCtx());
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


//                    SerialPortOpenSDK.getInstance().send(
//                            new Command.Builder().setCommand(Command.Consts.COMMAND_WRITE)
//                                    .setCommmandChannel(Integer.toHexString(Integer.parseInt(boxno)))
//                                    .build()
//                                    .getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishView();
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
        if (CommandProtocol.COMMAND_SELECT_DEPOSIT_STATE == commandProtocol.getCommand()) {
            int boxNum = commandProtocol.getData().size();
            LogUtils.e("boxNum:" + boxNum);
//            mPresenter.createDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), boxNum + "");
        }
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
