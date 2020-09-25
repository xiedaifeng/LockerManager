package com.locker.manager.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.BackOrderRequestBean;
import com.example.http_lib.bean.DeviceBoxTimeStatusRequestBean;
import com.example.http_lib.bean.GetOrderInfoByCodeRequestBean;
import com.example.http_lib.bean.OpenDeviceBoxRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.locker.manager.R;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.dialog.BoxStateDialog;
import com.locker.manager.dialog.SaveOverTimeDialog;
import com.locker.manager.dialog.SecondaryDialog;
import com.locker.manager.manager.VibratorManager;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class SenderPickUpActivity extends BaseUrlView {

    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_post_phone)
    EditText etPostPhone;
    @BindView(R.id.tv_back_home)
    TextView tvBackHome;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_title_count_down)
    TextView mTvCountDown;

    private BoxStateDialog dialog = null;

    private SaveOverTimeDialog timeDialog = null;
    private String post_no;
    private String orderId;

    private String money;

    private final int countDownCode = 0x113;

    private int countDownTime = 30;

    private boolean isLongClickEnable;

    private final int longClickDeleteCode = 0x114;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == countDownCode) {
                countDownTime--;
                mTvCountDown.setText(String.format("%ss后返回首页", countDownTime));
                if (countDownTime > 0) {
                    mHandler.sendEmptyMessageDelayed(countDownCode, 1000);
                } else {
                    ViewManager.getInstance().finishOthersView(HomeActivity.class);
                }
            } else
                if(msg.what == longClickDeleteCode){
                deleteString();
            }
        }
    };

    @Override
    protected int getView() {
        return R.layout.activity_sender_pick_up;
    }


    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    public int getCountDownTime() {
        return 60;
    }

    @Override
    public void init() {
        setCountDownTextView(mTvCountDown);

        setCurrentTime(tvTitle, System.currentTimeMillis());

        tvDelete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        isLongClickEnable = false;
                        if(mHandler!=null){
                            mHandler.removeMessages(longClickDeleteCode);
                        }
                        break;
                }
                return false;
            }
        });

        tvDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isLongClickEnable = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isLongClickEnable){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(mHandler!=null){
                                mHandler.sendEmptyMessage(longClickDeleteCode);
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

        SoftKeyboardUtil.disableShowInput(etPostPhone);
    }

    @OnClick({R.id.iv_left, R.id.tv_next, R.id.iv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_next:
                if(!isDeviceOnLine()){
                    return;
                }
                VibratorManager.getInstance().vibrate(50);
                String code = etPostPhone.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast("取件码不能为空");
                    return;
                }
                mPresenter.getOrderInfoByCode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), code);
//                mPresenter.getDeviceBoxTimeStatus(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), code);
                break;
            case R.id.iv_help:
                VibratorManager.getInstance().vibrate(50);
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }

    @OnClick({R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_reset, R.id.tv_delete})
    public void onViewNumClicked(View view) {
        VibratorManager.getInstance().vibrate(50);
        switch (view.getId()) {
            case R.id.tv_0:
                addString("0");
                break;
            case R.id.tv_1:
                addString("1");
                break;
            case R.id.tv_2:
                addString("2");
                break;
            case R.id.tv_3:
                addString("3");
                break;
            case R.id.tv_4:
                addString("4");
                break;
            case R.id.tv_5:
                addString("5");
                break;
            case R.id.tv_6:
                addString("6");
                break;
            case R.id.tv_7:
                addString("7");
                break;
            case R.id.tv_8:
                addString("8");
                break;
            case R.id.tv_9:
                addString("9");
                break;
            case R.id.tv_reset:
                reSetString();
                break;
            case R.id.tv_delete:
                deleteString();
                break;
        }
    }

    private void addString(String str) {
        if (etPostPhone.isFocused()) {
            EditTextInputUtils.addString(etPostPhone, str);
        }
    }

    private void reSetString() {
        if (etPostPhone.isFocused()) {
            etPostPhone.setText("");
        }
    }

    private void deleteString() {
        if (etPostPhone.isFocused()) {
            EditTextInputUtils.deleteString(etPostPhone);
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == OpenDeviceBoxRequestBean.class) {

                if (!TextUtils.isEmpty(post_no)) { //post_no不为空即为快递员包裹   为空为其他包裹

                    if (dialog == null) {
                        dialog = new BoxStateDialog(this);
                    }
                    dialog.setOpenBoxId(etPostPhone.getText().toString());
                    dialog.setClickListener(new BoxStateDialog.IClickListener() {
                        @Override
                        public void openBox(String openBoxId) {
                        }
                        @Override
                        public void getBack(String openBoxId) {
                            SecondaryDialog secondaryDialog = new SecondaryDialog(getCtx(),etPostPhone.getText().toString(),orderId);
                            secondaryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dia) {
                                    if(dialog!=null && !dialog.isShowing() && !dialog.isCountDownEnd){
                                        dialog.show();
                                    }
                                }
                            });
                            secondaryDialog.show();
//                            mPresenter.backOrder(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString(),orderId);
//                            LockerApplication.sTuiOrderId = orderId;
                        }
                    });
                    dialog.show();
                }
            }
            if (requestCls == DeviceBoxTimeStatusRequestBean.class) {
                // TODO: 2020/7/24  判断取件的快递的状态，是否超时 超时弹出收款码弹框   没有超时则打开相应的箱门
                String code = responseBean.getData();
                if (TextUtils.equals("1", code)) { //超时请支付费用
                    if (timeDialog == null) {
                        timeDialog = new SaveOverTimeDialog(getCtx(), etPostPhone.getText().toString(),money);
                    }
                    timeDialog.setCountDownCallback(new SaveOverTimeDialog.IOnCountDownCallback() {
                        @Override
                        public void onFinish() {
                            timeDialog.dismiss();
                            timeDialog = null;
                            mTvCountDown.setVisibility(View.VISIBLE);
                            if(mHandler!=null){
                                mHandler.removeMessages(countDownCode);
                                countDownTime = 30;
                                mHandler.sendEmptyMessageDelayed(countDownCode,1000);
                            }
                        }
                    });
                    timeDialog.hidePayView();
                    timeDialog.show();
                } else if (TextUtils.equals("0", code)) {
                    mPresenter.openDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString());
                }
            }
            if (requestCls == BackOrderRequestBean.class) {
            }
            if (requestCls == GetOrderInfoByCodeRequestBean.class) {
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                post_no = orderInfoBean.getPost_no();
                orderId = orderInfoBean.getId();
                LockerApplication.sQuOrderId = orderId;
                String chao_hour = TextUtils.isEmpty(orderInfoBean.getChao_hour())?"0":orderInfoBean.getChao_hour();
                String chao_money = TextUtils.isEmpty(orderInfoBean.getChao_money())?"0":orderInfoBean.getChao_money();
//                money = Float.parseFloat(chao_hour)*Float.parseFloat(chao_money);

                money = orderInfoBean.getChaoshi_money();
                mPresenter.getDeviceBoxTimeStatus(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString());


                if(mHandler!=null){
                    mHandler.removeMessages(countDownCode);
                    mTvCountDown.setVisibility(View.GONE);
                }

                releaseCountDown();

            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDialog != null) {
            timeDialog.releaseTimer();
            timeDialog.setCountDownCallback(null);
            if(timeDialog.isShowing()){
                timeDialog.dismiss();
            }
        }
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
        if(mHandler!=null){
            mHandler.removeMessages(countDownCode);
        }
    }
}
