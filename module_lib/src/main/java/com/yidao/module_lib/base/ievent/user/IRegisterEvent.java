package com.yidao.module_lib.base.ievent.user;

import com.yidao.module_lib.base.ibase.IBaseEvent;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */
public interface IRegisterEvent extends IBaseEvent {

    void register();

    void registerSuccess(String bean);

    void registerFail(String bean);

}
