package com.example.http_lib.response;

import java.util.List;

public class MokaDetailBean {


    /**
     * graduateSchool : 伯虎
     * worksList : [{"actor":"赵刚","joinTime":2018,"createTime":1592531536865,"id":0,"type":1,"createTimeZero":0,"userId":1,"repertoireName":"亮剑"},{"actor":"赵刚","joinTime":2018,"createTime":1592531536865,"id":0,"type":1,"createTimeZero":0,"userId":1,"repertoireName":"亮剑"}]
     * sex : 0
     * weight : 9
     * userId : 7
     * repertoireImgs : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/eaf80ccb4e754f6fada1af42ea737248.jpg
     * interest : 石榴
     * createTime : 1592531536865
     * name : 华安
     * nativePlace : 伯虎
     * id : 0
     * createTimeZero : 0
     * age : 9
     * introduction : 伯虎
     * height : 6
     */

    private String graduateSchool;
    private int sex;
    private int weight;
    private int userId;
    private String repertoireImgs;
    private String interest;
    private long createTime;
    private String name;
    private String nativePlace;
    private int id;
    private int createTimeZero;
    private int age;
    private String introduction;
    private int height;
    private List<WorksListBean> worksList;

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRepertoireImgs() {
        return repertoireImgs;
    }

    public void setRepertoireImgs(String repertoireImgs) {
        this.repertoireImgs = repertoireImgs;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WorksListBean> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<WorksListBean> worksList) {
        this.worksList = worksList;
    }


}
