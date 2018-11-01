package com.enstrapp.fieldtekpro.Local_PushMessage;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.fcm.NotificationUtils;
import com.enstrapp.fieldtekpro.splashscreen.SplashScreen_Activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Local_PushMessage {
    public String send_local_pushmessage(Context context, String message) {
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder;
        Bitmap icon;
        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_app_icon);
        notificationBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setLargeIcon(icon)
                .setContentTitle("Message From FieldTekPro")
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setContentText(message);
        Intent notificationIntent = new Intent(context, SplashScreen_Activity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));

        notificationManager.notify(id, notification);
        //For Local Notification Written by SUryaKiran

        //COmmented by SUrya Kiran
        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(context);
        notificationUtils.playNotificationSound();

        return null;
    }

}

