package com.yidao.module_lib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/11/13
 */
public class BlurrUtils {


    private  Handler mHandler;
    private  Activity mActivity;
    private  ImageView dialogBg;
    private int originalW;
    private int originalH;
    private static BlurrUtils blurrUtils;

    public static BlurrUtils getInstance(){
        if (blurrUtils== null) {
            blurrUtils = new BlurrUtils();
        }
        return blurrUtils;
    }

    public void initView( ImageView view,Activity activity){
        mActivity = activity;
        dialogBg = view;
        view.setImageAlpha(0);
        view.setVisibility(View.GONE);

        mHandler = new MyHandle(view);
    }



    public Bitmap captureScreen(Activity activity) {
        activity.getWindow().getDecorView().destroyDrawingCache();  //先清理屏幕绘制缓存(重要)
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        //获取原图尺寸
        originalW = bmp.getWidth();
        originalH = bmp.getHeight();
        //对原图进行缩小，提高下一步高斯模糊的效率
        bmp = Bitmap.createScaledBitmap(bmp, originalW /4 , originalH /4, false);
        return bmp;
    }

    public void setScreenBgLight(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp;
        if (window != null) {
            lp = window.getAttributes();
            lp.dimAmount = 0.2f;
            window.setAttributes(lp);
        }
    }

    public void handleBlur() {
        Bitmap bp = captureScreen(mActivity);
        bp = blur(bp);                      //对屏幕截图模糊处理
        //将模糊处理后的图恢复到原图尺寸并显示出来
        bp = Bitmap.createScaledBitmap(bp, originalW, originalH, false);
        dialogBg.setImageBitmap(bp);
        dialogBg.setVisibility(View.VISIBLE);
        //防止UI线程阻塞，在子线程中让背景实现淡入效果
        asyncRefresh(true);
    }
    public void asyncRefresh(boolean in) {
        //淡出淡入效果的实现
        if(in) {    //淡入效果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 256; i += 5) {
                        refreshUI(i);//在UI线程刷新视图
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {    //淡出效果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 255; i >= 0; i -= 5) {
                        refreshUI(i);//在UI线程刷新视图
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //当淡出效果完毕后发送消息给mHandler把对话框背景设为不可见
                    mHandler.sendEmptyMessage(0);
                }
            }).start();
        }
    }

    public void refreshUI(final int i) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogBg.setImageAlpha(i);
            }
        });
    }

    public void hideBlur() {
        //把对话框背景隐藏
        asyncRefresh(false);
        System.gc();
    }

    public Bitmap blur(Bitmap bitmap) {
        //使用RenderScript对图片进行高斯模糊处理
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(mActivity); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); //
        // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 开辟输入内存
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 开辟输出内存
        float radius = 10f;     //设置模糊半径
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入内存
        gaussianBlue.forEach(allOut); // 模糊编码，并将内存填入输出内存
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy();
        //rs.releaseAllContexts(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }


    public static class MyHandle extends Handler{

        private ImageView imageView;

        public MyHandle(ImageView view){
            imageView = view;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
