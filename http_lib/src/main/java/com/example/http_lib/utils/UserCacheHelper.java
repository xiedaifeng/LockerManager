package com.example.http_lib.utils;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.response.VersionBean;
import com.yidao.module_lib.utils.SharedPreferencesUtils;

public class UserCacheHelper {

    public static String user_Info = "user_info";

    public static String first_open = "first_open";
    public static String version = "version";

    public static String Cookie = "cookie";
    public static String UserType = "userType";

    public static UserInfoBean getUserInfo() {
        String userInfo = SharedPreferencesUtils.getString(user_Info, null);
        if (TextUtils.isEmpty(userInfo)) {
            return null;
        }
        return JSON.parseObject(userInfo, UserInfoBean.class);
    }

    public static void saveUserInfo(String userInfo) {
        SharedPreferencesUtils.putString(user_Info, userInfo);
    }

    public static VersionBean getVersionBean(){
        String versionInfo = SharedPreferencesUtils.getString(version, null);
        if (TextUtils.isEmpty(versionInfo)) {
            return null;
        }
        return JSON.parseObject(versionInfo, VersionBean.class);
    }

    public static void saveVersion(String versionInfo){
        SharedPreferencesUtils.putString(version, versionInfo);
    }

    public static boolean isLogin() {
        UserInfoBean userBean = getUserInfo();
        return userBean != null;
    }

    public static void logOut() {
        SharedPreferencesUtils.remove(user_Info);
    }

    public static String getAESToken() {
        UserInfoBean userInfoBean = getUserInfo();
        return userInfoBean == null ? "" : userInfoBean.getToken();
    }

    public static void setFirstOpen(boolean open){
        SharedPreferencesUtils.putBoolean(first_open, open);
    }

    public static boolean getFirstOpen(){
        return SharedPreferencesUtils.getBoolean(first_open, true);
    }

    public static void setCookie(String cookie){
        SharedPreferencesUtils.putString(Cookie, cookie);
    }

    public static String getCookie(){
        return SharedPreferencesUtils.getString(Cookie, "");
    }


    public static void setUserType(int userType){
        SharedPreferencesUtils.putInt(UserType, userType);
    }

    public static int getUserType(){
        return SharedPreferencesUtils.getInt(UserType, 0);
    }
}
