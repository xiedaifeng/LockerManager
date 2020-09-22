package com.locker.manager.dialog;

import android.content.Context;
import android.view.View;

import com.example.http_lib.bean.BackOrderRequestBean;
import com.locker.manager.R;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.base.BaseUrlDialog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.OnClick;

public class SecondaryDialog extends BaseUrlDialog {

    private Context mContext;

    private String openCode;

    private String orderId;

    public SecondaryDialog(Context context,String opencode,String orderId) {
        super(context);
        this.mContext = context;
        this.openCode = opencode;
        this.orderId = orderId;
    }

    public SecondaryDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_secondary;
    }

    @Override
    protected void initPress() {
    }

    @OnClick({R.id.tv_cancel, R.id.tv_sure})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_exit:
                mPresenter.backOrder(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), openCode,orderId);
                LockerApplication.sTuiOrderId = orderId;
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == BackOrderRequestBean.class){

            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
