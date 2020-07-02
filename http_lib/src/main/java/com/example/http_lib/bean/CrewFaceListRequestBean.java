package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl, path = ILocalHost.getFaceList)
public class CrewFaceListRequestBean extends BaseBean implements ILocalHost {


    public PageBean page;

    public long repertoireId;
    public long roleId;
    public Integer status;

}
