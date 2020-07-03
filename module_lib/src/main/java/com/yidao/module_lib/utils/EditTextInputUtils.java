package com.yidao.module_lib.utils;

import android.widget.EditText;

public class EditTextInputUtils {

    // 往文本框中添加内容
    public static void addString(EditText editText,String sequence) {
        int index = getEditSelection(editText);// 光标的位置
        if (index < 0 || index >= getEditTextViewString(editText).length()) {
            editText.append(sequence);
        } else {
            editText.getEditableText().insert(index, sequence);// 光标所在位置插入文字
        }
    }

    // 回删
    public static void deleteString(EditText editText) {
        int index = getEditSelection(editText);// 光标的位置
        if (index <= 0 || index > getEditTextViewString(editText).length()) {
        } else {
            editText.getEditableText().delete(index-1, index);
        }
    }

    // 获取光标当前位置
    public static int getEditSelection(EditText editText) {
        return editText.getSelectionStart();
    }

    // 获取文本框的内容
    public static String getEditTextViewString(EditText editText) {
        return editText.getText().toString();
    }


    // 删除指定位置的字符
    public static void deleteEditValue(EditText editText,int index) {
        editText.getText().delete(index - 1, index);
    }

    // 设置光标位置
    public static void setEditSelectionLoc(EditText editText,int index) {
        editText.setSelection(index);
    }
}
