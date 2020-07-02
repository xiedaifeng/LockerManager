package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ILoginPress extends IBasePress {

    void login(int type);

    void getPhone();

    void sendCode();
}
