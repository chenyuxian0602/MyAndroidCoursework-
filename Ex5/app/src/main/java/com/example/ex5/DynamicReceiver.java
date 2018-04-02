package com.example.ex5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.example.ex5.dynamicreceiver";

    public DynamicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals(DYNAMICACTION)) {
            Bundle bd = intent.getExtras();
            String input = (String) bd.get("input");
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.apple);

            builder.setContentTitle("动态广播")
                    .setContentText(input)
                    .setLargeIcon(bm)
                    .setSmallIcon(R.mipmap.dynamic)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis());

            Intent mintent = new Intent(context, MainActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mintent, 0);
            builder.setContentIntent(mPendingIntent);

            Notification notify = builder.build();
            manager.notify(0, notify);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.fruit_widget);
            rv.setTextViewText(R.id.fruit_widget_text, bd.getString("input"));
            rv.setImageViewResource(R.id.fruit_widget_img, R.mipmap.dynamic);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context, FruitWidget.class), rv);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
