package com.example.http_lib.bean;


import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl,path = ILocalHost.getUserInfo,method = RequestMethodEnum.POST)

public class GetCrewUserInfoRequestBean extends BaseBean {

}
