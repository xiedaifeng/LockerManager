package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl, path = ILocalHost.addRole)
public class AddRoleRequestBean extends BaseBean implements ILocalHost {


    /**
     * address : 北京
     * repertoireId : 1
     * sex : 1
     * name : 张三丰
     * ages : 20-40
     * trait : 能吃能睡
     * repertoireRolesId : 1
     * startTime :
     * endTime :
     * remarks : 要求能歌善舞
     * addressId : 1
     */

    public String address;
    public Long repertoireId;
    public int sex;
    public String name;
    public String ages;
    public String trait;
    public Long repertoireRolesId;
    public long startTime;
    public long endTime;
    public String remarks;
    public String addressId;

}
