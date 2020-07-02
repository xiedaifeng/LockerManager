package com.yidao.module_lib.base.ibase;

import com.yidao.module_lib.base.http.ResponseBean;

/**
 * Created by xiaochan on 2017/6/19.
 */

public interface IBasePress {
    public void success(ResponseBean responseBean);

    public void failed(ResponseBean responseBean);


}
