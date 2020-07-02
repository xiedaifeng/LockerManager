package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.response.WorksListBean;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.util.List;

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl,path = ILocalHost.addcrew,method = RequestMethodEnum.POST)
public class AddCrewRequestBean extends BaseBean {

    public String name;
    public int catalog;
    public int type;
    public String  addressId;
    public String cover;
}
