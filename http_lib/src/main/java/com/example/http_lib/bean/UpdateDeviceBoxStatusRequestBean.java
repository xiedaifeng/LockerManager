package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/18
 */
@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl,path = ILocalHost.updateDeviceBoxStatus)
public class UpdateDeviceBoxStatusRequestBean extends BaseBean {


    public String openstatus;  //开启状态 open开启 close关闭
    public String boxno;  //箱门no
    public String device_id;  //手机号

}
