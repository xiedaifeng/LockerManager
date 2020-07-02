package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

@RequestrAnnotation(baseUrl = ILocalHost.myCrewHostUrl,path = ILocalHost.crewmessageInput,method = RequestMethodEnum.POST)
public class CrewMassageInputRequestBean extends BaseBean {

    public String businessLicense;
    public String companyName;
    public String legalPerson;
    public String bankUser;
    public String bankNo;
    public String bankName;
    public String idCardFront;
    public String idCardBack;
//    public String bankName;

}
