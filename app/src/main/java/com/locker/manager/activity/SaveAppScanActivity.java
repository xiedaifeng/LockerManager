package com.locker.manager.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;

import butterknife.BindView;
import butterknife.OnClick;


public class SaveAppScanActivity extends BaseUrlView {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    @Override
    protected int getView() {
        return R.layout.activity_save_app_scan;
    }

    @Override
    public void init() {
        ivLeft.setVisibility(View.GONE);
    }


    @OnClick({R.id.iv_left, R.id.tv_hand_save,R.id.tv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_hand_save:
                skipActivity(SaveHandActivity.class);
                break;
            case R.id.tv_help:
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }
}
