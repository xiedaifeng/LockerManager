package com.yidao.module_lib.entity;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/26
 */
public class ContactPhoneBean implements Comparable<ContactPhoneBean> {

    private String phoneNum;
    private String name;
    private int contactTimes;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getContactTimes() {
        return contactTimes;
    }

    public void setContactTimes(int contactTimes) {
        this.contactTimes = contactTimes;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactPhoneBean{" +
                "phoneNum='" + phoneNum + '\'' +
                ", name='" + name + '\'' +
                ", contactTimes=" + contactTimes +
                '}';
    }

    @Override
    public int compareTo(ContactPhoneBean contactPhoneBean) {
        return contactPhoneBean.getContactTimes()-this.getContactTimes();
    }
}
