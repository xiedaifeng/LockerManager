package com.example.http_lib;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.RequestBean;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;
import com.example.http_lib.model.IHttpClient;
import com.example.http_lib.utils.StringUtil;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求类
 * Created by xiaochan on 2017/6/26.
 */

public class HttpClient {

    Handler mMainHandler = new Handler(Looper.getMainLooper());

    private IHttpCallBack mModel;
    private Class mRequestClass;
    private Object mCarry;
    private Class mParseClass;
    private final String COOKIE = "Set-Cookie";

    private HttpClient(IHttpCallBack model) {
        if (model == null) {
            throw new NullPointerException("model can not be null");
        }
        this.mModel = model;
    }

    public static void request(BaseBean baseBean, IHttpCallBack model) {
        request(baseBean, true, model);
    }


    public static void request(BaseBean baseBean, boolean isEncrypt, IHttpCallBack model) {
        new HttpClient(model).realRequest(baseBean, isEncrypt);
    }

    private void realRequest(BaseBean baseBean, boolean isEncrypt) {
        this.mCarry = baseBean.carry;
        this.mParseClass = baseBean.parseClass;
        this.mRequestClass = baseBean.getClass();
        final RequestBean requestBean = new RequestBean(baseBean, isEncrypt);
        OkHttpClient.Builder builder1 = new OkHttpClient.Builder();
        builder1.retryOnConnectionFailure(true);
        OkHttpClient client = new OkHttpClient();
        String cookie = SharedPreferencesUtils.getString(COOKIE, "");

        Request.Builder builder = new Request.Builder();
        if (!TextUtils.isEmpty(cookie)) {
            LogUtils.d("Cookie :" + cookie);
            builder.addHeader("Cookie", cookie);
        }
        String url = requestBean.getUrl();
        Map<String, Object> params = requestBean.getParams();
        RequestTypeEnum typeValue = requestBean.getType();

        if (requestBean.getEnum() == RequestMethodEnum.POST) {
            if (!TextUtils.isEmpty(typeValue.getTypeStr())) {
                builder.addHeader("Content-type", typeValue.getTypeStr());
            }
            Request request = null;
            //post form方式提交的数据
            if (typeValue == RequestTypeEnum.FORM) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    formBuilder.add(key, value);
                }
                FormBody formBody = formBuilder.build();
                request = builder.post(formBody).url(url).build();
            } else if (typeValue == RequestTypeEnum.JSON) {
                String json = JSON.toJSONString(params);
                RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
                request = builder.post(requestBody).url(url).build();
            }
            if (request == null) {
                LogUtils.d("请求不能为空");
                return;
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setErrCode(404);
                    responseBean.setCode(404);
                    responseBean.setErrMsg("请求错误");
                    responseBean.setMessage("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    if (cookies.size() > 0) {
                        SharedPreferencesUtils.putString(COOKIE, cookies.get(0));
                    }
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });

        } else if (requestBean.getEnum() == RequestMethodEnum.GET) {
            url = splictGetUrl(url, params);
            Request request = builder.get().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setErrCode(404);
                    responseBean.setCode(404);
                    responseBean.setErrMsg("请求错误");
                    responseBean.setMessage("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Headers headers = response.headers();
                  /*  List<String> cookies = headers.values("Set-Cookie");
                    if (cookies.size()>0){
                        String session = cookies.get(0);
                        String result = session.substring(0, session.indexOf(";"));
                        if (result.contains("=")) {
                            String[] split = result.split("=");
                            if (split.length > 1) {
                                SharedPreferencesUtils.putString(COOKIE, split[1]);
                            }
                        }else {
                            SharedPreferencesUtils.putString(COOKIE, session);
                        }
                    }*/
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        } else if (requestBean.getEnum() == RequestMethodEnum.DELETE) {
            String json = JSON.toJSONString(params);
            RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
            Request request = builder.delete(requestBody).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setErrCode(404);
                    responseBean.setCode(404);
                    responseBean.setErrMsg("网络错误");
                    responseBean.setMessage("网络错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        } else if (requestBean.getEnum() == RequestMethodEnum.PUT) {
            String json = JSON.toJSONString(params);
            RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
            Request request = builder.put(requestBody).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setErrCode(404);
                    responseBean.setCode(404);
                    responseBean.setErrMsg("网络错误");
                    responseBean.setMessage("网络错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        }
    }

    private void callback(boolean isEncrypt, String result) {
        LogUtils.d("callback:" + result);
        ResponseBean responseBean = JSON.parseObject(result, ResponseBean.class);
        String data = responseBean.getData();
        // 判断是否加密  如果加密  进行解密操作
        LogUtils.d("平台返回的数据(解密前):" + mRequestClass.getSimpleName() + "," + responseBean.toString());
        if (isEncrypt) {
            if (!TextUtils.isEmpty(data) && !data.equals("{}")) {
                try {
                    data = StringUtil.AESToStringForSplit(data, UserCacheHelper.getAESToken(), "UTF-8");
                    LogUtils.d("平台返回的数据(解密后):" + data);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFail(responseBean);
                    LogUtils.d("平台返回的数据(解密失败):" + data);
                    return;
                }
            }
        }
        if (responseBean.getCode() == null) {
            responseBean = new ResponseBean();
            responseBean.setData(data);
            responseBean.setErrCode(-1);
            responseBean.setCode(-1);
            responseBean.setErrMsg("后台异常");
            responseBean.setMessage("后台异常");
            onFail(responseBean);
            return;
        }
        responseBean.setData(data);
        if (responseBean.getCode() == 0) {
            onSuccess(responseBean);
        } else {
            onFail(responseBean);
        }
    }

    private String splictGetUrl(String requestUrl, Map<String, Object> params) {
        Set<String> keySet = params.keySet();
        StringBuffer buffer = new StringBuffer();
        for (String key : keySet) {
            String value = params.get(key).toString();
            buffer.append(key).append("=").append(value).append("&");
        }
        String getParams = buffer.toString();

        requestUrl = requestUrl.endsWith("?") ? requestUrl : requestUrl + "?";
        getParams = getParams.endsWith("&") ? getParams.substring(0, getParams.length() - 1) : getParams;
        requestUrl += getParams;
        return requestUrl;
    }

    private void onSuccess(final ResponseBean responseBean) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                responseBean.setRequestClass(mRequestClass);
                responseBean.setCarry(mCarry);
                responseBean.setParseClass(mParseClass);
                mModel.success(responseBean);
            }
        });
    }

    private void onFail(final ResponseBean responseBean) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                responseBean.setRequestClass(mRequestClass);
                responseBean.setCarry(mCarry);
                responseBean.setParseClass(mParseClass);
                mModel.failed(responseBean);
            }
        });
    }
}
