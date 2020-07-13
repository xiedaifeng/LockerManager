package com.locker.manager.activity.sender;

import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.SaveAppScanActivity;
import com.locker.manager.activity.SaveFirstActivity;
import com.locker.manager.base.BaseUrlView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;


public class SenderPickUpSuccessActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_case_state)
    TextView tvCaseState;
    @BindView(R.id.tv_orderNo)
    TextView tvOrderNo;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    @Override
    protected int getView() {
        return R.layout.activity_pick_up_success;
    }


    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());
    }

    @OnClick({R.id.iv_left, R.id.tv_pick_success, R.id.tv_hand_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveAppScanActivity.class);
                break;
            case R.id.tv_pick_success:
                // TODO: 2020/7/13 倒计时结束或者柜门关闭都返回首页 
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveAppScanActivity.class);
                break;
            case R.id.tv_hand_continue:
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveFirstActivity.class);
                break;
        }
    }

}
