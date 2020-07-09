package com.qiao.serialport.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/11/26 0026.
 */

public class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        try {


        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }


}
