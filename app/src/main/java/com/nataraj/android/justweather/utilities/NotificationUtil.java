package com.nataraj.android.justweather.utilities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nataraj.android.justweather.MainActivity;
import com.nataraj.android.justweather.R;

public final class NotificationUtil {

    static final String NOTIFICATION_3H_CHANNEL_ID = "com.nataraj.android.justweather.3_hour_notification";
    private static final int NOTIFICATION_3H_ID = 1;

    private static NotificationCompat.Builder getNotificationBuilder(Context context, String city, String temp, int weatherIcon) {

        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_small_layout);
        RemoteViews notificationBigLayout = new RemoteViews(context.getPackageName(), R.layout.notification_large_layout);

        notificationLayout.setTextViewText(R.id.notification_city_name, city);
        notificationBigLayout.setTextViewText(R.id.notification_city_name, city);
        notificationLayout.setTextViewText(R.id.notification_temp_view, temp);
        notificationBigLayout.setTextViewText(R.id.notification_temp_view, temp);
        notificationLayout.setImageViewResource(R.id.notification_weather_icon, weatherIcon);
        notificationBigLayout.setImageViewResource(R.id.notification_weather_icon, weatherIcon);

        return new NotificationCompat.Builder(context, NOTIFICATION_3H_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationBigLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getIntent(context))
                .setAutoCancel(true);
    }

    private static PendingIntent getIntent(Context context) {
        Intent pendingIntent = new Intent(context, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, pendingIntent, 0);
    }

    public static void showNotification(Context context, String city, String temp, int weatherIcon) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_3H_ID, getNotificationBuilder(context, city, temp, weatherIcon).build());
    }
}
