package com.locker.manager.base;//package com.yifei.yankan.base;

import android.content.Context;

import com.locker.manager.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseDialog;

public abstract class BaseUrlDialog extends BaseDialog {

    protected CommonPresenter mPresenter = new CommonPresenter(this);


    public BaseUrlDialog(Context context) {
        super(context);
    }

    public BaseUrlDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

}
