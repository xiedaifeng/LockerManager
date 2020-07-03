package com.locker.manager.activity.sender;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.SaveAppScanActivity;
import com.locker.manager.activity.SaveHelpActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.EditTextInputUtils;
import com.yidao.module_lib.utils.SoftKeyboardUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class SenderActivity extends BaseUrlView {

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
    @BindView(R.id.tv_post_msg)
    TextView tvPostMsg;
    @BindView(R.id.et_fetch_phone)
    EditText etFetchPhone;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_fetch_msg)
    TextView tvFetchMsg;
    @BindView(R.id.et_parcel_num)
    EditText etParcelNum;

    @Override
    protected int getView() {
        return R.layout.activity_save_sender;
    }

    @Override
    public void init() {
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
                    if (etFetchPhone.isFocused()) {
                        etFetchPhone.setText("");
                    }
                    if(etParcelNum.isFocused()){
                        etParcelNum.setText("");
                    }
                } else if (TextUtils.equals("回删", str)) {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.deleteString(etPostPhone);
                    }
                    if (etFetchPhone.isFocused()) {
                        EditTextInputUtils.deleteString(etFetchPhone);
                    }
                    if (etParcelNum.isFocused()) {
                        EditTextInputUtils.deleteString(etParcelNum);
                    }
                } else {
                    if (etPostPhone.isFocused()) {
                        EditTextInputUtils.addString(etPostPhone, str);
                    }
                    if (etFetchPhone.isFocused()) {
                        EditTextInputUtils.addString(etFetchPhone, str);
                    }
                    if (etParcelNum.isFocused()) {
                        EditTextInputUtils.addString(etParcelNum, str);
                    }
                }
            }
        });

        SoftKeyboardUtil.disableShowInput(etPostPhone);
        SoftKeyboardUtil.disableShowInput(etFetchPhone);
        SoftKeyboardUtil.disableShowInput(etParcelNum);
    }


    @OnClick({R.id.iv_left, R.id.tv_next, R.id.iv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveAppScanActivity.class);
                break;
            case R.id.tv_next:
                skipActivity(SenderDeliverActivity.class);
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
        }
    }

}
