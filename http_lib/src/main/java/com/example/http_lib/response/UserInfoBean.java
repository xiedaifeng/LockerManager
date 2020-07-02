package com.example.http_lib.response;

import java.io.Serializable;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */
public class UserInfoBean implements Serializable {



    /**
     * failRemark : 如花
     * level : 2
     * infoStatus : 0
     * accessKey : nw8w6wb2vbeWiERT
     * createTime : 1592474091242
     * nickName : 华安
     * userMobile : 18840921586
     * identityStatus : 0
     * avatar : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/eaf80ccb4e754f6fada1af42ea737248.jpg
     * id : 0
     * token : e10adc3949ba59abbe56e057f20f883e
     */

    private String failRemark;
    private int level;
    private int infoStatus;
    private String accessKey;
    private long createTime;
    private String nickName;
    private String userMobile;
    private int identityStatus;
    private String avatar;
    private int id;
    private String token;
    private String lastCheckAddrId;

    private int loginType;//剧组登入的是否是自己的剧组 0：不是 1：是
    private int enabled;//禁用状态 0-未禁用 1-已禁用
    private int phoneRoot;//手机编辑权限0：无 1：有


    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getPhoneRoot() {
        return phoneRoot;
    }

    public void setPhoneRoot(int phoneRoot) {
        this.phoneRoot = phoneRoot;
    }

    public String getLastCheckAddrId() {
        return lastCheckAddrId;
    }

    public void setLastCheckAddrId(String lastCheckAddrId) {
        this.lastCheckAddrId = lastCheckAddrId;
    }

    public String getFailRemark() {
        return failRemark;
    }

    public void setFailRemark(String failRemark) {
        this.failRemark = failRemark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(int infoStatus) {
        this.infoStatus = infoStatus;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
