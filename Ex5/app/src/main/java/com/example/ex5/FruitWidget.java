package com.example.ex5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class FruitWidget extends AppWidgetProvider {

    private static final String STATICACTION = "com.example.ex5.staticreceiver";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fruit_widget);
        views.setTextViewText(R.id.fruit_widget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent clickInt = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, clickInt, 0);
        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.fruit_widget);
        rv.setOnClickPendingIntent(R.id.fruit_widget_img, pi);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.fruit_widget);
        Bundle bd = intent.getExtras();

        if (intent.getAction().equals(STATICACTION)) {
            Fruit fruit = (Fruit) bd.get("item");
            String name = fruit.getName();
            rv.setTextViewText(R.id.fruit_widget_text, name);
            rv.setImageViewResource(R.id.fruit_widget_img, fruit.getImg());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context, FruitWidget.class), rv);
        }
        //else if (intent.getAction().equals(DYNAMICACTION)) {
        //    rv.setImageViewResource(R.id.fruit_widget_img, R.mipmap.dynamic);
        //    rv.setTextViewText(R.id.fruit_widget_text, bd.getString("input"));
        //    Log.w("broadcast", "dynamic");
        //}


        //updateAppWidget(context, AppWidgetManager.getInstance(context), R.layout.fruit_widget);

    }
}

