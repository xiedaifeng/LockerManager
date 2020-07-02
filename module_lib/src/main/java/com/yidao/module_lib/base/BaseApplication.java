package com.yidao.module_lib.base;

import android.content.Context;


import androidx.multidex.MultiDexApplication;


public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getApplication(){
        return mApplication;
    }
}
