package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class CityListBean extends BaseResponseBean {


    /**
     * cityName : 北京
     * parentCode : 0
     * createTime : 1592458401827
     * cityCode : 111
     * grade : 1
     * id : 0
     * status : 0
     */

    private String name;
    private long createTime;
    private long createTimeZero;
    private int hot;
    private int id;


    /**
     *  热门定位的属性
     */

    private String cityName;
    private String cityId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public long getCreateTimeZero() {
        return createTimeZero;
    }

    public void setCreateTimeZero(long createTimeZero) {
        this.createTimeZero = createTimeZero;
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
}
