package com.example.http_lib.response;

import android.text.TextUtils;

import java.io.Serializable;

public class VersionBean implements Serializable {


    /**
     * remark : xxx版本
     * forceUpdate : 0
     * id : 3
     * type : 0
     * newest : 0
     * version : 1.0.2
     * url : http://xxxxx
     */

    private String remark;
    private boolean forceUpdate;
    private int id;
    private String type;
    private String newest;
    private String version;
    private String url;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewest() {
        return newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isNeedUpdate(String localVersion){
        String[] localStrs = null;
        String[] platformStrs = null;
        if(localVersion.contains(".")){
            localStrs = localVersion.split(".");
        }
        if(TextUtils.isEmpty(version)){
            return false;
        }
        if(version.contains(".")){
            platformStrs = version.split(".");
        }
        if(localStrs!=null && platformStrs!=null && platformStrs.length == 3 && localStrs.length == 3){
            if(Integer.parseInt(platformStrs[0]) > Integer.parseInt(localStrs[0])){
                return true;
            }
            if(Integer.parseInt(platformStrs[1]) > Integer.parseInt(localStrs[1])){
                return true;
            }
            int platformVersionNum = Integer.parseInt(version.replace(".",""));
            int localVersionNum = Integer.parseInt(localVersion.replace(".",""));
            if(platformVersionNum>localVersionNum){
                return true;
            }
        }
        return false;
    }
}
