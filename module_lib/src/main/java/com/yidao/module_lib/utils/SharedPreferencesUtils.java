package com.yidao.module_lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yidao.module_lib.base.BaseApplication;

import java.util.Set;

/**
 * @author cifz
 *         首选项的工具类
 *         boolean String
 */
public final class SharedPreferencesUtils {

    private static String name = "dream_preferences";
    private static int mode = Context.MODE_PRIVATE;
    private static Context context = BaseApplication.getApplication();


    //存值
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void putString(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void putInt(String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void putStringList(String key, Set<String> searchList) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putStringSet(key, searchList);
        edit.apply();
    }

    //获取值
    public static Set<String> getStringList(String key) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getStringSet(key, null);
    }

    //获取值
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getString(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }

    public static void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }


    public static void clean() {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }


}
