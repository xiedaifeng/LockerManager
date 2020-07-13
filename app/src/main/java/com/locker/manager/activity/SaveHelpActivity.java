package com.locker.manager.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;


public class SaveHelpActivity extends BaseUrlView {


    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected int getView() {
        return R.layout.activity_save_help;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());
    }


    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveAppScanActivity.class);
                break;
        }
    }
}
