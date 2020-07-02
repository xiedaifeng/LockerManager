package com.locker.manager.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class SaveDepositActivity extends BaseUrlView {

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
        return R.layout.activity_save_deposit;
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


    @OnClick({R.id.iv_left, R.id.tv_agree, R.id.iv_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_agree:
                break;
            case R.id.iv_help:
                break;
        }
    }
}
