package com.locker.manager.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http_lib.bean.PayQrCodeRequestBean;
import com.locker.manager.R;
import com.locker.manager.base.BaseUrlDialog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.CommonGlideUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SaveOverTimeDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    private Context mContext;

    public SaveOverTimeDialog(Context context,String opencode) {
        super(context);
        this.mContext = context;
        mPresenter.getPayQrCode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(mContext),opencode);
    }

    public SaveOverTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_save_overtime;
    }

    @Override
    protected void initPress() {
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_exit:
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == PayQrCodeRequestBean.class){
                CommonGlideUtils.showImageView(mContext,responseBean.getData(),ivQrcode);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
