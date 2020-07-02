package com.yidao.module_lib.base.ibase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.yidao.module_lib.widget.LoadingAlertDialog;


/**
 * Created by xiaochan on 2017/6/26.
 */

public interface IBaseView  {

    Activity getAty();

    Context getCtx();

    public void alertSuccess();

    public void alertFailed();


    public void showToast(String content);

    public void showToast(int resId);

    public void showLongToast(String content);

    public void showLongToast(int resId);

    public void cancelToast();


    public void skipActivity(Class<? extends IBaseView> view) ;

    public void skipActivity(Class<? extends IBaseView> view, Bundle bundle);

    public void skipActivityByFinish(Class<? extends IBaseView> view);

    public void skipActivityByFinish(Class<? extends IBaseView> view, Bundle bundle) ;

    public void skipActivityForResult(Class<? extends IBaseView> view, int requestCode) ;

    public void skipActivityForResult(Class<? extends IBaseView> view, Bundle bundle,int requestCode) ;

    void dismissLoadingDialog();

    void showLoaddingDialog();

    void setLoaddingMsg(String msg);

    LoadingAlertDialog getLoadingDialog();
}
