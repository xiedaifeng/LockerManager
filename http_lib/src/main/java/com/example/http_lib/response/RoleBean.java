package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class RoleBean extends BaseResponseBean {


    /**
     * address : 北京
     * createTimeZero : 0
     * addressId : 1
     * endTime : 1592979922378
     * startTime : 1592979922378
     * trait : 喝酒
     * remarks : 洒洒水
     * ages : 30-47
     * minAge : 1
     * status : 1
     * id : 0
     * createTime : 1592979922378
     * repertoireId : 1
     * name : 和尚
     * maxAge : 10
     * sex : 1
     * companyId : 1
     */

    private String address;
    private int createTimeZero;
    private String addressId;
    private long endTime;
    private long startTime;
    private String trait;
    private String remarks;
    private String ages;
    private int minAge;
    private int status;
    private long id;
    private long createTime;
    private long repertoireId;
    private String name;
    private int maxAge;
    private int sex;
    private int companyId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreateTimeZero() {
        return createTimeZero;
    }

    public void setCreateTimeZero(int createTimeZero) {
        this.createTimeZero = createTimeZero;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 招募状态（0=未开始招募, 1=招募中，2=已停止招募）
     * @return
     */
    public String getStatusStr() {
        String str = "";
        switch (status){
            case 0:
                str = "未开始招募";
                break;
            case 1:
                str = "招募中";
                break;
            case 2:
                str = "招募完成";
                break;
        }
        return str;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getRepertoireId() {
        return repertoireId;
    }

    public void setRepertoireId(long repertoireId) {
        this.repertoireId = repertoireId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * 性别 0-女 1-男
     * @return
     */
    public String getSexStr() {
        String str = "";
        switch (sex){
            case 0:
                str = "女";
                break;
            case 1:
                str = "男";
                break;
        }
        return str;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
