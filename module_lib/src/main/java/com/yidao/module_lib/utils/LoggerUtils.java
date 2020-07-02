package com.yidao.module_lib.utils;

import android.util.Log;

import com.yidao.module_lib.config.Config;


/**
 * Created by xiaochan on 2017/6/19.
 */

public class LoggerUtils {
    public static boolean IS_DEBUG = Config.ISDEBUG;
    private static final int MAX_LENGTH = 1000 * 2;

    public static void debug(String tagLabel, Class<?> className, String msgInfo) {
        if (IS_DEBUG) {
            if (msgInfo.length() <= MAX_LENGTH) {
                Log.i(tagLabel, className.getName() + ":" + msgInfo);
            } else {
                int time = (int) Math.ceil(msgInfo.length() * 1.0f / MAX_LENGTH);
                for (int i = 0; i < time; i++) {
                    if ((i + 1) * MAX_LENGTH < msgInfo.length()) {
                        Log.i(tagLabel, className.getName() + ":\t" + msgInfo.substring(i * MAX_LENGTH, (i + 1) * MAX_LENGTH));
                    } else {
                        Log.i(tagLabel, msgInfo.substring(i * MAX_LENGTH, msgInfo.length()));
                    }
                }
            }
        }
    }

    public static void debug(String tagLabel, Throwable e) {
        if (IS_DEBUG) {
            Log.i("debug", tagLabel, e);
        }
    }

    public static void info(String tagLabel, Class<?> className, String msgInfo) {
        Log.i(tagLabel, className.getName() + ":" + msgInfo);
    }

    public static void error(Exception e, Class<?> className) {
        Log.i("error", className.getName() + ":" + e.getMessage());
    }
}
