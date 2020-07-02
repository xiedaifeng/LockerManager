package com.yidao.module_lib.base.ievent.user;

import com.yidao.module_lib.base.ibase.IBaseEvent;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IPhoneCodeEvent extends IBaseEvent {

    void sendCode();

    void sendCodeSuccess(String data);

    void sendCodeFail(String msg);

}
