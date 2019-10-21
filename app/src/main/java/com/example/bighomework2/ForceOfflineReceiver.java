package com.example.bighomework2;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("recover","接收到强制下线广播");
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Warming");
        builder.setMessage("下线成功！请重新登录！");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                Intent i=new Intent(context,LoginActivity.class);
                context.startActivity(i);
            }
        });
        builder.show();
    }
}
