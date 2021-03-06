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


public class SaveHandActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_count_down)
    TextView mTvCountDown;

    @Override
    protected int getView() {
        return R.layout.activity_save_hand;
    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
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
                //注释掉该句代码by2021-03-10 22:16
//                skipActivityByFinish(SaveSecondActivity.class);

                //直接跳转到发起存件的界面，不区分快递员和普通人by2021-03-10 22:16
                skipActivityByFinish(SenderActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
            case R.id.rl_sender:
                if(isFastClick()){
                    return;
                }
                skipActivityByFinish(SenderPickUpActivity.class);
                VibratorManager.getInstance().vibrate(50);
                break;
        }
    }
}
