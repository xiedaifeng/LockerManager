package com.yidao.module_lib.base.http.callback;

import com.yidao.module_lib.base.http.ResponseBean;

/**
 * http请求回调接口
 * Created by xiaochan on 2017/6/26.
 */

public interface IHttpCallBack {
    /**
     * 成功回调
     */
    void success(ResponseBean responseBean);

    /**
     * 失败回调
     */
    void failed(ResponseBean responseBean);
}
