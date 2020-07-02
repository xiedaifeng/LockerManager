package com.yidao.module_lib.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;


import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;

import java.util.List;

/**
 * Created by xiaochan on 2017/7/26.
 */

public abstract class BaseAdapterPress<T extends BaseAdapterView> extends BaseAdapter implements IBasePress {

    private Class<T> viewClass;

    private AdapterView parent;

    protected List<Object> dataList;

    private Context context;

    protected IBaseView mAttachView;

    public BaseAdapterPress(Context context, Class<T> classzz, AdapterView parent) {
        this.context = context;
        this.viewClass = classzz;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    public void setList(List<Object> mList) {
        dataList = mList;
    }

    public List<Object> getList() {
        return dataList;
    }

    public AdapterView getParent() {
        return parent;
    }

    public void setAttachView(IBaseView attachView){
        this.mAttachView = attachView;
    }

    public void destoryAttachView(){
        mAttachView = null;
        parent = null;
        dataList.clear();
        dataList = null;
        context = null;
    }

    public BaseView getMainView() {
        return (BaseView) context;
    }

    public void setDataList(List<Object> list) {
        dataList = list;
        parent.setAdapter(this);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //解决item数据混乱问题,但是无法复用
        T itemView = null;
        if (convertView == null) {
            try {
                itemView = viewClass.newInstance();
                itemView.initView(context, parent);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            convertView = itemView.getCurrentView();
            convertView.setTag(itemView);
        } else {
            itemView = (T) convertView.getTag();
        }

        if (getCount() > 0) {
            bindData(getItem(position), itemView, position);
        }

        return itemView.getCurrentView();
    }

    @Override
    public void success(ResponseBean responseBean) {

    }

    @Override
    public void failed(ResponseBean responseBean) {
    }

    protected abstract void bindData(Object bean, T v, int position);
}
