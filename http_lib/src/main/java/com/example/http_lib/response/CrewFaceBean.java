package com.example.http_lib.response;

import android.text.TextUtils;

import com.yidao.module_lib.base.BaseResponseBean;

public class CrewFaceBean extends BaseResponseBean {


    /**
     * repertoireImgs : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/3ac939b7dc9e422abf4fa981edfb8ca5.jpg
     * nativePlace : 石榴
     * userId : 3
     * avatar : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/2721a0fb957b4949a2c4ef3505e70d97.jpg
     * height : 8
     * introduction : 旺财
     * interviewId : 3
     * weight : 2
     * interest : 如花
     * age : 9
     * name : 华安
     * graduateSchool : 华安
     * sex : 0
     * page : {}
     */

    private String repertoireImgs;
    private String nativePlace;
    private long userId;
    private String avatar;
    private int height;
    private String introduction;
    private int interviewId;
    private int weight;
    private String interest;
    private String age;
    private String name;
    private String graduateSchool;
    private int sex;
    private int status;
    private long id;
    private PageBean page;

    public boolean isChoose;
    public boolean isVisible;

    /**
     * @return 面戏状态（0=申请中,1=待试戏，10=试戏中，20=剧组已签约，30=演员已签约,40=已结束(已杀青)，-1=已取消, -2=拒签,-3=拒绝面戏）
     */
    public String getStatusStr() {
        String statusStr = "";
        if (status == 0) {
            statusStr = "申请中";
        }
        if (status == 1) {
            statusStr = "等待试戏";
        }
        if (status == 10) {
            statusStr = "试戏中";
        }
        if (status == 20) {
            statusStr = "签约中";
        }
        if (status == 30) {
            statusStr = "签约成功";
        }
        if (status == 40) {
            statusStr = "杀青";
        }
        if (status == -1) {
            statusStr = "已取消";
        }
        if (status == -2) {
            statusStr = "拒签";
        }
        if (status == -3) {
            statusStr = "拒绝面戏";
        }
        return statusStr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRepertoireImgs() {
        return repertoireImgs;
    }

    public void setRepertoireImgs(String repertoireImgs) {
        this.repertoireImgs = repertoireImgs;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getAge() {
        return TextUtils.isEmpty(age) ? "" : age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
    }
}
