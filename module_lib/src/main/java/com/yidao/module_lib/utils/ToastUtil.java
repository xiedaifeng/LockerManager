package com.yidao.module_lib.utils;

import android.widget.Toast;

/**
 * Created by igeas on 2017/7/6.
 */

public class ToastUtil {
    private ToastUtil(){
        throw new IllegalArgumentException("ToastUtil should not be initialized");
    }

    public static void showShortToast(String content){
        CustomToast.getInstance().showToast(content, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int resId){
        CustomToast.getInstance().showToast(resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String content){
        CustomToast.getInstance().showToast(content, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int resId){
        CustomToast.getInstance().showToast(resId, Toast.LENGTH_LONG);
    }

    public static void showToast(String content, int duration){
        CustomToast.getInstance().showToast(content,duration);
    }

    public static void showToast(int resId,int duration){
        CustomToast.getInstance().showToast(resId,duration);
    }

    public static void dismiss(){
        CustomToast.getInstance().dismiss();
    }
}
