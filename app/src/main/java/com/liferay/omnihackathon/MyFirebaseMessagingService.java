package com.liferay.omnihackathon;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.liferay.mobile.sdk.json.JSONParser;
import com.liferay.omnihackathon.model.Processo;
import com.liferay.omnihackathon.util.Constants;
import com.liferay.omnihackathon.view.ItemDetailActivity;
import com.liferay.omnihackathon.view.WebViewActivity;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        RemoteMessage.Notification notification =
            remoteMessage.getNotification();

        if (notification != null && notification.getBody() != null) {

            String title = notification.getTitle();
            String body = notification.getBody();

            JsonElement parse = JSONParser.parse(body);
            JsonObject asJsonObject = parse.getAsJsonObject();

            String name = asJsonObject.get("name").getAsString();
            String status = asJsonObject.get("status").getAsString();

            Processo process = new Processo(name, status);

            NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);

            int color = ContextCompat.getColor(this, R.color.colorPrimary);
            Uri defaultSound = RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION);

            Intent urlIntent =
                new Intent(getApplicationContext(), ItemDetailActivity.class);
            urlIntent.putExtra(Constants.ITEM, process);
            PendingIntent contentIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, urlIntent,
                    0);

            NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
            builder.setAutoCancel(true);
            builder.setCategory(NotificationCompat.CATEGORY_SOCIAL);
            builder.setColor(color);
            builder.setContentIntent(contentIntent);
            builder.setContentText(name);
            builder.setContentTitle(title);
            builder.setDefaults(Notification.DEFAULT_VIBRATE);
            //builder.setLargeIcon(_getLargeIcon(portraitURL));
            builder.setLights(color, 300, 100);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setSound(defaultSound);

            manager.notify(remoteMessage.getNotification().getBody().hashCode(),
                builder.build());
        }
    }
}
