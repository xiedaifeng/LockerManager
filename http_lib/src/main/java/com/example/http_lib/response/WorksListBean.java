package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class WorksListBean extends BaseResponseBean {
    /**
     * actor : 赵刚
     * joinTime : 2018
     * createTime : 1592531536858
     * id : 0
     * type : 1
     * createTimeZero : 0
     * userId : 1
     * repertoireName : 亮剑
     */

    private String actor;
    private int joinTime;
    private long createTime;
    private int id;
    private int type;
    private int createTimeZero;
    private int userId;
    private String repertoireName;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(int joinTime) {
        this.joinTime = joinTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getRepertoireName() {
        return repertoireName;
    }

    public void setRepertoireName(String repertoireName) {
        this.repertoireName = repertoireName;
    }

}
