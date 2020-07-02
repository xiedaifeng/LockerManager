package com.locker.manager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SaveSecondActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getView() {
        return R.layout.activity_save_second;
    }


    @Override
    public void init() {

    }


    @OnClick({R.id.iv_left, R.id.rl_parcel, R.id.rl_sender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.rl_parcel:
                break;
            case R.id.rl_sender:
                break;
        }
    }
}
