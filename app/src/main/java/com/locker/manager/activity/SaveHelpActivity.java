package com.locker.manager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.locker.manager.R;
import com.locker.manager.base.BaseUrlView;
import com.locker.manager.dialog.ExitDialog;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemLongClick;


public class SaveHelpActivity extends BaseUrlView {


    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getView() {
        return R.layout.activity_save_help;
    }

    @Override
    public void init() {
        setCurrentTime(tvTitle,System.currentTimeMillis());

        tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                startActivity(intent);

                ExitDialog dialog = new ExitDialog(getCtx());
                dialog.show();
                return false;
            }
        });
    }


    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
                break;
        }
    }
}
