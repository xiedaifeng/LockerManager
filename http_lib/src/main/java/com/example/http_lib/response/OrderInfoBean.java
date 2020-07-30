package com.example.http_lib.response;

import java.io.Serializable;

public class OrderInfoBean implements Serializable {


//    {
//            "id": "11",
//            "device_id": "02:00:00:00:00:00",设备id'
//            "boxno": "11",格口号
//            "cun_phone": "17310034307",存件手机号
//            "qu_phone": "14738809304",取件手机号
//            "order_no": "2020072591253",订单号
//            "chird_order_no": "2020072510086",子订单号
//            "post_no": "SF12345678901",快递单号
//            "verification_text": "验证信息",验证信息
//            "status": "1",状态 待付款：0, 待取出：1,待取出退回：2,已取出：3 <string>
//            "money": "2.00",存件金额
//            "qu_money": "0.00",取件应付金额
//            "chao_money": "0.50",超出每小时应付金额
//            "bao_hour": "12",包含小时数
//            "chao_hour": "0",超出小时
//            "create_time": "1595465133",创建时间
//            "cunjian_time": "1595666821",存件时间
//            "qujian_time": "1595665717",存件时间
//            "tui_time": null,退回时间
//            "cun_qu_time": null,取件时间
//            "opencode": "119691"开箱密码 前两位为箱门编号 长度为6位
//    }




    private String id;
    private String device_id;
    private String boxno;
    private String cun_phone;
    private String qu_phone;
    private String order_no;
    private String verification_text;

    private String post_no;
    private String chird_order_no;
    private String status;
    private String money;
    private String qu_money;
    private String chao_money;
    private String bao_hour;

    private String chao_hour;
    private String create_time;
    private String cunjian_time;
    private String qujian_time;
    private String tui_time;
    private String cun_qu_time;
    private String opencode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getBoxno() {
        return boxno;
    }

    public void setBoxno(String boxno) {
        this.boxno = boxno;
    }

    public String getCun_phone() {
        return cun_phone;
    }

    public void setCun_phone(String cun_phone) {
        this.cun_phone = cun_phone;
    }

    public String getQu_phone() {
        return qu_phone;
    }

    public void setQu_phone(String qu_phone) {
        this.qu_phone = qu_phone;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getVerification_text() {
        return verification_text;
    }

    public void setVerification_text(String verification_text) {
        this.verification_text = verification_text;
    }

    public String getPost_no() {
        return post_no;
    }

    public void setPost_no(String post_no) {
        this.post_no = post_no;
    }

    public String getChird_order_no() {
        return chird_order_no;
    }

    public void setChird_order_no(String chird_order_no) {
        this.chird_order_no = chird_order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getQu_money() {
        return qu_money;
    }

    public void setQu_money(String qu_money) {
        this.qu_money = qu_money;
    }

    public String getChao_money() {
        return chao_money;
    }

    public void setChao_money(String chao_money) {
        this.chao_money = chao_money;
    }

    public String getBao_hour() {
        return bao_hour;
    }

    public void setBao_hour(String bao_hour) {
        this.bao_hour = bao_hour;
    }

    public String getChao_hour() {
        return chao_hour;
    }

    public void setChao_hour(String chao_hour) {
        this.chao_hour = chao_hour;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCunjian_time() {
        return cunjian_time;
    }

    public void setCunjian_time(String cunjian_time) {
        this.cunjian_time = cunjian_time;
    }

    public String getQujian_time() {
        return qujian_time;
    }

    public void setQujian_time(String qujian_time) {
        this.qujian_time = qujian_time;
    }

    public String getTui_time() {
        return tui_time;
    }

    public void setTui_time(String tui_time) {
        this.tui_time = tui_time;
    }

    public String getCun_qu_time() {
        return cun_qu_time;
    }

    public void setCun_qu_time(String cun_qu_time) {
        this.cun_qu_time = cun_qu_time;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }
}
