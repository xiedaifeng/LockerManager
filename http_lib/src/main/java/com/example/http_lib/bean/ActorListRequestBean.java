package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl, path = ILocalHost.getActorList)
public class ActorListRequestBean extends BaseBean implements ILocalHost {

//    public Long repertoireId;
//    public Integer noticeType;
//    public Integer status;
    public PageBean page;

}
