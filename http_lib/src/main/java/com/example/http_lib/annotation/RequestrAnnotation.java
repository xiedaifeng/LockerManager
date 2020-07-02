package com.example.http_lib.annotation;


import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 类名：RequestrAnnotation
 * 类描述：请求注解
 * 创建人：byron
 * 修改人：byron
 * 修改时间：2017年6月23日 下午10:26:15
 * 修改备注：
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestrAnnotation {
    /**
     * 表求方式 get, post
     */
    public RequestMethodEnum method() default RequestMethodEnum.POST;

    /**
     * 请求头Content-Type
     */
    public RequestTypeEnum type() default RequestTypeEnum.JSON;

    /**
     * 请求的地址
     * @return
     */
    public String path();

    /**
     * 基础请求地址
     * @return
     */
    public String baseUrl();

}
