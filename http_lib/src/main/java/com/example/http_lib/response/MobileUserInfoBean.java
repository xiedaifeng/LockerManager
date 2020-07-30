package com.example.http_lib.response;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */
public class MobileUserInfoBean implements Serializable {

//"member_id": "50",... <string>
//"username": "",... <string>
//"realname": "真实姓名",... <string>
//"openid": "",... <string>
//"unionid": "",... <string>
//"token": "8805d80e33efdc37083bc6c55cc2328a",... <string>
//"telephone": "17310034307",... <string>
//"avatar": "https://www.tan1688.com//Uploads/image/goods/2020-07-17/4719631853793541.jpg",... <string>
//"idcard": null... <string>

    private String member_id;
    private String username;
    private String realname;
    private String openid;
    private String unionid;
    private String token;
    private String telephone;
    private String avatar;
    private String idcard;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return TextUtils.isEmpty(realname)?"":realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
