package com.example.http_lib.response;

import java.io.Serializable;

public class OrderInfoBean implements Serializable {


    /**
     * qu_phone : 15715760196
     * order_no : 2020080533654
     * id : 45
     * cun_phone : 15757829477
     * money : 0.50
     * chird_order_no : 2020080575968
     * status : 0
     * chao_hour : 0
     * chao_money : 0.10
     * device_id : 890cab49072700000000
     * boxno : 05
     * qu_member : {"full_user_name":"","unionid":"","score":"0.00","idcard":"1234567890","avatar":"","password":"123456","account_money":"0.00","reg_type":"weixin","we_openid":"","member_id":"56","username":"","token":"df6ca9dff0076fa666e8b532112da3e3","create_time":"1596592073","openid":"","realname":"qz","telephone":"15715760196"}
     * cun_member : {"full_user_name":"","unionid":"","score":"0.00","idcard":"1234567890","avatar":"","password":"123456","account_money":"0.00","reg_type":"weixin","we_openid":"","member_id":"55","username":"","token":"db9376d0a3677e20595f93c3c9bead2d","create_time":"1596554385","openid":"","realname":"xdf","telephone":"15757829477"}
     * qu_money : 0.00
     * device : {"id":"7","update_time":"1596595011","create_time":"1596521909","device_id":"890cab49072700000000","device_status":"1"}
     * create_time : 1596595404
     * device_address : {}
     * bao_hour : 12
     * opencode : 057423
     */

    private String qu_phone;
    private String order_no;
    private String id;
    private String cun_phone;
    private String money;
    private String chird_order_no;
    private String status;
    private String chao_hour;
    private String chao_money;
    private String device_id;
    private String boxno;
    private QuMemberBean qu_member;
    private CunMemberBean cun_member;
    private String qu_money;
    private DeviceBean device;
    private String create_time;
    private DeviceAddressBean device_address;
    private String bao_hour;
    private String opencode;

    private String post_no;

    public String getPost_no() {
        return post_no;
    }

    public void setPost_no(String post_no) {
        this.post_no = post_no;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCun_phone() {
        return cun_phone;
    }

    public void setCun_phone(String cun_phone) {
        this.cun_phone = cun_phone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getChao_hour() {
        return chao_hour;
    }

    public void setChao_hour(String chao_hour) {
        this.chao_hour = chao_hour;
    }

    public String getChao_money() {
        return chao_money;
    }

    public void setChao_money(String chao_money) {
        this.chao_money = chao_money;
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

    public QuMemberBean getQu_member() {
        return qu_member;
    }

    public void setQu_member(QuMemberBean qu_member) {
        this.qu_member = qu_member;
    }

    public CunMemberBean getCun_member() {
        return cun_member;
    }

    public void setCun_member(CunMemberBean cun_member) {
        this.cun_member = cun_member;
    }

    public String getQu_money() {
        return qu_money;
    }

    public void setQu_money(String qu_money) {
        this.qu_money = qu_money;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public DeviceAddressBean getDevice_address() {
        return device_address;
    }

    public void setDevice_address(DeviceAddressBean device_address) {
        this.device_address = device_address;
    }

    public String getBao_hour() {
        return bao_hour;
    }

    public void setBao_hour(String bao_hour) {
        this.bao_hour = bao_hour;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public static class QuMemberBean {
        /**
         * full_user_name :
         * unionid :
         * score : 0.00
         * idcard : 1234567890
         * avatar :
         * password : 123456
         * account_money : 0.00
         * reg_type : weixin
         * we_openid :
         * member_id : 56
         * username :
         * token : df6ca9dff0076fa666e8b532112da3e3
         * create_time : 1596592073
         * openid :
         * realname : qz
         * telephone : 15715760196
         */

        private String full_user_name;
        private String unionid;
        private String score;
        private String idcard;
        private String avatar;
        private String password;
        private String account_money;
        private String reg_type;
        private String we_openid;
        private String member_id;
        private String username;
        private String token;
        private String create_time;
        private String openid;
        private String realname;
        private String telephone;

        public String getFull_user_name() {
            return full_user_name;
        }

        public void setFull_user_name(String full_user_name) {
            this.full_user_name = full_user_name;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccount_money() {
            return account_money;
        }

        public void setAccount_money(String account_money) {
            this.account_money = account_money;
        }

        public String getReg_type() {
            return reg_type;
        }

        public void setReg_type(String reg_type) {
            this.reg_type = reg_type;
        }

        public String getWe_openid() {
            return we_openid;
        }

        public void setWe_openid(String we_openid) {
            this.we_openid = we_openid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }

    public static class CunMemberBean {
        /**
         * full_user_name :
         * unionid :
         * score : 0.00
         * idcard : 1234567890
         * avatar :
         * password : 123456
         * account_money : 0.00
         * reg_type : weixin
         * we_openid :
         * member_id : 55
         * username :
         * token : db9376d0a3677e20595f93c3c9bead2d
         * create_time : 1596554385
         * openid :
         * realname : xdf
         * telephone : 15757829477
         */

        private String full_user_name;
        private String unionid;
        private String score;
        private String idcard;
        private String avatar;
        private String password;
        private String account_money;
        private String reg_type;
        private String we_openid;
        private String member_id;
        private String username;
        private String token;
        private String create_time;
        private String openid;
        private String realname;
        private String telephone;

        public String getFull_user_name() {
            return full_user_name;
        }

        public void setFull_user_name(String full_user_name) {
            this.full_user_name = full_user_name;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccount_money() {
            return account_money;
        }

        public void setAccount_money(String account_money) {
            this.account_money = account_money;
        }

        public String getReg_type() {
            return reg_type;
        }

        public void setReg_type(String reg_type) {
            this.reg_type = reg_type;
        }

        public String getWe_openid() {
            return we_openid;
        }

        public void setWe_openid(String we_openid) {
            this.we_openid = we_openid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }

    public static class DeviceBean {
        /**
         * id : 7
         * update_time : 1596595011
         * create_time : 1596521909
         * device_id : 890cab49072700000000
         * device_status : 1
         */

        private String id;
        private String update_time;
        private String create_time;
        private String device_id;
        private String device_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_status() {
            return device_status;
        }

        public void setDevice_status(String device_status) {
            this.device_status = device_status;
        }
    }

    public static class DeviceAddressBean {
    }
}
