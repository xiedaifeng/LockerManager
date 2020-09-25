package com.locker.manager.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.receiver.TimerReceiver;
import com.locker.manager.dialog.BoxStateDialog;
import com.locker.manager.mvp.presenter.CommonPresenter;
import com.locker.manager.service.LockerIntentService;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.DateUtils;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

public abstract class BaseUrlView extends BaseView {

    protected CommonPresenter mPresenter = new CommonPresenter(this);

    private TextView tvTitle;

    private TextView tvCountDown;

    private TimeCount timeCount = null;

    private TimerReceiver receiver = new TimerReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(Intent.ACTION_TIME_TICK)) {
                onReceiveTime(System.currentTimeMillis());
            }
        }
    };

    @Override
    protected void setNeedCountDown() {
        super.setNeedCountDown();
        timeCount = new TimeCount(getCountDownTime() * 1000, 1000);
        timeCount.start();
    }

    protected void setCountDownTextView(TextView tvCountDown){
        this.tvCountDown = tvCountDown;
    }

    protected boolean isFinishToHome(){
        return true;
    }


    protected int getCountDownTime(){
        return 30;
    }

    protected void releaseCountDown(){
        if(timeCount!=null){
            timeCount.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        getAty().registerReceiver(receiver,filter);
    }

    protected void onReceiveTime(long time){
        if(tvTitle!=null){
            setCurrentTime(tvTitle,time);
        }
    }

    protected void setCurrentTime(TextView text, long time){
        this.tvTitle = text;
        String timeFirst = DateUtils.stampToDateFormat(time, "MM月dd日    EEEE    HH:mm");
        text.setText(timeFirst);
    }

    protected boolean isDeviceOnLine(){
        if(!LockerIntentService.isDeviceOnLine){
            ToastUtil.showLongToast("设备已离线，请检查网络");
        }
        return LockerIntentService.isDeviceOnLine;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAty().unregisterReceiver(receiver);
        if(timeCount!=null){
            timeCount.cancel();
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (tvCountDown == null) {
                return;
            }
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            tvCountDown.setText(String.format("%ss后返回首页", value));
        }

        @Override
        public void onFinish() {
            if(isFinishToHome()){
                ViewManager.getInstance().finishOthersView(HomeActivity.class);
            } else {
                ViewManager.getInstance().finishView();
            }
        }
    }
}
