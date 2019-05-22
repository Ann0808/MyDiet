package com.pisk.mydiet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, ProductsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Resources res = context.getResources();

        builder.setContentIntent(contentIntent)
                // обязательные настройки
                .setSmallIcon(R.drawable.balance)
                .setContentTitle("Напоминание!")
                .setContentText("Завтра начало диеты") // Текст уведомления
                // необязательные настройки
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.strong)) // большая
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = new long[] { 500, 0, 500, 500, 500 };
       // Resources systemResources = Resources.getSystem();
//        builder.setLights(
//                ContextCompat.getColor(context, systemResources
//                        .getIdentifier("config_defaultNotificationColor", "color", "android")),
//                res.getInteger(systemResources
//                        .getIdentifier("config_defaultNotificationLedOn", "integer", "android")),
//                res.getInteger(systemResources
//                        .getIdentifier("config_defaultNotificationLedOff", "integer", "android")));
        builder.setLights(Color.RED, 1, 1); // will blink
        builder.setVibrate(vibrate);
        builder.setSound(ringURI);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(101, builder.build());
    }
}