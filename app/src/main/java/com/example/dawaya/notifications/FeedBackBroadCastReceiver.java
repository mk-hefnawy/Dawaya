package com.example.dawaya.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.dawaya.R;
import com.example.dawaya.ui.MyOrdersActivity;


public class FeedBackBroadCastReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "NotificationId" ;


    NotificationCompat.Builder notificationBuilder;
    String notificationId;
    String channelId;
    String notificationBody;
    String notificationTitle;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

         channelId = intent.getStringExtra("ChannelId");
         notificationBody = intent.getStringExtra("NotificationBody");
         notificationTitle = intent.getStringExtra("NotificationTitle");

         notificationBuilder = getNotificationBuilder(context);
         respond(context, intent, notificationBuilder);


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void respond(Context context, Intent intent, NotificationCompat.Builder builder){
        String orderId = intent.getStringExtra("OrderId");

        Intent onNotificationClickedIntent = new Intent(context, MyOrdersActivity.class);
        onNotificationClickedIntent.putExtra("orderId", orderId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MyOrdersActivity.class);
        stackBuilder.addNextIntent(onNotificationClickedIntent);

        notificationId = intent.getStringExtra(NOTIFICATION_ID);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        //Notification Channel
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = new NotificationChannel(channelId , "NOTIFICATION_CHANNEL_NAME" , importance) ;
        Log.v("ChannelId", channelId);
        notificationManager.createNotificationChannel(notificationChannel) ;


        notificationManager.notify(Integer.parseInt(notificationId), builder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder (Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationId);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationBody);
        builder.setVibrate(new long[] { 500, 1000, 5000, 1000, 500 });
        builder.setSmallIcon(R.drawable.outline_notifications_black_36);
        builder.setAutoCancel(true);
        builder.setChannelId(channelId);
        return builder;
    }

}
