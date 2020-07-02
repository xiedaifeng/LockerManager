package com.yidao.module_lib.base;


import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEvent;
import com.yidao.module_lib.base.ibase.IBaseEventPlus;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;

/**
 * Created by xiaochan on 2017/6/19.
 */

public class BasePressPlus<T extends IBaseView> extends BasePress implements IBasePress {


    public BasePressPlus(IBaseView view) {
        super(view);
    }

    @Override
    public void success(ResponseBean responseBean) {
        getView().alertSuccess();
        if (getView() instanceof IBaseEventPlus){
            try {
                IBaseEventPlus event = (IBaseEventPlus) getView();
                event.onResponse(true,responseBean.getRequestClass(),responseBean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(ResponseBean responseBean) {
        if (getView() instanceof IBaseEventPlus){
            try {
                IBaseEventPlus event = (IBaseEventPlus) getView();
                event.onResponse(false,responseBean.getRequestClass(),responseBean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setRequst(BaseBean baseBean, IBaseModel iBaseModel){
        iBaseModel.setBean(baseBean);
        iBaseModel.request(false);
    }
}
