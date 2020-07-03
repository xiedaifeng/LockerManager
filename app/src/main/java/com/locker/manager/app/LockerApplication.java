package com.locker.manager.app;

import com.locker.manager.task.LockerTask;
import com.qiao.launch.starter.TaskDispatcher;
import com.qiao.launch.starter.task.Task;
import com.yidao.module_lib.base.BaseApplication;

public class LockerApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        TaskDispatcher.init(this);
        TaskDispatcher.createInstance().addTask(new LockerTask()).start();
    }
}
