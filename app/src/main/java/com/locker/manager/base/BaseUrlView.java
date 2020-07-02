package com.locker.manager.base;

import com.locker.manager.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseView;

public abstract class BaseUrlView extends BaseView {

    protected CommonPresenter mPresenter = new CommonPresenter(this);
}
