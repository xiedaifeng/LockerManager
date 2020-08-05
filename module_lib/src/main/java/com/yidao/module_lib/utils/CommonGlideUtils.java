package com.yidao.module_lib.utils;
/**
 * Created by zhaolong on 2017/9/29.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yidao.module_lib.glide.UnsafeOkHttpClient;

import java.io.File;
import java.io.InputStream;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * class infation
 */
public class CommonGlideUtils {


    public static String url1 = "https://gitee.com/elkshrek/ELKPic/raw/pic/meinv/meinv001.png";

    public static void showImageView(Context context, String url, ImageView imgeview) {
        Glide.with(context).load(url).apply(new RequestOptions()).into(imgeview);
    }

    public static void showImageViewSkipCache(Context context, String url, ImageView imgeview) {
        Glide.with(context).load(url).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imgeview);
    }

    public static void showImageView(Context context, String url, ImageView imgeview,int errorImg) {
        Glide.with(context).load(url).apply(new RequestOptions().placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showCirclePhotoSkipCache(Context context, String url, ImageView imageView,int errorImg) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).error(errorImg)).into(imageView);
    }

    public static void showCirclePhoto(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }

    public static void showImageCorner(Context context, String url, ImageView imgeview,int pxValue) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue))).into(imgeview);
    }

    public static void showImageCornerWithCenterCrop(Context context, String url, ImageView imgeview, int pxValue) {
        Glide.with(context).load(url).apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(pxValue))).into(imgeview);
    }

    public static void showImageCorner(Context context, Uri uri, ImageView imgeview, int pxValue) {
        Glide.with(context).load(uri).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue))).into(imgeview);
    }

    public static void showImageCorner(Context context, String url, ImageView imgeview, int pxValue,int errorImg) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue)).placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showImageCornerWithCenterCrop(Context context, String url, ImageView imgeview, int pxValue,int errorImg) {
        Glide.with(context).load(url).apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(pxValue)).placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showImageCornerWithCenterCropAndPos(Context context, String url, ImageView imgeview,  CornerTransform transformation,int errorImg) {
//        CornerTransform transformation = new CornerTransform(mContext, DensityUtil.dip2px(mContext, 10));
//        //只是绘制左上角和右上角圆角
//        transformation.setExceptCorner(false, false, true, true);
        Glide.with(context).load(url).apply(new RequestOptions().transforms(new CenterCrop(), transformation).placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showCirclePhoto(final Context context, String url, final ImageView imageView, int errorImg) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(errorImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(errorImg).error(errorImg)).into(imageView);
    }

    public static void lubanCompress(Context context, String path, final String key, final ICompressListener compressListener){
        Luban.with(context)
                .load(path)
                .ignoreBy(200)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if(compressListener!=null){
                            compressListener.compressSuccess(key,file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    public static interface ICompressListener{
        void compressSuccess(String key,File file);
    }

    private static RoundedBitmapDrawable bindCircleBitmapToImageView(Context context, int errorImg){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),errorImg);
        RoundedBitmapDrawable bitmapDrawable =  RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        bitmapDrawable.setCircular(true);
        return bitmapDrawable;
    }
}
