package com.locker.manager.dialog;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http_lib.bean.PayQrCodeRequestBean;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.base.BaseUrlDialog;
import com.squareup.picasso.Picasso;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SaveOverTimeDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.view_gap)
    View viewGap;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.dialog_back_tv)
    TextView dialogBackTv;

    private Context mContext;

    private TimeCount timeCount = null;


    private IOnCountDownCallback countDownCallback;


    public void setCountDownCallback(IOnCountDownCallback countDownCallback) {
        this.countDownCallback = countDownCallback;
    }

    public SaveOverTimeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public SaveOverTimeDialog(Context context,String opencode) {
        super(context);
        this.mContext = context;
        mPresenter.getPayQrCode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(mContext),opencode);
    }

    public SaveOverTimeDialog(Context context,String opencode,String price) {
        super(context);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        tvPrice.setText(String.format("￥%s",price));
        mPresenter.getPayQrCode(PhoneInfoUtils.getLocalMacAddressFromWifiInfo(mContext),opencode);
    }

    public void setPrice(String price){
        tvPrice.setText(String.format("￥%s",price));
    }


    public void releaseTimer(){
        if(timeCount!=null){
            timeCount.cancel();
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        LogUtils.e("setOnDismissListener");
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    public void setTvTitle(String title){
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }

    public void hidePayView(){
        viewGap.setVisibility(View.GONE);
        tvCancel.setVisibility(View.GONE);
    }

    public SaveOverTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_save_overtime;
    }

    @Override
    protected void initPress() {
        timeCount = new TimeCount(60 * 1000, 1000);
        timeCount.start();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                timeCount = new TimeCount(60 * 1000, 1000);
                timeCount.start();
                hidePayView();
                tvExit.setText("取消");
                break;
            case R.id.tv_exit:
                dismiss();
                String text = tvExit.getText().toString();
                if(TextUtils.equals("返回首页",text)){
//                    ViewManager.getInstance().finishAllView();
//                    skipActivity(HomeActivity.class);

                    ViewManager.getInstance().finishOthersView(HomeActivity.class);
                }
                break;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (dialogBackTv == null) {
                return;
            }
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            dialogBackTv.setText(String.format("%ss后返回首页", value));
        }

        @Override
        public void onFinish() {
            dialogBackTv.setText(String.format("%ss后返回首页", 0));
            viewGap.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.VISIBLE);
            tvExit.setText("返回首页");
            if(countDownCallback!=null){
                countDownCallback.onFinish();
            }
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == PayQrCodeRequestBean.class){
                Picasso.with(mContext).load(responseBean.getData().replace("https","http")).into(ivQrcode);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }


    public interface IOnCountDownCallback{
        void onFinish();
    }
}
