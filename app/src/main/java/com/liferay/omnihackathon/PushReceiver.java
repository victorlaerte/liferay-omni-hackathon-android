package com.liferay.omnihackathon;

import com.liferay.mobile.push.PushNotificationsReceiver;

/**
 * @author Victor Oliveira
 */
public class PushReceiver extends PushNotificationsReceiver {
    @Override
    public String getServiceClassName() {
        return PushService.class.getName();
    }
}


