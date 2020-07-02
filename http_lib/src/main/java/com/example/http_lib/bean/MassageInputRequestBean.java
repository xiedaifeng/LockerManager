package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.response.GetMyInfoBean;
import com.example.http_lib.response.WorksListBean;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.util.List;

@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl,path = ILocalHost.messageInput,method = RequestMethodEnum.POST)
public class MassageInputRequestBean extends BaseBean {

    public String realName;
    public String repertoireImg;
    public String userMobile;
    public int  sex;
    public List<WorksListBean> worksList;
}
