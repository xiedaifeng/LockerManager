package com.yidao.module_lib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidao.module_lib.base.ibase.IBaseView;
import com.yidao.module_lib.widget.LoadingAlertDialog;

import butterknife.ButterKnife;


/**
 * Created by xiaochan on 2017/6/19.
 */

public abstract class BaseAdapterView implements IBaseView {
    protected Context context;
    protected IBaseView act;
    private LoadingAlertDialog mAlertDialog;
    private View view;
    private ViewGroup group;
    private LayoutInflater layoutInflater;
    //绑定的fragment或者activity
    protected IBaseView mAttachView;
    private long lastClickTime;

    public void initView(Context context, ViewGroup group) {
        this.act = (IBaseView) context;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.group = group;
        initView(getLayoutView(getView()));
    }

    public View getLayoutView(int layout) {
        return layoutInflater.inflate(layout, group, false);
    }

    public Context getContext() {
        return context;
    }

    public void initView(View v) {
        view = v;
//        XFViewInject.inject(this, view);
        ButterKnife.bind(this,view);
        init();
    }

    public View getCurrentView() {
        return view;
    }

    protected abstract int getView();

    public void setAttachView(IBaseView attachView){
        mAttachView = attachView;
    }

    public void destoryAttachView(){
        mAttachView = null;
        context = null;
        act = null;
        layoutInflater = null;
    }

    public boolean isFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime <= 300){
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    @Override
    public void alertSuccess() {
    }

    @Override
    public void alertFailed() {
    }

    public abstract void init();

    @Override
    public void showToast(String content) {
        act.showToast(content);
    }

    @Override
    public void showToast(int resId) {
        act.showToast(resId);
    }

    @Override
    public void showLongToast(String content) {
        act.showLongToast(content);
    }

    @Override
    public void showLongToast(int resId) {
        act.showLongToast(resId);
    }

    @Override
    public void cancelToast() {
        act.cancelToast();
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view) {
        act.skipActivity(view);
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view, Bundle bundle) {
        act.skipActivity(view, bundle);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view) {
        act.skipActivityByFinish(view);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view, Bundle bundle) {
        act.skipActivityByFinish(view, bundle);
    }

    @Override
    public void skipActivityForResult(Class<? extends IBaseView> view, int requestCode){
        act.skipActivityForResult(view, requestCode);
    }

    @Override
    public void skipActivityForResult(Class<? extends IBaseView> view, Bundle bundle,int requestCode){
        act.skipActivityForResult(view,bundle, requestCode);
    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showLoaddingDialog() {

    }

    @Override
    public void setLoaddingMsg(String msg) {

    }

    @Override
    public LoadingAlertDialog getLoadingDialog() {
        return null;
    }



}
