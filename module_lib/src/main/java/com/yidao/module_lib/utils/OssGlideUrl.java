package com.yidao.module_lib.utils;

import com.bumptech.glide.load.model.GlideUrl;

public class OssGlideUrl extends GlideUrl {

    String url;

    public OssGlideUrl(String url) {
        super(url);
        this.url = url;
    }

    @Override
    public String getCacheKey() {
        //去除Glide缓存key中的Oss token
        if (url.contains("?")) {
            return url.substring(0, url.lastIndexOf("?"));
        } else {
            return url;
        }
    }
}