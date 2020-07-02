package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.response.WorksListBean;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.util.List;
@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl,path = ILocalHost.changeMoka,method = RequestMethodEnum.POST)
public class ChangeMokaDetailRequestBean  extends BaseBean {

    public String name;
    public String  age;
    public String  height;
    public String  weight;
    public String interest;
    public String graduateSchool;
    public String nativePlace;
    public String introduction;
    public String repertoireImgs;
    public List<WorksListBean> worksList;
}

