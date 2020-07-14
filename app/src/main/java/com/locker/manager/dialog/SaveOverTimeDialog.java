package com.locker.manager.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class SaveOverTimeDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    public SaveOverTimeDialog(Context context) {
        super(context);
    }

    public SaveOverTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_save_overtime;
    }

    @Override
    protected void initPress() {

    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_exit:
                break;
        }
    }
}
