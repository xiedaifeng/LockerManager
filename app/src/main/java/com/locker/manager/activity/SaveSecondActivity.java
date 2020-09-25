package com.locker.manager.activity;

import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.sender.SenderActivity;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.manager.VibratorManager;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;


public class SaveSecondActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_count_down)
    TextView mTvCountDown;

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    protected int getView() {
        return R.layout.activity_save_second;
    }


    @Override
    public void init() {
        setCountDownTextView(mTvCountDown);
        setCurrentTime(tvTitle,System.currentTimeMillis());
    }


    @OnClick({R.id.iv_left, R.id.rl_parcel, R.id.rl_sender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.rl_parcel:
                if(isFastClick()){
                    return;
                }
                skipActivityByFinish(SaveFirstActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
            case R.id.rl_sender:
                if(isFastClick()){
                    return;
                }
//                skipActivity(SenderDeliverAndBackActivity.class);
                skipActivityByFinish(SenderActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
        }
    }
}
