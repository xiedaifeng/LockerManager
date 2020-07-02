package com.example.http_lib.utils;

import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/24
 */
public class OkhttpUpload {

    private static final String url = "http://lyp.3kmo.com/api/upload";

    public static ResponseBean doPostFileSyn(List<File> files) {
        ResponseBean responseBean = new ResponseBean();

        MultipartBody.Builder builder1 = new MultipartBody.Builder();
        builder1.setType(MultipartBody.FORM);

        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (files != null) {
            for (File file : files) {
                builder1.addFormDataPart("uploadFile", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
        }

        RequestBody requestBody = builder1.build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
            String responseData = response.body().string();
            LogUtils.d("OkhttpUpload = " + responseData);
            responseBean.setData(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBean;
    }

    public static ResponseBean doPostFileSyn(File file) {
        ResponseBean responseBean = new ResponseBean();

        MultipartBody.Builder builder1 = new MultipartBody.Builder();
        builder1.setType(MultipartBody.FORM);

        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key

        builder1.addFormDataPart("uploadFile", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));

        RequestBody requestBody = builder1.build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
            String responseData = response.body().string();
            LogUtils.d("OkhttpUpload = " + responseData);
            responseBean.setData(responseData);
        } catch (IOException e) {
            LogUtils.e("error = "+e.getMessage());
            e.printStackTrace();
        }
        return responseBean;
    }
}
