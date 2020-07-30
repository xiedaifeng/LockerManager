package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/18
 */
@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl,path = ILocalHost.createOrder)
public class CreateOrderRequestBean extends BaseBean {


    public String boxno;  //
    public String device_id;  //
    public String cun_phone;  //
    public String post_no;  //
    public String verification_text;  //
    public String qu_phone;  //
    public String boxsize;  //

}
