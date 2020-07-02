package com.locker.manager.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.locker.manager.R;


public class NumAdapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public NumAdapter() {
        super(R.layout.item_num);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        TextView tvNum = holder.findView(R.id.tv_num);
    }
}
