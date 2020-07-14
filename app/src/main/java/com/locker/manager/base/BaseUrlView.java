package com.locker.manager.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.locker.manager.activity.receiver.TimerReceiver;
import com.locker.manager.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.utils.DateUtils;
import com.yidao.module_lib.utils.LogUtils;

public abstract class BaseUrlView extends BaseView {

    protected CommonPresenter mPresenter = new CommonPresenter(this);

    private TextView tvTitle;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        getAty().registerReceiver(receiver,filter);
    }

    protected void onReceiveTime(long time){
        LogUtils.e("onReceiveTime");
        if(tvTitle!=null){
            setCurrentTime(tvTitle,time);
        }
    }

    protected void setCurrentTime(TextView text, long time){
        this.tvTitle = text;
        String timeFirst = DateUtils.stampToDateFormat(time, "MM月dd日    EEEE    HH:mm");
        text.setText(timeFirst);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAty().unregisterReceiver(receiver);
    }
}
