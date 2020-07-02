package com.example.http_lib.enums;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/23
 */
public enum RequestTypeEnum {

    JSON("application/json;charset=utf-8"),
    FORM("application/x-www-form-urlencoded"),
    HTML("text/html"),
    MUL_FORM("multipart/form-data;boundary=%s");

    private String typeStr;

    RequestTypeEnum(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
