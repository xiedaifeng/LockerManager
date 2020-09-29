package com.locker.manager.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.SaveDepositActivity;
import com.locker.manager.activity.sender.SenderDeliverActivity;
import com.locker.manager.base.BaseUrlDialog;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.utils.PhoneUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckPhoneDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private Context mContext;


    private OnItemCallBack mOnItemCallBack;

    public void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        mOnItemCallBack = onItemCallBack;
    }

    public CheckPhoneDialog(Context context,String phone) {
        super(context);
        this.mContext = context;
        tvTip.setText(String.format(tvTip.getText().toString(),phone));
    }

    public CheckPhoneDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_check_phone;
    }

    @Override
    protected void initPress() {
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_exit:
                break;
        }
    }
}
