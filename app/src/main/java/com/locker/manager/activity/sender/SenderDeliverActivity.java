package com.locker.manager.activity.sender;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.GetAllBoxDetailRequestBean;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.response.DeviceBoxDetailBean;
import com.locker.manager.R;
import com.locker.manager.activity.HomeActivity;
import com.locker.manager.activity.SaveHelpActivity;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.app.Constant;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.callback.OnItemCallBack;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneInfoUtils;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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

    private List<View> mViews = new ArrayList<>();
    private String orderId;


    @Override
    protected int getView() {
        return R.layout.activity_sender_deliver;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());

        orderId = getIntent().getStringExtra(Constant.OrderInfoKey);

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


    @OnClick({R.id.iv_left, R.id.tv_agree, R.id.iv_help, R.id.tv_last, R.id.tv_save, R.id.ll_small, R.id.ll_middle, R.id.ll_large})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
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
                mPresenter.getOrderInfo(orderId);
                // TODO: 2020/7/3 判断付费、免费和付费成功自动跳转
                Bundle bundle = new Bundle();
                bundle.putString(Constant.OrderInfoKey,orderId);
                skipActivity(SenderDeliverSuccessActivity.class,bundle);
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
        for (int i = 0; i < mViews.size(); i++) {
            mViews.get(i).setSelected(position == i);
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


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls == GetAllBoxDetailRequestBean.class){
                List<DeviceBoxDetailBean> boxDetailBeans = JSON.parseArray(responseBean.getData(), DeviceBoxDetailBean.class);
                String smallNum = "0";
                String middleNum = "0";
                String largeNum = "0";
                for(DeviceBoxDetailBean bean:boxDetailBeans){
                    if(TextUtils.equals("big",bean.getSize())){
                        tvLargePrice.setText("￥"+bean.getMoney());
                        tvLargeRemain.setText("剩余空箱"+bean.getCount());
                        tvLargeSize.setText(bean.getTitle());
                        largeNum = bean.getCount();
                    }
                    if(TextUtils.equals("medium",bean.getSize())){
                        tvMiddlePrice.setText("￥"+bean.getMoney());
                        tvMiddleRemain.setText("剩余空箱"+bean.getCount());
                        tvMiddleSize.setText(bean.getTitle());
                        middleNum = bean.getCount();
                    }
                    if(TextUtils.equals("small",bean.getSize())){
                        tvSmallPrice.setText("￥"+bean.getMoney());
                        tvSmallRemain.setText("剩余空箱"+bean.getCount());
                        tvSmallSize.setText(bean.getTitle());
                        smallNum = bean.getCount();
                    }
                }
                tvTip.setText(String.format(tvTip.getText().toString(),"xx",smallNum,middleNum,largeNum));
            }
            if(requestCls == GetOrderInfoRequestBean.class){

            }
        } else {
            ToastUtil.showShortToast(responseBean.getMessage());
        }
    }
}
