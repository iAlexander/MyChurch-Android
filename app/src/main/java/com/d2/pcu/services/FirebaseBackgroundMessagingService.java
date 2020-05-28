package com.d2.pcu.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.d2.pcu.App;
import com.d2.pcu.MainActivity;
import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Random;

import timber.log.Timber;

public class FirebaseBackgroundMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        App.getInstance().getRepositoryInstance().updatePushToken(token);
    }

    private void createNotification(String title, String message, Intent intent) {

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.pcu_firebase_msg_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_calendar)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_news_active))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManagerCompat.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Random random = new Random();
        int rand = random.nextInt(9000);
        notificationManagerCompat.notify(rand, notification);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent intent = new Intent(this, MainActivity.class);

//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.PUSH_NOTIFICATION, "item");

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> map = remoteMessage.getData();

            if (map.containsKey("item")) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                NotificationHistoryItem nhi = gson.fromJson(map.get("item"), NotificationHistoryItem.class);
                App.getInstance().getRepositoryInstance().saveNotificationToDb(nhi);
                intent.putExtra(Constants.PUSH_NOTIFICATION_ID, nhi.getId());
            }
        }

        if (remoteMessage.getNotification() != null) {
            Timber.d("notification: \n%s\n%s", remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            createNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);
        }
    }
}
