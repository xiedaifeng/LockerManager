package com.yidao.module_lib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.module_lib.R;
import com.yidao.module_lib.base.BaseApplication;


/**
 * Created by igeas on 2017/7/6.
 */

public class CustomToast {
    private Toast mToast;
    private TextView mToastText;
    private CustomToast(Context context){
        View root = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        mToastText = (TextView) root.findViewById(R.id.toast_text);
        mToast = new Toast(context);
        mToast.setView(root);
    }

    public static CustomToast getInstance(){
        return ToastHolder.CUSTOM_TOAST;
    }

    public void showToast(String content, int duration){
        if (TextUtils.isEmpty(content)){
            return;
        }
        mToastText.setText(content);
        mToast.setDuration(duration);
        mToast.show();
    }

    public void showToast(int resId,int duration){
        mToastText.setText(resId);
        mToast.setDuration(duration);
        mToast.show();
    }

    public void dismiss(){
        if (mToast != null) {
            mToast.cancel();
        }
    }
    private static class ToastHolder{
        private static final CustomToast CUSTOM_TOAST = new CustomToast(BaseApplication.getApplication());
    }
}
