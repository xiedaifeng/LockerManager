package com.locker.manager.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.locker.manager.R;
import com.locker.manager.callback.OnItemCallBack;

import java.util.ArrayList;
import java.util.List;


public class TestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private List<String> mList = new ArrayList<>();

    private Context mContext;

    private OnItemCallBack mOnItemCallBack;

    public void setOnItemCallBack(OnItemCallBack<String> onItemCallBack) {
        mOnItemCallBack = onItemCallBack;
    }

    public TestAdapter(Context context) {
        super(R.layout.item_test_gekou);
        this.mContext = context;

        for(int i =1;i<=99;i++){
            mList.add(i+"");
        }

        setNewData(mList);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        TextView tvNum = holder.findView(R.id.tv_gekou);
        tvNum.setText(String.format("%s号格口",s));

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
