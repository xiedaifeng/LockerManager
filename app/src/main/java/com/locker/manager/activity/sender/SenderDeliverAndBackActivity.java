package com.locker.manager.activity.sender;

import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SenderPickUpActivity;
import com.locker.manager.base.BaseUrlView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;


public class SenderDeliverAndBackActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getView() {
        return R.layout.activity_sender_deliver_back;
    }


    @Override
    public void init() {
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
                skipActivity(SenderDeliverActivity.class);
                break;
            case R.id.rl_sender:
                skipActivity(SenderPickUpActivity.class);
                break;
        }
    }
}
