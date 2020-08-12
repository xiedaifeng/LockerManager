package com.locker.manager.activity.sender;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.CreateOrderRequestBean;
import com.example.http_lib.bean.GetAllBoxDetailRequestBean;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.DeviceBoxDetailBean;
import com.example.http_lib.response.OrderInfoBean;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SaveHelpActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.Constant;
import com.locker.manager.app.LockerApplication;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.locker.manager.command.CommandProtocol;
import com.locker.manager.dialog.SaveOverTimeDialog;
import com.qiao.serialport.SerialPortOpenSDK;
import com.qiao.serialport.listener.SerialPortMessageListener;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;


public class SenderDeliverActivity extends BaseUrlView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_small_price)
    TextView tvSmallPrice;
    @BindView(R.id.tv_small_remain)
    TextView tvSmallRemain;
    @BindView(R.id.ll_small)
    LinearLayout llSmall;
    @BindView(R.id.tv_middle_price)
    TextView tvMiddlePrice;
    @BindView(R.id.tv_middle_remain)
    TextView tvMiddleRemain;
    @BindView(R.id.ll_middle)
    LinearLayout llMiddle;
    @BindView(R.id.tv_large_price)
    TextView tvLargePrice;
    @BindView(R.id.tv_large_remain)
    TextView tvLargeRemain;
    @BindView(R.id.ll_large)
    LinearLayout llLarge;
    @BindView(R.id.tv_small_size)
    TextView tvSmallSize;
    @BindView(R.id.tv_middle_size)
    TextView tvMiddleSize;
    @BindView(R.id.tv_large_size)
    TextView tvLargeSize;

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    private List<View> mViews = new ArrayList<>();

    private String orderId;
    private String opencode;

    private String postPhone;
    private String fetchPhone;
    private String postNo;
    private String userName;

    private int mPosition = 0;

    private int smallBoxNum = 0;
    private int middleBoxNum = 0;
    private int largeBoxNum = 0;

    private final int countDownCode = 0x112;

    private int countDownTime = 30;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == countDownCode) {
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
        return R.layout.activity_sender_deliver;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle, System.currentTimeMillis());

        userName = getIntent().getStringExtra(Constant.UserName);
        postPhone = getIntent().getStringExtra(Constant.PostPhone);
        fetchPhone = getIntent().getStringExtra(Constant.FetchPhone);
        postNo = getIntent().getStringExtra(Constant.PostNo);

        mPresenter.getAllBoxDetail(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx()));

        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 3));
        NumAdapter adapter = new NumAdapter(getCtx());
        recyclerView.setAdapter(adapter);

        mViews.add(llSmall);
        mViews.add(llMiddle);
        mViews.add(llLarge);

        chooseCase(0);

        adapter.setOnItemCallBack(new OnItemCallBack<String>() {
            @Override
            public void onItemClick(int position, String str, int... i) {
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_help, R.id.tv_last, R.id.tv_save, R.id.ll_small, R.id.ll_middle, R.id.ll_large})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
            case R.id.tv_last:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_save:
                if (mPosition == 0 && smallBoxNum == 0) {
                    ToastUtil.showShortToast("暂无相应型号的箱子可用");
                    return;
                }
                if (mPosition == 1 && middleBoxNum == 0) {
                    ToastUtil.showShortToast("暂无相应型号的箱子可用");
                    return;
                }
                if (mPosition == 2 && largeBoxNum == 0) {
                    ToastUtil.showShortToast("暂无相应型号的箱子可用");
                    return;
                }
                if (TextUtils.isEmpty(opencode)) {
                    String boxSize = mPosition == 0 ? "small" : mPosition == 1 ? "medium" : "big";
                    CreateOrderRequestBean requestBean = new CreateOrderRequestBean();
                    requestBean.cun_phone = postPhone;
                    requestBean.qu_phone = fetchPhone;
                    requestBean.device_id = PhoneInfoUtils.getLocalMacAddressFromWifiInfo(getCtx());
                    requestBean.boxsize = boxSize;
                    requestBean.post_no = TextUtils.isEmpty(postNo) ? "123456" : postNo;
                    mPresenter.createOrder(requestBean);

                    mHandler.sendEmptyMessageDelayed(countDownCode, 1000);
                } else {
                    SaveOverTimeDialog timeDialog = new SaveOverTimeDialog(getCtx(), opencode);
                    timeDialog.hidePayView();
                    timeDialog.setTvTitle("包裹订单创建成功\n请扫描下方二维码支付寄存包裹");
                    timeDialog.show();
                }

//                mPresenter.getOrderInfo(orderId);
//                // TODO: 2020/7/3 判断付费、免费和付费成功自动跳转
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.OrderInfoKey,orderId);
//                skipActivity(SenderDeliverSuccessActivity.class,bundle);
                break;
            case R.id.ll_small:
                chooseCase(0);
                break;
            case R.id.ll_middle:
                chooseCase(1);
                break;
            case R.id.ll_large:
                chooseCase(2);
                break;
        }
    }

    private void chooseCase(int position) {
        mPosition = position;
        for (int i = 0; i < mViews.size(); i++) {
            mViews.get(i).setSelected(position == i);
        }

        tvSmallPrice.setTextColor(getResources().getColor(position == 0 ? R.color.color_0ED26B : R.color.color_999999));
        tvSmallSize.setTextColor(getResources().getColor(position == 0 ? R.color.color_0ED26B : R.color.color_999999));
        tvSmallRemain.setTextColor(getResources().getColor(position == 0 ? R.color.color_0ED26B : R.color.color_999999));

        tvMiddlePrice.setTextColor(getResources().getColor(position == 1 ? R.color.color_0ED26B : R.color.color_999999));
        tvMiddleSize.setTextColor(getResources().getColor(position == 1 ? R.color.color_0ED26B : R.color.color_999999));
        tvMiddleRemain.setTextColor(getResources().getColor(position == 1 ? R.color.color_0ED26B : R.color.color_999999));

        tvLargePrice.setTextColor(getResources().getColor(position == 2 ? R.color.color_0ED26B : R.color.color_999999));
        tvLargeSize.setTextColor(getResources().getColor(position == 2 ? R.color.color_0ED26B : R.color.color_999999));
        tvLargeRemain.setTextColor(getResources().getColor(position == 2 ? R.color.color_0ED26B : R.color.color_999999));
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetAllBoxDetailRequestBean.class) {
                List<DeviceBoxDetailBean> boxDetailBeans = JSON.parseArray(responseBean.getData(), DeviceBoxDetailBean.class);
                for (DeviceBoxDetailBean bean : boxDetailBeans) {
                    if (TextUtils.equals("big", bean.getSize())) {
                        tvLargePrice.setText("￥" + bean.getMoney());
                        tvLargeRemain.setText("剩余空箱" + bean.getCount());
                        tvLargeSize.setText(bean.getTitle());

                        largeBoxNum = Integer.parseInt(bean.getCount());
                    }
                    if (TextUtils.equals("medium", bean.getSize())) {
                        tvMiddlePrice.setText("￥" + bean.getMoney());
                        tvMiddleRemain.setText("剩余空箱" + bean.getCount());
                        tvMiddleSize.setText(bean.getTitle());

                        middleBoxNum = Integer.parseInt(bean.getCount());
                    }
                    if (TextUtils.equals("small", bean.getSize())) {
                        tvSmallPrice.setText("￥" + bean.getMoney());
                        tvSmallRemain.setText("剩余空箱" + bean.getCount());
                        tvSmallSize.setText(bean.getTitle());

                        smallBoxNum = Integer.parseInt(bean.getCount());
                    }
                }
                tvTip.setText(String.format(tvTip.getText().toString(), userName, smallBoxNum, middleBoxNum, largeBoxNum));
            }
            if (requestCls == CreateOrderRequestBean.class) {
                OrderInfoBean orderInfoBean = JSON.parseObject(responseBean.getData(), OrderInfoBean.class);
                opencode = orderInfoBean.getOpencode();
                orderId = orderInfoBean.getId();
                LockerApplication.sOrderId = orderId;

                SaveOverTimeDialog timeDialog = new SaveOverTimeDialog(getCtx(), opencode);
                timeDialog.hidePayView();
                timeDialog.setTvTitle("包裹订单创建成功\n请扫描下方二维码支付寄存包裹");
                timeDialog.show();
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }

//    @Override
//    public void onMessage(int error, String errorMessage, byte[] data) throws Exception {
//        CommandProtocol commandProtocol = new CommandProtocol.Builder().setBytes(data).parseMessage();
//        if(CommandProtocol.COMMAND_OPEN_RESPONSE == commandProtocol.getCommand()){
//                mPresenter.getOrderInfo(orderId);
////                 TODO: 2020/7/3 判断付费、免费和付费成功自动跳转
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.OrderInfoKey,orderId);
//                skipActivity(SenderDeliverSuccessActivity.class,bundle);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(countDownCode);
        }
    }
}
