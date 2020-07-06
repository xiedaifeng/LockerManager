package com.locker.manager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.activity.sender.SenderPickUpSuccessActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.manager.ViewManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    private List<View> mViews = new ArrayList<>();

    @Override
    protected int getView() {
        return R.layout.activity_save_deposit;
    }

    @Override
    public void init() {
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


    @OnClick({R.id.iv_left, R.id.tv_agree, R.id.iv_help, R.id.tv_last, R.id.tv_save,R.id.ll_small, R.id.ll_middle, R.id.ll_large})
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
                skipActivity(SenderPickUpSuccessActivity.class);
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

    private void chooseCase(int position){
        for(int i=0;i<mViews.size();i++){
            mViews.get(i).setSelected(position==i);
        }

        tvSmallPrice.setTextColor(getResources().getColor(position==0?R.color.color_0ED26B:R.color.color_999999));
        tvSmallSize.setTextColor(getResources().getColor(position==0?R.color.color_0ED26B:R.color.color_999999));
        tvSmallRemain.setTextColor(getResources().getColor(position==0?R.color.color_0ED26B:R.color.color_999999));

        tvMiddlePrice.setTextColor(getResources().getColor(position==1?R.color.color_0ED26B:R.color.color_999999));
        tvMiddleSize.setTextColor(getResources().getColor(position==1?R.color.color_0ED26B:R.color.color_999999));
        tvMiddleRemain.setTextColor(getResources().getColor(position==1?R.color.color_0ED26B:R.color.color_999999));

        tvLargePrice.setTextColor(getResources().getColor(position==2?R.color.color_0ED26B:R.color.color_999999));
        tvLargeSize.setTextColor(getResources().getColor(position==2?R.color.color_0ED26B:R.color.color_999999));
        tvLargeRemain.setTextColor(getResources().getColor(position==2?R.color.color_0ED26B:R.color.color_999999));

    }
}
