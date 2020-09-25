package com.locker.manager.activity.sender;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.CheckSmsRequestBean;
import com.example.http_lib.bean.GetOrderInfoByPostNoRequestBean;
import com.example.http_lib.bean.GetUserInfoByMobileRequestBean;
import com.example.http_lib.bean.SendSmsRequestBean;
import com.example.http_lib.response.MobileUserInfoBean;
import com.example.http_lib.response.OrderInfoBean;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SaveHelpActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.callback.OnItemClickListener;
import com.locker.manager.manager.VibratorManager;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class SenderActivity extends BaseUrlView {

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
    @BindView(R.id.tv_fetch_agree)
    TextView tvFetchAgree;
    @BindView(R.id.tv_fetch_msg)
    TextView tvFetchMsg;
    @BindView(R.id.et_parcel_num)
    EditText etParcelNum;

    @BindView(R.id.et_post_verify)
    EditText etPostVerify;
    @BindView(R.id.et_fetch_verify)
    EditText etFetchVerify;
    @BindView(R.id.tv_post_send)
    TextView tvPostSend;

    @BindView(R.id.rl_post_verify)
    RelativeLayout rlPostVerify;
    @BindView(R.id.tv_post_verify)
    TextView tvPostVerify;
    @BindView(R.id.rl_fetch_verify)
    RelativeLayout rlFetchVerify;
    @BindView(R.id.tv_fetch_verify)
    TextView tvFetchVerify;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_title_count_down)
    TextView mTvCountDown;

    private boolean isPostCheck = false;
    private boolean isFetchCheck = false;

    private String userName = "";

    private boolean isLongClickEnable;

    private final int longClickDeleteCode = 0x113;


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
        return R.layout.activity_save_sender;
    }

    @Override
    protected boolean isFinishToHome() {
        return false;
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
    public void init() {
        setCountDownTextView(mTvCountDown);
        setCurrentTime(tvTitle,System.currentTimeMillis());

        SoftKeyboardUtil.disableShowInput(etPostPhone);
        SoftKeyboardUtil.disableShowInput(etFetchPhone);
        SoftKeyboardUtil.disableShowInput(etParcelNum);
        SoftKeyboardUtil.disableShowInput(etPostVerify);
        SoftKeyboardUtil.disableShowInput(etFetchVerify);

        etPostPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(PhoneUtils.isPhone(s.toString())){
                    mPresenter.getUserInfoByMobile(s.toString(),"post");
                } else {
                    tvPostSend.setVisibility(View.GONE);
                    tvPostMsg.setVisibility(View.INVISIBLE);
                    rlPostVerify.setVisibility(View.GONE);
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
                if(PhoneUtils.isPhone(s.toString())){
                    mPresenter.getUserInfoByMobile(s.toString(),"fetch");
                } else {
                    tvFetchAgree.setVisibility(View.GONE);
                    tvFetchMsg.setVisibility(View.INVISIBLE);
                    rlFetchVerify.setVisibility(View.GONE);
                }
            }
        });
        etPostVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 4){
                    mPresenter.checkSmsCode(etPostPhone.getText().toString(),s.toString(),"post");
                } else {
                    tvPostVerify.setVisibility(View.GONE);
                }
            }
        });
        etFetchVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 4){
                    mPresenter.checkSmsCode(etFetchPhone.getText().toString(),s.toString(),"fetch");
                } else {
                    tvFetchVerify.setVisibility(View.GONE);
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


    @OnClick({R.id.iv_left, R.id.tv_next, R.id.iv_help,R.id.tv_fetch_agree,R.id.tv_post_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
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
                if(rlPostVerify.getVisibility() == View.VISIBLE){
                    if (TextUtils.isEmpty(etPostVerify.getText().toString())) {
                        ToastUtil.showShortToast("验证码不能为空");
                        return;
                    }
                    if(!isPostCheck){
                        ToastUtil.showShortToast("验证失败");
                        return;
                    }
                }
                if(rlFetchVerify.getVisibility() == View.VISIBLE){
                    if (TextUtils.isEmpty(etFetchVerify.getText().toString())) {
                        ToastUtil.showShortToast("验证码不能为空");
                        return;
                    }
                    if(!isFetchCheck){
                        ToastUtil.showShortToast("验证失败");
                        return;
                    }
                }
                String postNo = etParcelNum.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString(Constant.UserName,userName);
                bundle.putString(Constant.PostPhone,postPhone);
                bundle.putString(Constant.FetchPhone,fetchPhone);
                bundle.putString(Constant.PostNo,postNo);
                skipActivity(SenderDeliverActivity.class,bundle);

//                mPresenter.getOrderInfoByPostNo(postNo);
//                skipActivity(SenderDeliverActivity.class);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
            case R.id.tv_fetch_agree:
                if(!isDeviceOnLine()){
                    return;
                }
                VibratorManager.getInstance().vibrate(50);
                fetchPhone = etFetchPhone.getText().toString();
                if (!PhoneUtils.isPhone(fetchPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                mPresenter.sendSms(fetchPhone);
                break;
            case R.id.tv_post_send:
                if(!isDeviceOnLine()){
                    return;
                }
                VibratorManager.getInstance().vibrate(50);
                postPhone = etPostPhone.getText().toString();
                if (!PhoneUtils.isPhone(postPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                mPresenter.sendSms(postPhone);
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
        } else if (etParcelNum.isFocused()) {
            EditTextInputUtils.addString(etParcelNum, str);
        } else if (etPostVerify.isFocused()) {
            EditTextInputUtils.addString(etPostVerify, str);
        } else if (etFetchVerify.isFocused()) {
            EditTextInputUtils.addString(etFetchVerify, str);
        }
    }

    private void reSetString() {
        if (etPostPhone.isFocused()) {
            etPostPhone.setText("");
        } else if (etFetchPhone.isFocused()) {
            etFetchPhone.setText("");
        } else if(etParcelNum.isFocused()){
            etParcelNum.setText("");
        } else if (etPostVerify.isFocused()) {
            etPostVerify.setText("");
        } else if (etFetchVerify.isFocused()) {
            etFetchVerify.setText("");
        }
    }

    private boolean deleteString() {
        if (etPostPhone.isFocused()) {
            EditTextInputUtils.deleteString(etPostPhone);
            return true;
        } else if (etFetchPhone.isFocused()) {
            EditTextInputUtils.deleteString(etFetchPhone);
            return true;
        } else if (etParcelNum.isFocused()) {
            EditTextInputUtils.deleteString(etParcelNum);
            return true;
        } else if (etPostVerify.isFocused()) {
            EditTextInputUtils.deleteString(etPostVerify);
            return true;
        } else if (etFetchVerify.isFocused()) {
            EditTextInputUtils.deleteString(etFetchVerify);
            return true;
        }
        return false;
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == GetUserInfoByMobileRequestBean.class){
                if(TextUtils.equals("post",responseBean.getCarry().toString())){
                    tvPostMsg.setVisibility(View.VISIBLE);
                    rlPostVerify.setVisibility(View.GONE);
                    tvPostSend.setVisibility(View.GONE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvPostMsg.setText("验证信息："+userInfoBean.getRealname());
                    userName = userInfoBean.getRealname();
                }
                if(TextUtils.equals("fetch",responseBean.getCarry().toString())){
                    tvFetchMsg.setVisibility(View.VISIBLE);
                    rlFetchVerify.setVisibility(View.GONE);
                    tvFetchAgree.setVisibility(View.GONE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvFetchMsg.setText("验证信息："+userInfoBean.getRealname());
                }
            }
            if(requestCls == SendSmsRequestBean.class){
                ToastUtil.showShortToast("验证码发送成功");
            }
            if (requestCls == CheckSmsRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostVerify.setVisibility(View.VISIBLE);
                    tvPostVerify.setText("验证成功");
                    isPostCheck = true;
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchVerify.setVisibility(View.VISIBLE);
                    tvFetchVerify.setText("验证成功");
                    isFetchCheck = true;
                }
            }
        } else {
            if(requestCls == GetUserInfoByMobileRequestBean.class){
                if(TextUtils.equals("post",responseBean.getCarry().toString())){
                    tvPostMsg.setVisibility(View.GONE);
                    rlPostVerify.setVisibility(View.VISIBLE);
                    tvPostSend.setVisibility(View.VISIBLE);
                }
                if(TextUtils.equals("fetch",responseBean.getCarry().toString())){
                    tvFetchMsg.setVisibility(View.GONE);
                    rlFetchVerify.setVisibility(View.VISIBLE);
                    tvFetchAgree.setVisibility(View.VISIBLE);
//                    tvFetchAgree.setText("发送验证码");
                }
                ToastUtil.showShortToast("非平台用户，需向对方手机发送验证码");
            }
            if (requestCls == CheckSmsRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostVerify.setVisibility(View.VISIBLE);
                    tvPostVerify.setText("验证失败");
                    isPostCheck = false;
                    tvPostSend.setText("重新发送");
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchVerify.setVisibility(View.VISIBLE);
                    tvFetchVerify.setText("验证失败");
                    isFetchCheck = false;
                    tvFetchAgree.setText("重新发送");
                }
            }
            if (requestCls != GetUserInfoByMobileRequestBean.class) {
                ToastUtil.showShortToast(responseBean.getMessage());
            }
        }
    }

}
