package com.youssefraafatnasry.otto

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class NotificationAutoReplier : NotificationListenerService() {

    private val REPLY_KEYWORD = "reply"

    inner class RepliableNotification(
        var bundle: Bundle,
        var pendingIntent: PendingIntent,
        var remoteInputs: Array<RemoteInput>
    )

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        // Ignore ongoing notifications and excluded packages
        if (!Config.IS_ACTIVE || sbn.isOngoing || sbn.packageName !in Config.PACKAGES) return

        // Get proper reply from saved rules
        val text = sbn.notification.extras.get(NotificationCompat.EXTRA_TEXT).toString()
        val name = sbn.notification.extras.get(NotificationCompat.EXTRA_TITLE).toString().substringBefore(" ")
        val reply: String
        try {
            val rule = Config.RULES.first { text matches it.regex }
            reply = rule.fullReply
                .replace(ReplyRule.TEXT, text.replace(rule.exclude, "", true))
                .replace(ReplyRule.NAME, name)
        } catch (e: NoSuchElementException) {
            return
        }

        // A WearableExtender is used to bypass the "Sending reply.." toast
        // when a normal StatusBarNotification is used to invoke actions
        val wearableExtender = NotificationCompat.WearableExtender(sbn.notification)
        val replyAction: NotificationCompat.Action
        try {
            replyAction = wearableExtender.actions.first {
                it != null && it.remoteInputs != null && it.title.contains(REPLY_KEYWORD, true)
            }
        } catch (e: NoSuchElementException) {
            return
        }

        // Reply to notification
        val intent = Intent()
        val n = RepliableNotification(
            sbn.notification.extras,
            replyAction.actionIntent,
            replyAction.remoteInputs
        )

        n.remoteInputs.forEach { n.bundle.putCharSequence(it.resultKey, reply) }
        RemoteInput.addResultsToIntent(n.remoteInputs, intent, n.bundle)
        n.pendingIntent.send(applicationContext, 0, intent)

    }

}
