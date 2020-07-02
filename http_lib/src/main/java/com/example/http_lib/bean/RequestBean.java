package com.example.http_lib.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;
import com.example.http_lib.utils.ObjectUtil;
import com.example.http_lib.utils.StringUtil;
import com.example.http_lib.utils.StringUtils;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.config.Constants;
import com.yidao.module_lib.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBean {

    private BaseBean mBaseBean;

    private RequestrAnnotation annotation;

    public RequestBean(BaseBean baseBean, boolean isEncrypt) {
        this.mBaseBean = baseBean;
        this.isEncrypt = isEncrypt;
        annotation = baseBean.getClass().getAnnotation(RequestrAnnotation.class);
        parseParams();
    }

    private String url;

    private String type;

    private RequestMethodEnum mEnum;

    private boolean isEncrypt;

    private Map<String, Object> params = new HashMap<>();

    private Map<String, Object> encryptMap = new HashMap<>();

    public String getUrl() {
        return annotation.baseUrl() + annotation.path();
    }

    public RequestTypeEnum getType() {
        return annotation.type();
    }

    public RequestMethodEnum getEnum() {
        return annotation.method();
    }

    public boolean isEncrypt() {
        return !TextUtils.isEmpty(UserCacheHelper.getAESToken()) && isEncrypt;
    }

    public Map<String, Object> getParams() {
        if (isEncrypt()) {
            params.put(Constants.ENCRYPT_PARAM, getAES());
        }
//        params.put(Constants.USER_ID, UserCacheHelper.getUserId());


        LogUtils.d("请求的url:" + getUrl());
        LogUtils.d("请求类型:" + getEnum().name());
        LogUtils.d("请求的参数:" + JSON.toJSONString(params));
        return params;
    }

    public Map<String, Object> getEncryptMap() {
        return encryptMap;
    }


    private void parseParams() {
        List<Field> fields = ObjectUtil.getFields(mBaseBean.getClass());
        for (int i = 0; i < fields.size(); i++) {
            Field fieldItem = fields.get(i);
            String fieldsName = fieldItem.getName();
            if (fieldsName.equals("serialVersionUID")) {
                continue;
            }
            if (fieldsName.equals("baseUrl")) {
                continue;
            }
            if (fieldsName.equals("carry") || fieldsName.equals("parseClass")) {
                continue;
            }
            fieldItem.setAccessible(true);
            Object value = null;
            try {
                value = fieldItem.get(mBaseBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                if (isEncrypt()) {
                    encryptMap.put(fieldsName, value);
                } else {
                    params.put(fieldsName, value);
                }
            }
        }
    }

    private String getAES() {
        String paramUrl = StringUtils.mapToUrl(encryptMap);
        String key = UserCacheHelper.getAESToken();
        String signUrl = paramUrl.concat("&key=" + key);
        String sign = StringUtils.toMD5(signUrl);

        encryptMap.put(Constants.ENCRYPT_SIGN, sign);

        //请求参数转换为json 然后做AES加密
        String jsonUrl = JSON.toJSONString(encryptMap);
        String aes = StringUtil.toAESForSplit(jsonUrl, key, "UTF-8");
        return aes;
    }
}
