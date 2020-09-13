package com.locker.manager.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.locker.manager.R;
import com.locker.manager.adapter.SerialAdapter;
import com.locker.manager.callback.OnItemCallBack;

public class SerialPopwindow {


    public void show(Context context, TextView view){
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_serial, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);

        PopupWindow popWnd = new PopupWindow (context);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SerialAdapter adapter = new SerialAdapter(context);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemCallBack(new OnItemCallBack<String>() {
            @Override
            public void onItemClick(int position, String s, int... i) {
                view.setText(s);
                popWnd.dismiss();
            }
        });

        popWnd.showAsDropDown(view,40,0);
    }
}
