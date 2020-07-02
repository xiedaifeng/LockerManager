package com.locker.manager.callback;

public interface OnItemCallBack<T> {

    void onItemClick(int position, T t, int... i);
}
