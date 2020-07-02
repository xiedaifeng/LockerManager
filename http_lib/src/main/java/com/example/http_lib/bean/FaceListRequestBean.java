package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.myHostUrl, path = ILocalHost.getFaceList)
public class FaceListRequestBean extends BaseBean implements ILocalHost {


    public PageBean page;
    /**
     * 面戏状态（-1=全部,0=申请中，10=试戏中，20=剧组已签约，30=签约成功，40=已取消）
     */
    public Integer status;

}
