package com.yidao.module_lib.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/13
 */
public class ViewUtils {

    public static void countDownText(final TextView textView){
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onFinish() {
                textView.setClickable(true);
                textView.setEnabled(true);
                textView.setText("发送验证码");
                cancel();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setClickable(false);
                textView.setEnabled(false);
                textView.setText(millisUntilFinished / 1000 + "s");
            }
        }.start();
    }

    public static void countDownTextP(final TextView textView,String phone){
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onFinish() {
                textView.setClickable(true);
                textView.setEnabled(true);
                textView.setText("发送验证码");
                cancel();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setClickable(false);
                textView.setEnabled(false);
                textView.setText(millisUntilFinished / 1000 + "s");
            }
        }.start();
    }

    /**
     * 关闭默认局部刷新动画
     */
//    public static void closeDefaultAnimator(RecyclerView mRvCustomer) {
//        if(null==mRvCustomer)return;
//        mRvCustomer.getItemAnimator().setAddDuration(0);
//        mRvCustomer.getItemAnimator().setChangeDuration(0);
//        mRvCustomer.getItemAnimator().setMoveDuration(0);
//        mRvCustomer.getItemAnimator().setRemoveDuration(0);
//        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
//    }
//
//
//    public static int getScollYDistance(RecyclerView recyclerView) {
//        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        if (layoutManager == null) return 0;
//        int position = layoutManager.findFirstVisibleItemPosition();
//        View firstVisiableChildView = layoutManager.findViewByPosition(position);
//        if (firstVisiableChildView ==null) return 0;
//        int itemHeight = firstVisiableChildView.getHeight();
//        return (position) * itemHeight - firstVisiableChildView.getTop();
//    }

    public static void setClickSwitch(boolean isClick,View view){
        view.setEnabled(isClick);
        view.setClickable(isClick);
    }

    public static void showEmpty(View content,View empty) {
        empty.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    public static void showContent(View content,View empty) {
        empty.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    public static void etFocus(EditText editText,boolean isFocus){
        editText.setFocusable(isFocus);
        editText.setFocusableInTouchMode(isFocus);
        editText.requestFocus();
        editText.findFocus();
    }



}
