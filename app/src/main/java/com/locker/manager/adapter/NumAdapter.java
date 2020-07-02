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


public class NumAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private List<String> mList = new ArrayList<>();

    private Context mContext;

    private OnItemCallBack mOnItemCallBack;

    public void setOnItemCallBack(OnItemCallBack<String> onItemCallBack) {
        mOnItemCallBack = onItemCallBack;
    }

    public NumAdapter(Context context) {
        super(R.layout.item_num);
        this.mContext = context;

        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.add("5");
        mList.add("6");
        mList.add("7");
        mList.add("8");
        mList.add("9");
        mList.add("重置");
        mList.add("0");
        mList.add("回删");

        setNewData(mList);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        TextView tvNum = holder.findView(R.id.tv_num);
        tvNum.setText(s);

        int position = holder.getAdapterPosition();
        if(TextUtils.equals("重置",s) || TextUtils.equals("回删",s)){
            tvNum.setTextSize(TypedValue.COMPLEX_UNIT_SP,mContext.getResources().getDimension(R.dimen.sp_20));
        } else {
            tvNum.setTextSize(TypedValue.COMPLEX_UNIT_SP,mContext.getResources().getDimension(R.dimen.sp_50));
        }

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
