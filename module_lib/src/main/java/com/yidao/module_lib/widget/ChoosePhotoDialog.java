package com.yidao.module_lib.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
//import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidao.module_lib.R;
import com.yidao.module_lib.config.Config;
import com.yidao.module_lib.utils.BitmapUtils;

import java.io.File;

import androidx.core.content.FileProvider;
import butterknife.ButterKnife;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/28
 */
@SuppressLint("ValidFragment")
public class ChoosePhotoDialog extends Dialog implements View.OnClickListener{

    public static int Take_Code = 100;
    public static int Album_Code = 101;
    public static int Crop_Code = 102;



    private File mCameraSavePath;
    private Uri uri;
    private Uri cropUri;
    private Context mContext;
    private String mKey;

    public Uri getUri() {
        return uri;
    }

    public Uri getCropUri() {
        return cropUri;
    }

    public File getCameraSavePath() {
        return mCameraSavePath;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public ChoosePhotoDialog(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    protected void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_choose_photo, null);
        ButterKnife.bind(view, this);
        setContentView(view);
        getWindow().setWindowAnimations(R.style.dialog_style);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        findViewById(R.id.tv_women).setOnClickListener(this);
        findViewById(R.id.tv_man).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }


    //激活相册操作
    private void openAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("scale", true);
        intent.putExtra("crop", false);
        intent.setType("image/*");
        ((Activity) mContext).startActivityForResult(intent, Album_Code);
    }


    //激活相机操作
    private void openCamera() {
        File takeFile = new File(Config.PHOTO_STORAGE_DIR);
        if (!takeFile.exists()) {
            takeFile.mkdirs();
        }
        mCameraSavePath = new File(Config.PHOTO_STORAGE_DIR + "take_" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", mCameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(mCameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity) mContext).startActivityForResult(intent, Take_Code);
    }


    /**
     * @param uri    传入拍照或者相册的Uri
     * @param isTake true 代表拍照   false 代表相册选择
     */
    public void photoClip(Uri uri, boolean isTake) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        if (isTake) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            this.cropUri = uri;
        } else {
            String filePath = BitmapUtils.getRealFilePath(mContext, uri);
            Uri newUri = BitmapUtils.getImageContentUri(mContext, new File(filePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, newUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            this.cropUri = newUri;
        }
        ((Activity) mContext).startActivityForResult(intent, ChoosePhotoDialog.Crop_Code);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        int id = view.getId();
        if (id == R.id.tv_man) {
            openAlbum();
        } else if (id == R.id.tv_women) {
            openCamera();
        }
    }
}
