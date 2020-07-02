package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl, path = ILocalHost.startAfterPlay)
public class AfterPlayRequestBean extends BaseBean implements ILocalHost {


    public String workAddrId;
    public long startTime;
    public long endTime;

}
