package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class ActorBean extends BaseResponseBean {


    /**
     * interest : 华安
     * age : 4
     * userId : 9
     * avatar : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/104e6044c738429f8679e5bb88e665e4.jpg
     * name : 莲花
     * sex : 0
     */

    private String interest;
    private int age;
    private int userId;
    private String avatar;
    private String name;
    private int sex;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
