package com.example.http_lib.response;

import java.io.Serializable;

public class DeviceBoxDetailBean implements Serializable {


    /**
     * hour : 12
     * title : 大
     * size : big
     * id : 1
     * chao_money : 0.50
     * count : 3
     * money : 2.00
     */

    private String hour;
    private String title;
    private String size;
    private String id;
    private String chao_money;
    private String count;
    private String money;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChao_money() {
        return chao_money;
    }

    public void setChao_money(String chao_money) {
        this.chao_money = chao_money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
