package com.yidao.module_lib.base.ibase;


import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;

/**
 * Created by xiaochan on 2017/6/26.
 */

public interface IBaseModel<T extends IBasePress> extends IHttpCallBack{
    T getPress();

    public BaseBean getBean();

    public void setBean(BaseBean obj);

    public void request();

    void request(boolean isEncrypt);

}
