package com.yidao.module_lib.utils;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.yidao.module_lib.base.BaseResponseBean;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RefreshUtils {

    public static void refreshWithData(SmartRefreshLayout refreshLayout, ResponseBean responseBean) {
        if (refreshLayout == null) {
            return;
        }

        View childAt = refreshLayout.getChildAt(0);
        if (!(childAt instanceof RecyclerView)) {
            return;
        }
        RecyclerView child = (RecyclerView) childAt;
        if (!(child.getAdapter() instanceof BaseQuickAdapter)) {
            return;
        }
        BaseQuickAdapter quickAdapter = (BaseQuickAdapter) child.getAdapter();
        if (quickAdapter == null) {
            return;
        }
        if(responseBean.getParseClass()==null){
            return;
        }

        List<BaseResponseBean> beans = JSON.parseArray(responseBean.getData(), responseBean.getParseClass());

        RefreshState state = refreshLayout.getState();
        if (state == RefreshState.Refreshing || state == RefreshState.None) {
            quickAdapter.setNewData(beans);
            refreshLayout.finishRefresh();
            refreshLayout.setNoMoreData(false);
        } else {
            refreshLayout.finishLoadMore();
            quickAdapter.addData(beans);
            if (responseBean.getPage().isNoMoreData()) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }


    public static void finishRefresh(SmartRefreshLayout refreshLayout) {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.finishRefresh(false);
        refreshLayout.finishLoadMore(false);
    }



    public static void removeRefresh(BaseQuickAdapter quickAdapter,int position) {
        if (quickAdapter == null) {
            return;
        }
        List data = quickAdapter.getData();
        if(data!=null){
            int previousSize = data.size();
            quickAdapter.getData().clear();
            data.remove(position);
            quickAdapter.notifyItemRangeRemoved(0,previousSize);
            quickAdapter.setNewData(data);
//            quickAdapter.notifyItemRangeInserted(0, data.size());
        }
    }
}
