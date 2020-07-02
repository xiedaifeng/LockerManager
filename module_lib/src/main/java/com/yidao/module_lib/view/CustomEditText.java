package com.yidao.module_lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

import com.yidao.module_lib.R;
import com.yidao.module_lib.utils.IDCardUtil;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.edit.CharInputFilter;


@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {

//    public enum InputType{
//        Normal(0),
//        Phone(1),
//        Email(2),
//        Password(3),
//        VerifyCode(4),
//        IDCard(5),
//        BankCard(6);
//
//        int value;
//        InputType(int value){
//            this.value = value;
//        }
//    }

    private int mInputType;
    private int mInputLength;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.custom_edit_text_attr);
        mInputType = array.getInteger(R.styleable.custom_edit_text_attr_customInputType, 0);
        mInputLength = array.getInteger(R.styleable.custom_edit_text_attr_customLength, 0);
        setSingleLine(true);
        setBackground(null);
        switch (mInputType) {
            case 0:
                break;
            case 1:
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(11) {},new CharInputFilter(CharInputFilter.MODEL_NUMBER)});
//                setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.et_digits_number)));
                setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 2:
                setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                break;
            case 3:
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(18) {},new CharInputFilter(CharInputFilter.MODEL_LETTER_AND_NUMBER)});
//                setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.et_digits)));
                setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 4:
                setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.et_digits_number)));
                setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 5:
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(20) {},new CharInputFilter(CharInputFilter.MODEL_LETTER_AND_NUMBER)});
                setInputType(InputType.TYPE_CLASS_TEXT);
//                setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.et_digits)));
                break;
            case 6:
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(19) {},new CharInputFilter(CharInputFilter.MODEL_NUMBER)});
                setInputType(InputType.TYPE_CLASS_NUMBER);
//                setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.et_digits_number)));
                break;
        }
        array.recycle();
    }


    public boolean isCheckSuccess() {
        boolean check = false;
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        switch (mInputType) {
            case 0:
                check = true;
                break;
            case 1:
                if (PhoneUtils.isPhone(text)) {
                    check = true;
                }
                break;
            case 2:
                if (text.contains("@") && !text.endsWith("@")) {
                    check = true;
                }
                break;
            case 3:
                if (text.length() >= 6 && text.length() <= 18) {
                    check = true;
                }
                break;
            case 4:
                if(text.length() == mInputLength){
                    check = true;
                }
                break;
            case 5:
                if (IDCardUtil.isIDCard(text)) {
                    check = true;
                }
                break;
            case 6:
                if (text.length() >= 15 && text.length() <= 19) {
                    check = true;
                }
                break;
        }
        LogUtils.d("checkSuccess:" + check);
        return check;
    }
}
