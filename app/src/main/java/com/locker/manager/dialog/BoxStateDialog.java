package com.locker.manager.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.base.BaseUrlDialog;
import com.locker.manager.command.CommandProtocol;
import com.qiao.serialport.SerialPortOpenSDK;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class BoxStateDialog extends BaseUrlDialog {

    @BindView(R.id.dialog_close_btn)
    ImageView dialogCloseBtn;
    @BindView(R.id.dialog_title)
    RelativeLayout dialogTitle;
    @BindView(R.id.dialog_content_tv)
    TextView dialogContentTv;
    @BindView(R.id.dialog_back_tv)
    TextView dialogBackTv;
    @BindView(R.id.dialog_content_ll)
    RelativeLayout dialogContentLl;
    @BindView(R.id.dialog_box_state_tv)
    TextView dialogBoxStateTv;
    @BindView(R.id.dialog_open_box_tv)
    TextView dialogOpenBoxTv;
    @BindView(R.id.dialog_finisn_tv)
    TextView dialogFinisnTv;
    @BindView(R.id.dialog_goback_tv)
    TextView dialogGobackTv;

    TimeCount timeCount = null;

    private IClickListener clickListener;

    public void setClickListener(IClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BoxStateDialog(Context context) {
        super(context);
        mContext = context;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        LogUtils.e("setOnDismissListener");
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    private String openBoxId="";

    public void setOpenBoxId(String openBoxId) {
        this.openBoxId = openBoxId;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timeCount != null) {
            timeCount.onFinish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_open_box;
    }

    @Override
    protected void initPress() {
        timeCount = new TimeCount(60 * 1000, 1000);
        timeCount.start();
    }

    @OnClick({R.id.dialog_open_box_tv, R.id.dialog_finisn_tv, R.id.dialog_goback_tv,R.id.dialog_close_btn})
    public void onClick(View view) {
        if (timeCount != null) {
            timeCount.onFinish();
        }
        dismiss();
        switch (view.getId()) {
            case R.id.dialog_open_box_tv:
                if(clickListener!=null){
                    clickListener.openBox(openBoxId);
                }
                break;
            case R.id.dialog_goback_tv:
//                if (TextUtils.isEmpty(openBoxId)||openBoxId.length()<=1){
//                    return;
//                }
//                String boxno = openBoxId.substring(0,2);
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

                if(clickListener!=null){
                    clickListener.getBack(openBoxId);
                }
                break;
            case R.id.dialog_finisn_tv:
            case R.id.dialog_close_btn:
                ViewManager.getInstance().finishAllView();
                Intent intent1 = new Intent(mContext, HomeActivity.class);
                mContext.startActivity(intent1);
                SoftKeyboardUtil.hideSoftKeyboard((Activity) mContext);
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
            dismiss();
            ViewManager.getInstance().finishAllView();
            Intent intent1 = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent1);
            SoftKeyboardUtil.hideSoftKeyboard((Activity) mContext);
        }
    }


    public interface IClickListener{
        void openBox(String openBoxId);
        void getBack(String openBoxId);
    }

}
