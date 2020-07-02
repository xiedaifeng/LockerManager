package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;


@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl,path = ILocalHost.getFollowsList,method = RequestMethodEnum.POST)
public class GetFollowsListRequestBean extends BaseBean {

    public PageBean page;


}
