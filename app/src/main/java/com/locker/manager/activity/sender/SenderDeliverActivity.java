package com.locker.manager.activity.sender;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.SaveAppScanActivity;
import com.locker.manager.activity.SaveHelpActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.manager.ViewManager;

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


    @Override
    protected int getView() {
        return R.layout.activity_sender_deliver;
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 3));
        NumAdapter adapter = new NumAdapter(getCtx());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemCallBack(new OnItemCallBack<String>() {
            @Override
            public void onItemClick(int position, String str, int... i) {
//                if (TextUtils.equals("重置", str)) {
//                    if (etPostPhone.isFocused()) {
//                        etPostPhone.setText("");
//                    }
//                    if (etFetchPhone.isFocused()) {
//                        etFetchPhone.setText("");
//                    }
//                } else if (TextUtils.equals("回删", str)) {
//                    if (etPostPhone.isFocused()) {
//                        EditTextInputUtils.deleteString(etPostPhone);
//                    }
//                    if (etFetchPhone.isFocused()) {
//                        EditTextInputUtils.deleteString(etFetchPhone);
//                    }
//                } else {
//                    if (etPostPhone.isFocused()) {
//                        EditTextInputUtils.addString(etPostPhone, str);
//                    }
//                    if (etFetchPhone.isFocused()) {
//                        EditTextInputUtils.addString(etFetchPhone, str);
//                    }
//                }
            }
        });
    }


    @OnClick({R.id.iv_left, R.id.tv_agree, R.id.iv_help,R.id.tv_last,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(SaveAppScanActivity.class);
                break;
            case R.id.tv_agree:
                break;
            case R.id.iv_help:
                skipActivity(SaveHelpActivity.class);
                break;
            case R.id.tv_last:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_save:
                // TODO: 2020/7/3 判断付费、免费和付费成功自动跳转
                skipActivity(SenderDeliverSuccessActivity.class);
                break;
        }
    }
}
