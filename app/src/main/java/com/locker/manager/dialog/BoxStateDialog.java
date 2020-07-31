package com.locker.manager.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.locker.manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public
class BoxStateDialog extends RxDialog {


    public BoxStateDialog(Context context) {
        super(context);
        ButterKnife.bind(this);
        initView();
    }


    @BindView(R.id.dialog_close_btn)
    ImageView dialogCloseBtn;
    @BindView(R.id.dialog_title)
    RelativeLayout dialogTitle;
    @BindView(R.id.dialog_content_tv)
    TextView dialogContentTv;
    @BindView(R.id.dialog_back_tv)
    TextView dialogBackTv;
    @BindView(R.id.dialog_content_ll)
    RelativeLayout dialogContentLl;
    @BindView(R.id.dialog_box_state_tv)
    TextView dialogBoxStateTv;
    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_open_box, null);
        setContentView(dialogView);
        setCanceledOnTouchOutside(false);
        dialogCloseBtn=dialogView.findViewById(R.id.dialog_close_btn);
        dialogCloseBtn=dialogView.findViewById(R.id.dialog_close_btn);
        dialogCloseBtn=dialogView.findViewById(R.id.dialog_close_btn);
        dialogBoxStateTv=dialogView.findViewById(R.id.dialog_box_state_tv);
    }

}
