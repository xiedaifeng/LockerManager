package com.yidao.module_lib.base.ievent.video;

import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEvent;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ICommonEvent extends IBaseEvent {

    void onResponse(boolean success, Class requestCls, ResponseBean responseBean);
}
