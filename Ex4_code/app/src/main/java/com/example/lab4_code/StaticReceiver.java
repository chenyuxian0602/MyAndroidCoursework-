package com.example.lab4_code;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by 陈昱宪 on 2016/10/23.
 */

public class StaticReceiver extends BroadcastReceiver{
    public static final String STATICACTION = "com.example.Lab4_code.staticreceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(STATICACTION)) {
            Bundle bd = intent.getExtras();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            //Resources res = Resources.getSystem();
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.apple);

            //int i = bd.getInt("img");
            //Log.e("ii",String.valueOf(i));
            builder.setContentTitle("静态广播")
                    .setContentText(bd.getString("name"))
                    .setLargeIcon(bm)
                    //.setPriority(Notification.PRIORITY_HIGH);
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("a new massage")
                    .setSmallIcon(bd.getInt("img"))
                    .setWhen(System.currentTimeMillis());

            Notification notify = builder.build();
            manager.notify(0, notify);
            Log.w("build", bd.getString("name"));
        }
    }
}
