package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class FollowsListBean extends BaseResponseBean {


    /**
     * companyId : 9
     * repertoireId : 3
     * createTime : 1592537035271
     * repertoireCover : 如花
     * id : 0
     * createTimeZero : 0
     * userId : 5
     * repertoireName : 如花
     */

    private int companyId;
    private int repertoireId;
    private long createTime;
    private String repertoireCover;
    private int id;
    private int createTimeZero;
    private int userId;
    private String repertoireName;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getRepertoireId() {
        return repertoireId;
    }

    public void setRepertoireId(int repertoireId) {
        this.repertoireId = repertoireId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRepertoireCover() {
        return repertoireCover;
    }

    public void setRepertoireCover(String repertoireCover) {
        this.repertoireCover = repertoireCover;
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

    public String getRepertoireName() {
        return repertoireName;
    }

    public void setRepertoireName(String repertoireName) {
        this.repertoireName = repertoireName;
    }
}
