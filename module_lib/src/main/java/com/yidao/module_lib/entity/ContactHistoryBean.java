package com.yidao.module_lib.entity;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/27
 */
public class ContactHistoryBean implements Comparable<ContactHistoryBean>{

    private String name;

    private String phoneNum;

    private int times;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public int compareTo(ContactHistoryBean contactHistoryBean) {
        return contactHistoryBean.getTimes() - this.getTimes();
    }
}
