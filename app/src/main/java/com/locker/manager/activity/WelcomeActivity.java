package com.locker.manager.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.yidao.module_lib.manager.PermissionManager;
import com.yidao.module_lib.utils.LogUtils;

import butterknife.BindView;


public class WelcomeActivity extends BaseUrlView {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init() {
        PermissionManager.getInstance().setIPermissionLiatener(new PermissionManager.IPermissionListener() {
            @Override
            public void getPermissionSuccess() {
                LogUtils.e("getPermissionSuccess");
                skipActivityByFinish(HomeActivity.class);
            }
        });
        PermissionManager.getInstance().requestPermissions(this);

        setCurrentTime(tvTitle, System.currentTimeMillis());
        ivLeft.setVisibility(View.GONE);
    }
}
