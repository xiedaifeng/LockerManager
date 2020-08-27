package com.locker.manager.activity.sender;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SaveSecondActivity;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class SenderDeliverSuccessActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_case_state)
    TextView tvCaseState;
    @BindView(R.id.tv_orderNo)
    TextView tvOrderNo;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;

    private final int countDownCode = 0x110;

    private int countDownTime = 30;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == countDownCode) {
                countDownTime--;
                tvCountDown.setText(String.format("%ss后返回首页", countDownTime));
                if (countDownTime > 0) {
                    mHandler.sendEmptyMessageDelayed(countDownCode, 1000);
                } else {
//                    ViewManager.getInstance().finishAllView();
//                    skipActivity(HomeActivity.class);

                    ViewManager.getInstance().finishOthersView(HomeActivity.class);
                }
            }
        }
    };

    @Override
    protected int getView() {
        return R.layout.activity_sender_deliver_success;
    }


    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());

        if(getIntent().hasExtra(Constant.OrderInfoKey)){
            String orderId = getIntent().getStringExtra(Constant.OrderInfoKey);
            mPresenter.getOrderInfo(orderId);
        }

        mHandler.sendEmptyMessageDelayed(countDownCode,1000);
    }


    @OnClick({R.id.iv_left, R.id.tv_pick_success, R.id.tv_hand_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_pick_success:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_hand_continue:
//                ViewManager.getInstance().finishAllView();

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                skipActivity(SaveSecondActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == GetOrderInfoRequestBean.class){
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);

                String postPhone = orderInfoBean.getCun_phone();
                String fetchPhone = orderInfoBean.getQu_phone();
                if(TextUtils.equals(postPhone,fetchPhone)){
                    llTip.setVisibility(View.VISIBLE);
                } else {
                    llTip.setVisibility(View.GONE);
                }

                tvCaseState.setText(String.format(tvCaseState.getText().toString(),orderInfoBean.getBoxno()));
                tvOrderNo.setText(String.format(tvOrderNo.getText().toString(),orderInfoBean.getOrder_no()));
                tvPhone.setText(String.format(tvPhone.getText().toString(),fetchPhone));
                tvTip.setText(String.format(tvTip.getText().toString(),orderInfoBean.getOpencode()));
            }
        } else {
            ViewManager.getInstance().finishView();
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeMessages(countDownCode);
        }
    }
}
