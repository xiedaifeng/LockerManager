package com.yidao.module_lib.base.http;


/**
 * Created by xiaochan on 2017/6/26.
 */

public class ResponseBean {
    private Integer code;

    private int errCode;

    private String errMsg;

    private String data;

    private Class mClass;
    /**
     * 解析的bean class
     */
    private Class mParseClass;

    private Object carry;

    private PageBean page;
    /**
     * totoResult : 2
     * showCount : 2
     * totalPage : 1
     * currentPage : 1
     */



    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getData() {
        return data;
    }

    public Class getRequestClass() {
        return mClass;
    }

    public void setRequestClass(Class aClass) {
        mClass = aClass;
    }

    public Object getCarry() {
        return carry;
    }

    public void setCarry(Object carry) {
        this.carry = carry;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public Class getParseClass() {
        return mParseClass;
    }

    public void setParseClass(Class parseClass) {
        mParseClass = parseClass;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", msg='" + errMsg + '\'' +
                ", data='" + data + '\'' +
                ", mClass='" + mClass+ '\'' +
                ", carry='" + carry + '\'' +
                ", errCode='"+ errCode + '\''+
                '}';
    }


    public static class PageBean{

        private int totoResult;
        private int showCount;
        private int totalPage;
        private int currentPage;

        public boolean isNoMoreData(){
            return currentPage == totalPage;
        }

        public int getTotoResult() {
            return totoResult;
        }

        public void setTotoResult(int totoResult) {
            this.totoResult = totoResult;
        }

        public int getShowCount() {
            return showCount;
        }

        public void setShowCount(int showCount) {
            this.showCount = showCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

    }
}
