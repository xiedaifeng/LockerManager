package com.yidao.module_lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEventPlus;
import com.yidao.module_lib.base.ibase.IBaseView;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LoggerUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;
import com.yidao.module_lib.widget.LoadingAlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by xiaochan on 2017/6/19.
 */

public abstract class BaseView extends AppCompatActivity implements IBaseView , IBaseEventPlus {
    private LoadingAlertDialog mAlertDialog;

    protected abstract int getView();

    protected View rootView;

    private long lastClickTime = 0L;

    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity OnCreate");
        ViewManager.getInstance().addView(this);
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(getView(), null);
        initLayout();
        setContentView(rootView);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        if (useTransparent()){
            mImmersionBar.statusBarDarkFont(true);
            if (changeStatusColor()){
                mImmersionBar.statusBarDarkFont(false);
                mImmersionBar.statusBarColor("#0ED26B");
            }
            mImmersionBar.init();
        }
        if(useStatusBarDarkFont()){
            mImmersionBar.statusBarDarkFont(true);
        }

        init();
    }

    protected boolean changeStatusColor() {
        return true;
    }

    /**
     *
     *
     */
    protected boolean useStatusBarDarkFont() {
        return false;
    }

    /**
     * 是否使用沉浸式
     * @return true 使用
     */
    protected boolean useTransparent() {
        return true;
    }

    protected void initLayout() {

    }

    @Override
    protected void onStart() {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity OnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity OnResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity OnRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity OnStop");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        ViewManager.getInstance().finishView();
    }

    @Override
    protected void onDestroy() {
        LoggerUtils.debug("lifecycle", this.getClass(), "Activity onDestroy");
        dismissLoadingDialog();
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        super.onDestroy();
    }

    protected boolean isFastClick(){
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - lastClickTime > 0 && currentClickTime - lastClickTime < 500){
            return true;
        }
        lastClickTime = currentClickTime;
        return false;
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {

    }

    public void setkeyAble(){
        mImmersionBar.keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).init();
    }


    @Override
    public void alertSuccess() {
        dismissLoadingDialog();
    }

    @Override
    public void alertFailed() {
        dismissLoadingDialog();
    }

    public abstract void init();

    @Override
    public void skipActivity(Class<? extends IBaseView> view) {
        Intent intent1 = new Intent(this, view);
        startActivity(intent1);
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view, Bundle bundle) {
        Intent intent1 = new Intent(this, view);
        intent1.putExtras(bundle);
        startActivity(intent1);
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void skipActivityForResult(Class<? extends IBaseView> view, int requestCode){
        Intent intent1 = new Intent(this, view);
        startActivityForResult(intent1,requestCode);
    }

    @Override
    public void skipActivityForResult(Class<? extends IBaseView> view, Bundle bundle,int requestCode){
        Intent intent1 = new Intent(this, view);
        intent1.putExtras(bundle);
        startActivityForResult(intent1,requestCode);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view) {
        skipActivity(view);
        ViewManager.getInstance().finishView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view, Bundle bundle) {
        skipActivity(view, bundle);
        ViewManager.getInstance().finishView();
    }

    /**
     * 获取数据后，隐藏dialog
     */
    @Override
    public void dismissLoadingDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * 耗时任务开启时，显示dialog,由子类调用
     */
    @Override
    public void showLoaddingDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new LoadingAlertDialog(this);
            mAlertDialog.setCancelable(true);
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    @Override
    public LoadingAlertDialog getLoadingDialog() {
        return mAlertDialog;
    }

    @Override
    public void setLoaddingMsg(String msg) {
    }

    /**
     * 吐司，由子类调用
     *
     * @param content 吐司的内容/资源id
     */
    @Override
    public void showToast(String content) {
        ToastUtil.showShortToast(content);
    }

    @Override
    public void showToast(int resId) {
        ToastUtil.showShortToast(resId);
    }

    @Override
    public void showLongToast(String content) {
        ToastUtil.showLongToast(content);
    }

    @Override
    public void showLongToast(int resId) {
        ToastUtil.showLongToast(resId);
    }

    @Override
    public void cancelToast() {
        ToastUtil.dismiss();
    }

    @Override
    public Activity getAty() {
        return (Activity)this;
    }

    @Override
    public Context getCtx() {
        return (Context)this;
    }


}
