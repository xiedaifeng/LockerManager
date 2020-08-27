package com.locker.manager.activity.user;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.OrderInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SenderPickUpActivity;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class UserPickUpSuccessActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_case_state)
    TextView tvCaseState;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.tv_hot_phone)
    TextView tvHotPhone;

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
        return R.layout.activity_user_pick_up_success;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());

        String hotPhone = UserCacheHelper.getHotPhone();
        if(!TextUtils.isEmpty(hotPhone)){
            tvHotPhone.setText(String.format("如有疑问，请致电客服：%s", hotPhone));
        }

        if(getIntent().hasExtra(Constant.OrderInfoKey)){
            String orderId = getIntent().getStringExtra(Constant.OrderInfoKey);
            mPresenter.getOrderInfo(orderId);
        }

//        if(getIntent().hasExtra(Constant.OpencodeKey)){
//            String opencode = getIntent().getStringExtra(Constant.OpencodeKey);
//            String boxno = opencode.substring(0,2);
//            tvCaseState.setText(String.format("格口号：%s（已开）",boxno));
//            try {
//                SerialPortOpenSDK.getInstance().send(
//                        new CommandProtocol.Builder()
//                                .setCommand(CommandProtocol.COMMAND_OPEN)
//                                .setCommandChannel(boxno)
//                                .builder()
//                                .getBytes());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        mHandler.sendEmptyMessageDelayed(countDownCode,1000);
    }


    @OnClick({R.id.iv_left, R.id.tv_pick_success, R.id.tv_hand_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);
//

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
            case R.id.tv_pick_success:
//                ViewManager.getInstance().finishAllView();

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                skipActivity(SenderPickUpActivity.class);
                break;
            case R.id.tv_hand_continue:
//                ViewManager.getInstance().finishAllView();
//                skipActivity(HomeActivity.class);

                ViewManager.getInstance().finishOthersView(HomeActivity.class);
                break;
        }
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == GetOrderInfoRequestBean.class){
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                String boxno = orderInfoBean.getBoxno();
                tvCaseState.setText(String.format("格口号：%s（已开）",boxno));
//                try {
//                    SerialPortOpenSDK.getInstance().send(
//                            new CommandProtocol.Builder()
//                                    .setCommand(CommandProtocol.COMMAND_OPEN)
//                                    .setCommandChannel(boxno)
//                                    .builder()
//                                    .getBytes());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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
