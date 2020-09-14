package com.locker.manager.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.bean.DeviceInfoRequestBean;
import com.example.http_lib.bean.PayQrCodeRequestBean;
import com.locker.manager.R;
import com.locker.manager.activity.SettingActivity;
import com.locker.manager.base.BaseUrlDialog;
import com.squareup.picasso.Picasso;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ExitDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.et_user)
    TextView etUser;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    private Context mContext;

    private int requestType = 0;//  0 代表退出   1 代表设置串口号

    public ExitDialog(Context context) {
        super(context);
        this.mContext = context;
        tvTip.setText("设备唯一标识:"+PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context));
        etUser.setText(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(context));
    }

    public ExitDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_exit;
    }

    @Override
    protected void initPress() {
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit,R.id.tv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_exit:
                String pwd = etPwd.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLongToast("密码不能为空");
                    return;
                }
                requestType = 0;
                mPresenter.getDeviceInfo(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(mContext));
                break;
            case R.id.tv_setting:
                pwd = etPwd.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLongToast("密码不能为空");
                    return;
                }
                requestType = 1;
                mPresenter.getDeviceInfo(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(mContext));
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == DeviceInfoRequestBean.class){
                JSONObject object = JSON.parseObject(responseBean.getData());
                String device_password = object.getString("device_password");
                if(TextUtils.equals(etPwd.getText().toString(),device_password)){
                    dismiss();
                    if(requestType == 0){
                        ToastUtil.showLongToast("操作成功");
                        ViewManager.getInstance().AppExit();
                    } else if (requestType == 1){
                        skipActivity(SettingActivity.class);
                    }
                } else {
                    ToastUtil.showLongToast("密码错误");
                }
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
