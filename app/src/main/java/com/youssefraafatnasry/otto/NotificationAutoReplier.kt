package com.youssefraafatnasry.otto

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationAutoReplier : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        throw NotImplementedError()
    }

}
