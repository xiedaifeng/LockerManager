package com.locker.manager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.GetUserInfoByMobileRequestBean;
import com.example.http_lib.bean.SendSmsRequestBean;
import com.example.http_lib.response.MobileUserInfoBean;
import com.locker.manager.R;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.dialog.CheckPhoneDialog;
import com.locker.manager.manager.VibratorManager;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;


public class SaveFirstActivity extends BaseUrlView {

    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_post_phone)
    EditText etPostPhone;
    @BindView(R.id.tv_post_msg)
    TextView tvPostMsg;

    @BindView(R.id.et_fetch_phone)
    EditText etFetchPhone;
    @BindView(R.id.tv_fetch_msg)
    TextView tvFetchMsg;
    @BindView(R.id.tv_fetch_agree1)
    TextView tvFetchAgree1;

    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_title_count_down)
    TextView mTvCountDown;

    private String userName = "";

    private boolean isLongClickEnable;

    private final int longClickDeleteCode = 0x112;


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == longClickDeleteCode){
                deleteString();
            }
        }
    };

    @Override
    protected int getView() {
        return R.layout.activity_save_first;
    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    protected int getCountDownTime() {
        return 60;
    }

    @Override
    protected boolean isFinishToHome() {
        return false;
    }

    @Override
    public void init() {
        setCountDownTextView(mTvCountDown);
        setCurrentTime(tvTitle, System.currentTimeMillis());

        SoftKeyboardUtil.disableShowInput(etPostPhone);
        SoftKeyboardUtil.disableShowInput(etFetchPhone);

        etPostPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
//                if(PhoneUtils.isPhone(etFetchPhone.getText().toString()) && PhoneUtils.isPhone(s.toString()) && !TextUtils.equals(etFetchPhone.getText().toString(),s.toString())) {
//                    tvFetchAgree1.setText("拨打该号码");
//                } else {
//                    tvFetchAgree1.setText("与存件人一致");
//                }

                if (PhoneUtils.isPhone(s.toString())) {
                    mPresenter.getUserInfoByMobile(s.toString(), "post");
                } else {
                    tvPostMsg.setVisibility(View.INVISIBLE);
                }
            }
        });
        etFetchPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(PhoneUtils.isPhone(etPostPhone.getText().toString()) && PhoneUtils.isPhone(s.toString()) && !TextUtils.equals(etPostPhone.getText().toString(),s.toString())) {
//                    tvFetchAgree1.setText("拨打该号码");
//                } else {
//                    tvFetchAgree1.setText("与存件人一致");
//                }

                if (PhoneUtils.isPhone(s.toString())) {
                    mPresenter.getUserInfoByMobile(s.toString(), "fetch");
                } else {
                    tvFetchMsg.setVisibility(View.INVISIBLE);
                }
            }
        });

        tvDelete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        isLongClickEnable = false;
                        if(mHandler!=null){
                            mHandler.removeMessages(longClickDeleteCode);
                        }
                        break;
                }
                return false;
            }
        });

        tvDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isLongClickEnable = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isLongClickEnable){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(mHandler!=null){
                                mHandler.sendEmptyMessage(longClickDeleteCode);
                            }
                        }
                    }
                }).start();
                return false;
            }
        });
    }


    @OnClick({R.id.iv_left, R.id.iv_help, R.id.tv_next , R.id.tv_fetch_agree1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.iv_help:
                VibratorManager.getInstance().vibrate(50);
                skipActivity(SaveHelpActivity.class);
                break;
            case R.id.tv_next:
                if(!isDeviceOnLine()){
                    return;
                }
                VibratorManager.getInstance().vibrate(50);
                String postPhone = etPostPhone.getText().toString();
                String fetchPhone = etFetchPhone.getText().toString();
                if (!PhoneUtils.isPhone(postPhone)) {
                    ToastUtil.showShortToast("请输入正确的存件手机号");
                    return;
                }
                if (!PhoneUtils.isPhone(fetchPhone)) {
                    ToastUtil.showShortToast("请输入正确的取件手机号");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constant.UserName, userName);
                bundle.putString(Constant.PostPhone, postPhone);
                bundle.putString(Constant.FetchPhone, fetchPhone);
                skipActivity(SaveDepositActivity.class, bundle);
                break;
            case R.id.tv_fetch_agree1:
                if(!isDeviceOnLine()){
                    return;
                }
                VibratorManager.getInstance().vibrate(50);
                String fetchAgree = tvFetchAgree1.getText().toString();
                if(TextUtils.equals("与存件人一致",fetchAgree)){
                    postPhone = etPostPhone.getText().toString();
                    if (!PhoneUtils.isPhone(postPhone)) {
                        ToastUtil.showShortToast("请输入正确的手机号");
                        return;
                    }
                    etFetchPhone.setText(postPhone);
                }
                break;
        }
    }

    @OnClick({R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_reset, R.id.tv_delete})
    public void onViewNumClicked(View view) {
        VibratorManager.getInstance().vibrate(50);
        switch (view.getId()) {
            case R.id.tv_0:
                addString("0");
                break;
            case R.id.tv_1:
                addString("1");
                break;
            case R.id.tv_2:
                addString("2");
                break;
            case R.id.tv_3:
                addString("3");
                break;
            case R.id.tv_4:
                addString("4");
                break;
            case R.id.tv_5:
                addString("5");
                break;
            case R.id.tv_6:
                addString("6");
                break;
            case R.id.tv_7:
                addString("7");
                break;
            case R.id.tv_8:
                addString("8");
                break;
            case R.id.tv_9:
                addString("9");
                break;
            case R.id.tv_reset:
                reSetString();
                break;
            case R.id.tv_delete:
                deleteString();
                break;
        }
    }

    private void addString(String str) {
        if (etPostPhone.isFocused()) {
            EditTextInputUtils.addString(etPostPhone, str);
        } else if (etFetchPhone.isFocused()) {
            EditTextInputUtils.addString(etFetchPhone, str);
        }
    }

    private void reSetString() {
        if (etPostPhone.isFocused()) {
            etPostPhone.setText("");
        } else if (etFetchPhone.isFocused()) {
            etFetchPhone.setText("");
        }
    }

    private boolean deleteString() {
        if (etPostPhone.isFocused()) {
            EditTextInputUtils.deleteString(etPostPhone);
            return true;
        } else if (etFetchPhone.isFocused()) {
            EditTextInputUtils.deleteString(etFetchPhone);
            return true;
        }
        return false;
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetUserInfoByMobileRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostMsg.setVisibility(View.VISIBLE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvPostMsg.setText("验证信息：" + userInfoBean.getRealname());
                    userName = userInfoBean.getRealname();
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchMsg.setVisibility(View.VISIBLE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvFetchMsg.setText("验证信息：" + userInfoBean.getRealname());
                }
            }
            if (requestCls == SendSmsRequestBean.class) {
                ToastUtil.showShortToast("验证码发送成功");
            }
        } else {
            if (requestCls == GetUserInfoByMobileRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostMsg.setVisibility(View.INVISIBLE);
                    CheckPhoneDialog dialog = new CheckPhoneDialog(getCtx(),etPostPhone.getText().toString());
                    dialog.show();
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchMsg.setVisibility(View.INVISIBLE);
                    CheckPhoneDialog dialog = new CheckPhoneDialog(getCtx(),etFetchPhone.getText().toString());
                    dialog.show();
                }
//                ToastUtil.showLongToast("非平台用户，需向对方手机发送验证码");
            }
            if (requestCls != GetUserInfoByMobileRequestBean.class) {
                ToastUtil.showLongToast(responseBean.getMessage());
            }
        }
    }
}
