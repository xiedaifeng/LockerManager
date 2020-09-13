package com.locker.manager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.locker.manager.R;
import com.locker.manager.callback.OnItemCallBack;
import com.qiao.serialport.SerialPortOpenSDK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class SerialAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private Context mContext;

    private OnItemCallBack mOnItemCallBack;

    public void setOnItemCallBack(OnItemCallBack<String> onItemCallBack) {
        mOnItemCallBack = onItemCallBack;
    }

    public SerialAdapter(Context context) {
        super(R.layout.item_serial);
        this.mContext = context;

//        List<String> list = new ArrayList<>();
//        for(int i=0;i<30;i++){
//            list.add("/dev/ttyS3");
//        }
//        setNewData(list);

        setNewData(Arrays.asList(SerialPortOpenSDK.getInstance().getDevicesPath()));
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        TextView tvNum = holder.findView(R.id.tv_serial);
        tvNum.setText(s);

        int position = holder.getAdapterPosition();

        tvNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemCallBack!=null){
                    mOnItemCallBack.onItemClick(position,s);
                }
            }
        });
    }
}
