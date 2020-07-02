package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class FaceBean extends BaseResponseBean {

    /**
     * address : 西安
     * repertoireId : 7
     * roleId : 8
     * roleName : 莲花
     * startTime : 1592531536901
     * endTime : 1592531536901
     * page : {}
     * repertoireName : 华安
     * addressId : 1
     * status : 0
     */

    private String address;
    private int repertoireId;
    private int roleId;
    private String roleName;
    private long startTime;
    private long endTime;
    private PageBean page;
    private String repertoireName;
    private String addressId;
    private int status;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRepertoireId() {
        return repertoireId;
    }

    public void setRepertoireId(int repertoireId) {
        this.repertoireId = repertoireId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public String getRepertoireName() {
        return repertoireName;
    }

    public void setRepertoireName(String repertoireName) {
        this.repertoireName = repertoireName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return 面戏状态（0=申请中,1=待试戏，10=试戏中，20=剧组已签约，30=演员已签约,40=已结束(已杀青)，-1=已取消, -2=拒签,-3=拒绝面戏）
     */
    public String getStatusStr(){
        String statusStr = "";
        if(status == 0){
            statusStr = "申请中";
        }
        if(status == 1){
            statusStr = "等待试戏";
        }
        if(status == 10){
            statusStr = "试戏中";
        }
        if(status == 20){
            statusStr = "签约中";
        }
        if(status == 30){
            statusStr = "签约成功";
        }
        if(status == 40){
            statusStr = "杀青";
        }
        if(status == -1){
            statusStr = "已取消";
        }
        if(status == -2){
            statusStr = "拒签";
        }
        if(status == -3){
            statusStr = "拒绝面戏";
        }
        return statusStr;
    }

    public static class PageBean {
    }
}
