package com.example.custom.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.custom.R;
import com.example.custom.widget.calendar.ClockView;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 */
public class ClockWidget extends AppWidgetProvider {
    private static final String TAG = "ClockWidget";
    private static final String ACTION_UPDATE_TIME = "action_update_time";
    private static ClockView clockView;
    private static Bitmap bitmap;
    private static Canvas canvas;
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private static Set<Integer> widgetIds = new HashSet<>();
    private static Paint paint;
    private static PorterDuffXfermode clear;
    private static PorterDuffXfermode src;

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d(TAG, "updateAppWidget");
        if (clockView == null) {
            clockView = new ClockView(context);
            clockView.setAutoUpdate(false);
            bitmap = Bitmap.createBitmap(clockView.getRealWidth(), clockView.getRealHeight(), Bitmap.Config.ARGB_4444);
            canvas = new Canvas(bitmap);
            paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            src = new PorterDuffXfermode(PorterDuff.Mode.SRC);
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Intent intent =new Intent(ACTION_UPDATE_TIME);
                    context.sendBroadcast(intent);
                }
            }, 0,1000L, TimeUnit.MILLISECONDS);
        }
        paint.setXfermode(clear);
        canvas.drawPaint(paint);
        paint.setXfermode(src);
        clockView.draw(canvas);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setBitmap(R.id.clock_view, "setImageBitmap", bitmap);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            widgetIds.add(appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase(ACTION_UPDATE_TIME)) {
            int[] widgets = new int[widgetIds.size()];
            int index = 0;
            for (Integer widgetId : widgetIds) {
                widgets[index++] = widgetId;
            }
            onUpdate(context, AppWidgetManager.getInstance(context), widgets);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

