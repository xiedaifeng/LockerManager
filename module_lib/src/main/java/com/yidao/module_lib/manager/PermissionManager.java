package com.yidao.module_lib.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yidao.module_lib.utils.LogUtils;

import io.reactivex.functions.Consumer;

public class PermissionManager {

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
    };

    private PermissionManager() {
    }

    private static class ManagerHolder {
        private static final PermissionManager instance = new PermissionManager();
    }

    public static PermissionManager getInstance() {
        return PermissionManager.ManagerHolder.instance;
    }

    private IPermissionListener mIPermissionListener;

    public void setIPermissionLiatener(IPermissionListener IPermissionListener) {
        mIPermissionListener = IPermissionListener;
    }

    @SuppressLint("CheckResult")
    public void requestPermissions(final Activity activity){
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    LogUtils.d("requestPermissions:全部权限已授权");
                    if(mIPermissionListener!=null){
                        mIPermissionListener.getPermissionSuccess();
                    }
                }else{
                    //只要有一个权限被拒绝，就会执行
                    LogUtils.d("requestPermissions:部分权限没有授权");
                    rxPermissions.request(permissions).subscribe(this);
                }
            }
        });
    }


    @SuppressLint("CheckResult")
    public void requestPermissions(final Activity activity, final String[] permissions){
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    LogUtils.d("requestPermissions:全部权限已授权");
                    if(mIPermissionListener!=null){
                        mIPermissionListener.getPermissionSuccess();
                    }
                }else{
                    //只要有一个权限被拒绝，就会执行
                    LogUtils.d("requestPermissions:部分权限没有授权");
                    rxPermissions.request(permissions).subscribe(this);
                }
            }
        });
    }

    public interface IPermissionListener{
        void getPermissionSuccess();
    }
}
