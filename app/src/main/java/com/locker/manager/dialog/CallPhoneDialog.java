package com.locker.manager.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.bean.DeviceInfoRequestBean;
import com.locker.manager.R;
import com.locker.manager.activity.SettingActivity;
import com.locker.manager.base.BaseUrlDialog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class CallPhoneDialog extends BaseUrlDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private Context mContext;
    private String mPhone;


    public CallPhoneDialog(Context context,String phone) {
        super(context);
        this.mContext = context;
        this.mPhone = phone;
        tvTip.setText("请用手机拨打该号码： "+phone+"  进行咨询");
    }

    public CallPhoneDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_call_phone;
    }

    @Override
    protected void initPress() {
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_exit:
                break;
        }
    }
}
