package com.example.lovely_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.telephony.SmsManager;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of App Widget functionality.
 */
public class L_Widget extends AppWidgetProvider {

    private SharedPreferences sharedPreferences;

    public static String WIDGET_BUTTON = "com.example.lovely_widget.WIDGET_BUTTON";

    // Public = Accessable from other classes, static meaning data is still, = gives it a variable Widget_Button


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.l__widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //Inside this command, this updates the widget to ensure data is processed timely.

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //sharedPreferences = context.getSharedPreferences("numbers", Context.MODE_PRIVATE);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.l__widget);

            remoteViews.setOnClickPendingIntent(R.id.lovebutton,
                    getPendingSelfIntent(context,
                            WIDGET_BUTTON)
            );

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

        //Inside this command, this updates the widget, and adds a on Click function, setting it up to be a button.

    }

    @Override
    public void onEnabled(Context context) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (WIDGET_BUTTON.equals(intent.getAction())) {
            this.sendMessage();
        }

        // This command is a if statement, practically acting as a control method.
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        // An explicit intent directed at the current class (the "self").
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    // Unsure what this command is.

    public void sendMessage () {
        try {

            SmsManager smgr = SmsManager.getDefault();

            Map<String,?> numbers = sharedPreferences.getAll();

            for (Iterator<?> i = numbers.values().iterator(); i.hasNext();) {

                System.out.println((String)i.next());

                smgr.sendTextMessage((String)i.next(),"+61 490457366","Test",null,null);

            };

        }
        catch (Exception e) {
            System.out.println(e.getMessage()); // This prints out an error//
        }

        // This command prints, and sends out a message.
    }

}

//public vs private void:
// Inside of a class code, you can have a public and private function.
// The private function can only be accessed by the class itself.
// The public function can be accessed from other classes.
// Private functions just mean other classes can't access the code.

//Void is a return value. It means no return value is there and it wont return any information.

