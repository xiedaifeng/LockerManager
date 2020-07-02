package com.example.http_lib.aliupload;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/8
 */
public class AliuploadInit {

     OSSClient ossClient;
    static AliuploadInit aliuploadInit;

    public static AliuploadInit getInstance() {
        if (aliuploadInit == null) {
            synchronized (AliuploadInit.class) {
                if (aliuploadInit == null)
                    aliuploadInit = new AliuploadInit();
            }
        }
        return aliuploadInit;
    }


    public  void initOSS(Context context) {
        if (ossClient == null) {
            OSSPlainTextAKSKCredentialProvider ossPlainTextAKSKCredentialProvider = new OSSPlainTextAKSKCredentialProvider(Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET);
            ossClient = new OSSClient(context, Config.OSS_ENDPOINT, ossPlainTextAKSKCredentialProvider);
        }
    }

    public OSSClient getOssClient() {
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }
}
