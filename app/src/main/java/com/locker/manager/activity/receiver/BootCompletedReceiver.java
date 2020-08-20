package com.locker.manager.activity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.locker.manager.activity.HomeActivity;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent data) {
        String action = data.getAction();
        if (action.equals(ACTION_BOOT_COMPLETED)){
            Intent intent = new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
