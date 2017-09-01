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
import com.liferay.mobile.push.PushNotificationsService;
import com.liferay.omnihackathon.view.WebViewActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class PushService extends PushNotificationsService {
    @Override
    public void onPushNotification(JSONObject pushNotification) {
        super.onPushNotification(pushNotification);

        Log.d("onPushNotification", pushNotification.toString());

        try {
            String body =
                pushNotification.has("body") ? pushNotification.getString(
                    "body").toString() : "";

            NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);

            int color = ContextCompat.getColor(this, R.color.colorPrimary);
            Uri defaultSound = RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION);

            Intent urlIntent = new Intent(getApplicationContext(), WebViewActivity.class);
            urlIntent.putExtra("URL", "http://192.168.109.23:8080/c/portal/login?p_l_id=20146");
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, urlIntent, 0);

            NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
            builder.setAutoCancel(true);
            builder.setCategory(NotificationCompat.CATEGORY_SOCIAL);
            builder.setColor(color);
            builder.setContentIntent(contentIntent);
            builder.setContentText(body);
            builder.setContentTitle("Notification TEST");
            builder.setDefaults(Notification.DEFAULT_VIBRATE);
            //builder.setLargeIcon(_getLargeIcon(portraitURL));
            builder.setLights(color, 300, 100);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setSound(defaultSound);

            manager.notify(body.hashCode(), builder.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
