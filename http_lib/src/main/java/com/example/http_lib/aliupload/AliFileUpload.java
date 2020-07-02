package com.example.http_lib.aliupload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yidao.module_lib.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/8
 */
public class AliFileUpload {

    public static PutObjectResult fileUpload(File file){


        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        qualityCompress(bitmap,file);
// 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(Config.BUCKET_NAME, "picture/"+file.getName(), file.getPath());

// 文件元信息的设置是可选的。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setContentType("application/octet-stream"); // 设置content-type。
// metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5。
// put.setMetadata(metadata);

        try {
            PutObjectResult putResult = AliuploadInit.getInstance().getOssClient().putObject(put);
//            String imgUrl = "https://laiyipiao.oss-cn-hangzhou.aliyuncs.com/picture/"+file.getName();
            String imgUrl = Config.OSS_URL+"/picture/"+file.getName();
            putResult.setServerCallbackReturnBody(imgUrl);
            Log.d("img_ali",imgUrl);
            Log.d("PutObject", "UploadSuccess");
            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());
            return putResult;
        } catch (ClientException e) {
            // 本地异常，如网络异常等。
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常。
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param filePath  文件路径
     *
     * @return 文件最终存放的绝对路径
     *
     */
    public static void OSSUpload(String filePath, OSSProgressCallback<PutObjectRequest> progressCallback, final OSSCompleteCallback completedCallback) {
        // 构造上传请求。
        String[] split = filePath.split("/");
        String fileName = split[split.length-1];
        PutObjectRequest put = new PutObjectRequest(Config.BUCKET_NAME, "video/"+fileName, filePath);

//        put.setCallbackParam(new HashMap<String, String>() {
//            {
//                put("callbackUrl", Config.OSS_CALLBACK_URL);
//                put("callbackHost", "oss-cn-beijing.aliyuncs.com");
//                put("callbackBodyType", "application/json");
//                put("callbackBody", "{\"mimeType\":${mimeType},\"size\":${size}}");
//            }
//        });


        final String url = Config.OSS_URL+"/video/"+fileName;
        LogUtils.e("videoUrl:"+url);


// 异步上传时可以设置进度回调。
        put.setProgressCallback(progressCallback);

        OSSAsyncTask<PutObjectResult> task = AliuploadInit.getInstance().getOssClient().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                completedCallback.onSuccess(url);
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                completedCallback.onFailure(clientException);
            }
        });

//        try {
//            String url = Config.OSS_URL+"/picture/"+fileName;
//            task.getResult().setServerCallbackReturnBody(url);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 尺寸压缩（通过缩放图片像素来减少图片占用内存大小）
     *
     * @param bmp
     * @param file
     */

    public static void sizeCompress(Bitmap bmp, File file) {
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 8;
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     *
     * @param bmp
     * @param file
     */
    public static void qualityCompress(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int quality = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OSSCompleteCallback{
        void onSuccess(String url);
        void onFailure(ClientException clientException);
    }
}
