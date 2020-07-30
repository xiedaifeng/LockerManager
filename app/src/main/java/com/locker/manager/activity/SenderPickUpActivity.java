package com.locker.manager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http_lib.bean.DeviceBoxTimeStatusRequestBean;
import com.example.http_lib.bean.OpenDeviceBoxRequestBean;
import com.locker.manager.R;
import com.locker.manager.activity.user.UserPickUpSuccessActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.dialog.SaveOverTimeDialog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class SenderPickUpActivity extends BaseUrlView {

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


    @Override
    protected int getView() {
        return R.layout.activity_sender_pick_up;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());

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
                } else if (TextUtils.equals("回删", str)) {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.deleteString(etPostPhone);
                    }
                } else {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.addString(etPostPhone, str);
                    }
                }
            }
        });

        SoftKeyboardUtil.disableShowInput(etPostPhone);
    }


    @OnClick({R.id.iv_left, R.id.tv_next, R.id.iv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
                break;
            case R.id.tv_next:
                String code = etPostPhone.getText().toString();
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showShortToast("取件码不能为空");
                    return;
                }
                mPresenter.getDeviceBoxTimeStatus(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),code);
//                skipActivity(UserPickUpSuccessActivity.class);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == OpenDeviceBoxRequestBean.class){
                Bundle bundle = new Bundle();
                bundle.putString(Constant.OpencodeKey,etPostPhone.getText().toString());
                skipActivity(UserPickUpSuccessActivity.class,bundle);
            }
            if(requestCls == DeviceBoxTimeStatusRequestBean.class){
                // TODO: 2020/7/24  判断取件的快递的状态，是否超时 超时弹出收款码弹框   没有超时则打开相应的箱门
                String code = responseBean.getData();
                if(TextUtils.equals("1",code)){ //超时请支付费用
                    SaveOverTimeDialog timeDialog = new SaveOverTimeDialog(getCtx(),etPostPhone.getText().toString());
                    timeDialog.show();
                } else if(TextUtils.equals("0",code)){
                    mPresenter.openDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()),etPostPhone.getText().toString());
                }
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
