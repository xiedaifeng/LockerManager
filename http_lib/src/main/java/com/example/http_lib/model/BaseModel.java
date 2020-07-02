package com.example.http_lib.model;

import com.example.http_lib.HttpClient;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created by xiaochan on 2017/6/19.
 */

public abstract class BaseModel<T extends IBasePress> implements IBaseModel<T> {
    private T mPress;
    protected BaseBean bean;

    public BaseModel(T press) {
        this.mPress = press;
    }

    @Override
    public T getPress() {
        return this.mPress;
    }

    @Override
    public BaseBean getBean() {
        return bean;
    }

    @Override
    public void setBean(BaseBean bean) {
        this.bean = bean;
    }


    @Override
    public void request() {
        HttpClient.request(getBean(), this);
    }

    @Override
    public void request(boolean isEncrypt) {
        HttpClient.request(getBean(), isEncrypt, this);
    }

    @Override
    public void success(ResponseBean responseBean) {
        getPress().success(responseBean);
    }

    @Override
    public void failed(ResponseBean responseBean) {
        getPress().failed(responseBean);
        if (responseBean.getCode() == 10000) {
//            keepCookie();
        }
    }

    private void keepCookie() {

//        LoginBean loginBean = new LoginBean();
//        loginBean.setImei(PhoneInfoUtils.getPhoneSign());
//        String moblie = SharedPreferencesUtils.getString("MOBLIE", "");
//        loginBean.setMobile(moblie);
//        loginBean.setPhoneModel(PhoneInfoUtils.getSystemModel());
//        loginBean.setPhoneType(1);
//        loginBean.setType(0);
//        loginBean.setVersion(String.valueOf(PackageUtils.getVersionCode(BaseApplication.getApplication())));
//        setBean(loginBean);
//        HttpClient.request(getBean(), false, new IHttpCallBack() {
//            @Override
//            public void success(ResponseBean responseBean) {
//
//            }
//
//            @Override
//            public void failed(ResponseBean responseBean) {
//
//            }
//        });
    }

}
