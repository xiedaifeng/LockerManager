package com.locker.manager.callback;

public interface OnItemClickListener<T>{
        void onItemClick(int position, T t);
        void onItemLongClick(int position, T t);
    }