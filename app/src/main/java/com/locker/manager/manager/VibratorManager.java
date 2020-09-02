package com.locker.manager.manager;

import android.content.Context;
import android.os.Vibrator;

import com.locker.manager.app.LockerApplication;

public class VibratorManager {

    private static VibratorManager vibratorManager = new VibratorManager();

    Vibrator vibrator = (Vibrator) LockerApplication.getApplication().getSystemService(Context.VIBRATOR_SERVICE);

    public static VibratorManager getInstance(){
        return vibratorManager;
    }

    public void vibrate(long milliseconds){
        vibrator.vibrate(milliseconds);
    }

}
