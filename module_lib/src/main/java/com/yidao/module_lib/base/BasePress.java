package com.yidao.module_lib.base;


import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEvent;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;

/**
 * Created by xiaochan on 2017/6/19.
 */

public class BasePress<T extends IBaseView> implements IBasePress {

    protected T view;

    public BasePress(T view) {
        this.view = view;

    }

    public T getView() {
        return view;
    }


    @Override
    public void success(ResponseBean responseBean) {
        getView().alertSuccess();
        if (getView() instanceof IBaseEvent){
            IBaseEvent event = (IBaseEvent) getView();
            event.onResponse(true,responseBean.getRequestClass(),responseBean);
        }
    }

    @Override
    public void failed(ResponseBean responseBean) {
        if (getView() instanceof IBaseEvent){
            IBaseEvent event = (IBaseEvent) getView();
            event.onResponse(false,responseBean.getRequestClass(),responseBean);
        }
    }

    public void setRequst(BaseBean baseBean,IBaseModel iBaseModel){
        iBaseModel.setBean(baseBean);
        iBaseModel.request(false);
    }
}
