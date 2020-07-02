package com.locker.manager.activity;

import android.widget.ImageView;

import com.locker.manager.R;
import com.locker.manager.adapter.NumAdapter;
import com.locker.manager.base.BaseUrlView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class SaveFirstActivity extends BaseUrlView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.iv_help)
    ImageView ivHelp;

    @Override
    protected int getView() {
        return R.layout.activity_save_first;
    }


    @Override
    public void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getCtx(),3));
        NumAdapter adapter = new NumAdapter(getCtx());
        recyclerView.setAdapter(adapter);
    }
}
