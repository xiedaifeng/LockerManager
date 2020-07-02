package com.locker.manager.base;//package com.yifei.yankan.base;

import com.locker.manager.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseFragment;

public abstract class BaseUrlFragment extends BaseFragment {
    protected CommonPresenter mPresenter = new CommonPresenter(this);
}
