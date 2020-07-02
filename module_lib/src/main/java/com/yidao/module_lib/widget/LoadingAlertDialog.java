package com.yidao.module_lib.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.yidao.module_lib.R;


/**自定义加载进度对话框
 * Created by Rem on 2017/1/9.
 */

public class LoadingAlertDialog extends Dialog {


    public LoadingAlertDialog(final Activity context) {
        super(context);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                context.finish();
            }
        });
    }
}
