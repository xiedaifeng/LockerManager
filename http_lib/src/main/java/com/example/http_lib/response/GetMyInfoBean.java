package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

import java.util.List;

public class GetMyInfoBean  extends BaseResponseBean {


    /**
     * realName : 如花
     * repertoireImg : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/778b813fc28048f6848ec7aadc691a13.jpg
     * failRemark : 伯虎
     * createTime : 1592531536858
     * userMobile : 18830744726
     * worksList : [{"actor":"赵刚","joinTime":2018,"createTime":1592531536858,"id":0,"type":1,"createTimeZero":0,"userId":1,"repertoireName":"亮剑"},{"actor":"赵刚","joinTime":2018,"createTime":1592531536858,"id":0,"type":1,"createTimeZero":0,"userId":1,"repertoireName":"亮剑"}]
     * sex : 0
     * identityStatus : 1
     * id : 0
     * createTimeZero : 0
     * userId : 8
     */

    private String realName;
    private String repertoireImg;
    private String failRemark;
    private long createTime;
    private String userMobile;
    private int sex;
    private int identityStatus;
    private int id;
    private int createTimeZero;
    private int userId;
    private List<WorksListBean> worksList;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRepertoireImg() {
        return repertoireImg;
    }

    public void setRepertoireImg(String repertoireImg) {
        this.repertoireImg = repertoireImg;
    }

    public String getFailRemark() {
        return failRemark;
    }

    public void setFailRemark(String failRemark) {
        this.failRemark = failRemark;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreateTimeZero() {
        return createTimeZero;
    }

    public void setCreateTimeZero(int createTimeZero) {
        this.createTimeZero = createTimeZero;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<WorksListBean> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<WorksListBean> worksList) {
        this.worksList = worksList;
    }


}
