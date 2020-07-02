package com.yidao.module_lib.base.ibase;

import com.yidao.module_lib.base.http.ResponseBean;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/5
 */
public interface IBaseEventPlus {
    void onResponse(boolean success, Class requestCls, ResponseBean responseBean);
}
