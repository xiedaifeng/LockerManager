package com.locker.manager.dialog;

import android.content.Context;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlDialog;

public class SaveOverTimeDialog extends BaseUrlDialog {

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
}
