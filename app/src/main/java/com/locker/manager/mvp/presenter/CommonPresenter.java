package com.locker.manager.mvp.presenter;

import com.locker.manager.mvp.model.CommomModel;
import com.yidao.module_lib.base.BasePressPlus;
import com.yidao.module_lib.base.ibase.IBaseView;

public class CommonPresenter extends BasePressPlus {

    private CommomModel mCommomModel;

    public CommonPresenter(IBaseView view) {
        super(view);
        mCommomModel = new CommomModel(this);
    }
}
