package com.yidao.module_lib.config;

import android.os.Environment;

/**
 * Created by xiaochan on 2017/6/19.
 */

public class Config {

    public static final boolean ISDEBUG = true;

    public static final String BASE_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/yanKa";

    public static final String VIDEO_STORAGE_DIR = BASE_STORAGE_DIR + "/ShortVideo/";
    public static final String PHOTO_STORAGE_DIR = BASE_STORAGE_DIR + "/photo/";
    public static final String PHOTO_STORAGE_DOWMLOAD_DIR = BASE_STORAGE_DIR + "/download/";
}
