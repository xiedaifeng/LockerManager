package com.locker.manager.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.CheckSmsRequestBean;
import com.example.http_lib.bean.GetUserInfoByMobileRequestBean;
import com.example.http_lib.bean.SendSmsRequestBean;
import com.example.http_lib.response.MobileUserInfoBean;
import com.locker.manager.R;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;


public class SaveFirstActivity extends BaseUrlView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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

    @BindView(R.id.tv_fetch_agree1)
    TextView tvFetchAgree1;

    private boolean isPostCheck = false;
    private boolean isFetchCheck = false;

    private String userName = "";


    @Override
    protected int getView() {
        return R.layout.activity_save_first;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle, System.currentTimeMillis());

        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 3));
        NumAdapter adapter = new NumAdapter(getCtx());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemCallBack(new OnItemCallBack<String>() {
            @Override
            public void onItemClick(int position, String str, int... i) {
                if (TextUtils.equals("重置", str)) {
                    if (etPostPhone.isFocused()) {
                        etPostPhone.setText("");
                    }
                    if (etFetchPhone.isFocused()) {
                        etFetchPhone.setText("");
                    }
                    if (etPostVerify.isFocused()) {
                        etPostVerify.setText("");
                    }
                    if (etFetchVerify.isFocused()) {
                        etFetchVerify.setText("");
                    }
                } else if (TextUtils.equals("回删", str)) {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.deleteString(etPostPhone);
                    }
                    if (etFetchPhone.isFocused()) {
                        EditTextInputUtils.deleteString(etFetchPhone);
                    }
                    if (etPostVerify.isFocused()) {
                        EditTextInputUtils.deleteString(etPostVerify);
                    }
                    if (etFetchVerify.isFocused()) {
                        EditTextInputUtils.deleteString(etFetchVerify);
                    }
                } else {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.addString(etPostPhone, str);
                    }
                    if (etFetchPhone.isFocused()) {
                        EditTextInputUtils.addString(etFetchPhone, str);
                    }
                    if (etPostVerify.isFocused()) {
                        EditTextInputUtils.addString(etPostVerify, str);
                    }
                    if (etFetchVerify.isFocused()) {
                        EditTextInputUtils.addString(etFetchVerify, str);
                    }
                }
            }
        });

        SoftKeyboardUtil.disableShowInput(etPostPhone);
        SoftKeyboardUtil.disableShowInput(etFetchPhone);
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
                if (PhoneUtils.isPhone(s.toString())) {
                    mPresenter.getUserInfoByMobile(s.toString(), "post");
                } else {
                    tvPostSend.setVisibility(View.GONE);
                    tvPostMsg.setVisibility(View.INVISIBLE);
                    rlPostVerify.setVisibility(View.GONE);
                }
//                if (TextUtils.isEmpty(s.toString())) {
//                    tvPostSend.setVisibility(View.GONE);
//                    tvPostMsg.setVisibility(View.INVISIBLE);
//                    rlPostVerify.setVisibility(View.GONE);
//                }
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
                    mPresenter.getUserInfoByMobile(s.toString(), "fetch");
                } else {
                    tvFetchAgree.setVisibility(View.GONE);
                    tvFetchAgree1.setVisibility(View.VISIBLE);
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
    }


    @OnClick({R.id.iv_left, R.id.tv_fetch_agree, R.id.iv_help, R.id.tv_next, R.id.tv_post_send,R.id.tv_fetch_agree1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
            case R.id.tv_next:
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
                Bundle bundle = new Bundle();
                bundle.putString(Constant.UserName, userName);
                bundle.putString(Constant.PostPhone, postPhone);
                bundle.putString(Constant.FetchPhone, fetchPhone);
                skipActivity(SaveDepositActivity.class, bundle);
                break;
            case R.id.tv_fetch_agree:
                fetchPhone = etFetchPhone.getText().toString();
                if (!PhoneUtils.isPhone(fetchPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                mPresenter.sendSms(fetchPhone);
                break;
            case R.id.tv_post_send:
                postPhone = etPostPhone.getText().toString();
                if (!PhoneUtils.isPhone(postPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                mPresenter.sendSms(postPhone);
                break;
            case R.id.tv_fetch_agree1:
                postPhone = etPostPhone.getText().toString();
                if (!PhoneUtils.isPhone(postPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                etFetchPhone.setText(postPhone);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetUserInfoByMobileRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostMsg.setVisibility(View.VISIBLE);
                    rlPostVerify.setVisibility(View.GONE);
                    tvPostSend.setVisibility(View.GONE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvPostMsg.setText("验证信息：" + userInfoBean.getRealname());
                    userName = userInfoBean.getRealname();
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchMsg.setVisibility(View.VISIBLE);
                    rlFetchVerify.setVisibility(View.GONE);
                    tvFetchAgree.setVisibility(View.GONE);
                    MobileUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), MobileUserInfoBean.class);
                    tvFetchMsg.setText("验证信息：" + userInfoBean.getRealname());
                }
            }
            if (requestCls == SendSmsRequestBean.class) {
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
            if (requestCls == GetUserInfoByMobileRequestBean.class) {
                if (TextUtils.equals("post", responseBean.getCarry().toString())) {
                    tvPostMsg.setVisibility(View.GONE);
                    rlPostVerify.setVisibility(View.VISIBLE);
                    tvPostSend.setVisibility(View.VISIBLE);
                }
                if (TextUtils.equals("fetch", responseBean.getCarry().toString())) {
                    tvFetchMsg.setVisibility(View.GONE);
                    rlFetchVerify.setVisibility(View.VISIBLE);
                    tvFetchAgree.setVisibility(View.VISIBLE);
                    tvFetchAgree1.setVisibility(View.GONE);
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
