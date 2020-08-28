package com.locker.manager.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.BackOrderRequestBean;
import com.example.http_lib.bean.DeviceBoxTimeStatusRequestBean;
import com.example.http_lib.bean.GetOrderInfoByCodeRequestBean;
import com.example.http_lib.bean.OpenDeviceBoxRequestBean;
import com.example.http_lib.bean.UpdateDeviceBoxStatusRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.locker.manager.R;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.dialog.BoxStateDialog;
import com.locker.manager.dialog.SaveOverTimeDialog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;
import com.yidao.module_lib.utils.ToastUtil;

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

    private BoxStateDialog dialog = null;

    private SaveOverTimeDialog timeDialog = null;
    private String post_no;
    private String orderId;

    private float money;

    @Override
    protected int getView() {
        return R.layout.activity_sender_pick_up;
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
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_next:
                String code = etPostPhone.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast("取件码不能为空");
                    return;
                }
                mPresenter.getOrderInfoByCode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), code);
//                mPresenter.getDeviceBoxTimeStatus(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), code);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == OpenDeviceBoxRequestBean.class) {
//                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);

//                openBoxByOpencode();

                if (!TextUtils.isEmpty(post_no)) { //post_no不为空即为快递员包裹   为空为其他包裹

                    if (dialog == null) {
                        dialog = new BoxStateDialog(this);
                    }
                    dialog.setOpenBoxId(etPostPhone.getText().toString());
                    dialog.setClickListener(new BoxStateDialog.IClickListener() {
                        @Override
                        public void openBox(String openBoxId) {
                        }
                        @Override
                        public void getBack(String openBoxId) {
                            mPresenter.backOrder(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString(),orderId);
                            LockerApplication.sTuiOrderId = orderId;
                        }
                    });
                    dialog.show();
                }
            }
            if (requestCls == DeviceBoxTimeStatusRequestBean.class) {
                // TODO: 2020/7/24  判断取件的快递的状态，是否超时 超时弹出收款码弹框   没有超时则打开相应的箱门
                String code = responseBean.getData();
                if (TextUtils.equals("1", code)) { //超时请支付费用
                    if (timeDialog == null) {
                        timeDialog = new SaveOverTimeDialog(getCtx(), etPostPhone.getText().toString(),money+"");
                    }
                    timeDialog.hidePayView();
                    timeDialog.show();
                } else if (TextUtils.equals("0", code)) {
                    mPresenter.openDeviceBox(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString());
                }
            }
            if (requestCls == BackOrderRequestBean.class) {

            }
            if (requestCls == UpdateDeviceBoxStatusRequestBean.class) {
            }
            if (requestCls == GetOrderInfoByCodeRequestBean.class) {
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                post_no = orderInfoBean.getPost_no();
                orderId = orderInfoBean.getId();
                LockerApplication.sQuOrderId = orderId;
                String chao_hour = TextUtils.isEmpty(orderInfoBean.getChao_hour())?"0":orderInfoBean.getChao_hour();
                String chao_money = TextUtils.isEmpty(orderInfoBean.getChao_money())?"0":orderInfoBean.getChao_money();
                money = Float.parseFloat(chao_hour)*Float.parseFloat(chao_money);
                mPresenter.getDeviceBoxTimeStatus(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()), etPostPhone.getText().toString());
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDialog != null && timeDialog.isShowing()) {
            timeDialog.dismiss();
        }
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
