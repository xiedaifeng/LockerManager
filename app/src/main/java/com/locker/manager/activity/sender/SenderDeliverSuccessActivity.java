package com.locker.manager.activity.sender;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.http_lib.bean.GetOrderInfoRequestBean;
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

    private final int countDownCode = 0x110;

    private int countDownTime = 60;

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
                    ViewManager.getInstance().finishAllView();
                    skipActivity(HomeActivity.class);
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
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
                break;
            case R.id.tv_pick_success:
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
                break;
            case R.id.tv_hand_continue:
                ViewManager.getInstance().finishAllView();
                skipActivity(SenderActivity.class);
//                skipActivity(SenderDeliverAndBackActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == GetOrderInfoRequestBean.class){

            }
        } else {
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
