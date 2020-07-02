package com.example.http_lib.response;

import com.yidao.module_lib.base.BaseResponseBean;

public class OperaDetailBean extends BaseResponseBean {


    /**
     * address : 北京
     * material : Zsq98h
     * createTimeZero : 0
     * play : LBlRWd
     * addressId : 1
     * focus : false
     * cover : m7E21w
     * enable : 0
     * id : 0
     * catalog : 1
     * createTime : 1592963139711
     * hotAreaAddressId : wq3JON
     * hot : 0
     * name : 亮剑
     * hotAreaType : 0
     * hotAreaAddress : 1HARg3
     * companyAuthBean : {"createTimeZero":0,"companyName":"如花","id":0,"failRemark":"伯虎","createTime":1592963139710,"bankName":"伯虎","businessLicense":"https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/104e6044c738429f8679e5bb88e665e4.jpg","idCardFront":"https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/3ac939b7dc9e422abf4fa981edfb8ca5.jpg","bankUser":"石榴","legalPerson":"华安","bankNo":"6","identityStatus":0,"companyId":1,"idCardBack":"https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/778b813fc28048f6848ec7aadc691a13.jpg"}
     * type : 1
     * companyId : 1
     */

    private String address;
    private String material;
    private int createTimeZero;
    private String play;
    private String addressId;
    private boolean focus;
    private String cover;
    private int enable;
    private int id;
    private int catalog;
    private long createTime;
    private String hotAreaAddressId;
    private int hot;
    private String name;
    private int hotAreaType;
    private String hotAreaAddress;
    private CompanyAuthBeanBean companyAuthBean;
    private int type;
    private int companyId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getCreateTimeZero() {
        return createTimeZero;
    }

    public void setCreateTimeZero(int createTimeZero) {
        this.createTimeZero = createTimeZero;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHotAreaAddressId() {
        return hotAreaAddressId;
    }

    public void setHotAreaAddressId(String hotAreaAddressId) {
        this.hotAreaAddressId = hotAreaAddressId;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHotAreaType() {
        return hotAreaType;
    }

    public void setHotAreaType(int hotAreaType) {
        this.hotAreaType = hotAreaType;
    }

    public String getHotAreaAddress() {
        return hotAreaAddress;
    }

    public void setHotAreaAddress(String hotAreaAddress) {
        this.hotAreaAddress = hotAreaAddress;
    }

    public CompanyAuthBeanBean getCompanyAuthBean() {
        return companyAuthBean;
    }

    public void setCompanyAuthBean(CompanyAuthBeanBean companyAuthBean) {
        this.companyAuthBean = companyAuthBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public static class CompanyAuthBeanBean {
        /**
         * createTimeZero : 0
         * companyName : 如花
         * id : 0
         * failRemark : 伯虎
         * createTime : 1592963139710
         * bankName : 伯虎
         * businessLicense : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/104e6044c738429f8679e5bb88e665e4.jpg
         * idCardFront : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/3ac939b7dc9e422abf4fa981edfb8ca5.jpg
         * bankUser : 石榴
         * legalPerson : 华安
         * bankNo : 6
         * identityStatus : 0
         * companyId : 1
         * idCardBack : https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/778b813fc28048f6848ec7aadc691a13.jpg
         */

        private int createTimeZero;
        private String companyName;
        private int id;
        private String failRemark;
        private long createTime;
        private String bankName;
        private String businessLicense;
        private String idCardFront;
        private String bankUser;
        private String legalPerson;
        private String bankNo;
        private int identityStatus;
        private int companyId;
        private String idCardBack;

        public int getCreateTimeZero() {
            return createTimeZero;
        }

        public void setCreateTimeZero(int createTimeZero) {
            this.createTimeZero = createTimeZero;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFailRemark() {
            return failRemark;
        }

        public void setFailRemark(String failRemark) {
            this.failRemark = failRemark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public String getBankUser() {
            return bankUser;
        }

        public void setBankUser(String bankUser) {
            this.bankUser = bankUser;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public int getIdentityStatus() {
            return identityStatus;
        }

        public void setIdentityStatus(int identityStatus) {
            this.identityStatus = identityStatus;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getIdCardBack() {
            return idCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            this.idCardBack = idCardBack;
        }
    }
}
