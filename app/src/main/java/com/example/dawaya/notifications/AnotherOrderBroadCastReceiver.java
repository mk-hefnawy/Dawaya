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
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.ui.HomeActivity;
import com.example.dawaya.ui.MyOrdersActivity;
import com.example.dawaya.ui.SearchFrag;
import com.example.dawaya.viewmodels.SearchViewModel;
import com.google.gson.Gson;

public class AnotherOrderBroadCastReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "NotificationId";
    public static String NOTIFICATION_BODY = "NotificationBody";
    public static String NOTIFICATION_TITLE = "NotificationTitle";
    public static String NOTIFICATION_CHANNEL_ID = "ChannelId";
    public static String PRODUCT = "Product";

    NotificationCompat.Builder notificationBuilder;
    String notificationId;
    String channelId;
    String notificationBody;
    String notificationTitle;

    String productString;
    ProductModel product;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        notificationId = intent.getStringExtra(NOTIFICATION_ID);
        channelId = intent.getStringExtra(NOTIFICATION_CHANNEL_ID);
        notificationBody = intent.getStringExtra(NOTIFICATION_BODY);
        notificationTitle = intent.getStringExtra(NOTIFICATION_TITLE);
        notificationBuilder = getNotificationBuilder(context);

        productString = intent.getStringExtra(PRODUCT);
        product = new Gson().fromJson(productString, ProductModel.class);

        respond(context, intent, notificationBuilder);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void respond(Context context, Intent intent, NotificationCompat.Builder notificationBuilder) {

        Intent onNotificationClickedIntent = new Intent(context, HomeActivity.class);
        onNotificationClickedIntent.putExtra("Product", productString);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(onNotificationClickedIntent);

        notificationId = intent.getStringExtra(NOTIFICATION_ID);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        //Notification Channel
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = new NotificationChannel(channelId , "NOTIFICATION_CHANNEL_NAME" , importance) ;
        Log.v("ChannelId", channelId);
        notificationManager.createNotificationChannel(notificationChannel) ;


        notificationManager.notify(Integer.parseInt(notificationId), notificationBuilder.build());
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
