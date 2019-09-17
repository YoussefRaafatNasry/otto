package com.youssefraafatnasry.otto.services

import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.RemoteInput
import com.youssefraafatnasry.otto.util.Config
import com.youssefraafatnasry.otto.models.Template

class AutoReplyService : NotificationListenerService() {

    private val REPLY_KEYWORD = "reply"

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        // Ignore ongoing notifications and excluded packages
        if (sbn.isOngoing || sbn.packageName !in Config.PACKAGES) return

        // A WearableExtender is used to bypass the "Sending reply.." toast
        // when a normal StatusBarNotification is used to invoke actions
        val wearableExtender = NotificationCompat.WearableExtender(sbn.notification)
        val replyAction = wearableExtender.actions.firstOrNull {
            it?.remoteInputs != null && it.title.contains(REPLY_KEYWORD, true)
        } ?: return

        // Get proper reply from saved rules
        val extras = sbn.notification.extras
        val text   = extras.get(NotificationCompat.EXTRA_TEXT).toString()
        val name   = extras.get(NotificationCompat.EXTRA_TITLE).toString().substringBefore(" ")
        val rule   = Config.RULES.firstOrNull { text matches it.regex } ?: return
        val reply  = rule.processReply(
            hashMapOf(
                Template.TEXT to text,
                Template.NAME to name
            )
        )

        replyToNotification(sbn.notification.extras, replyAction, reply)

    }

    private fun replyToNotification(bundle: Bundle, action: Action, reply: String) {
        val intent = Intent()
        action.remoteInputs.forEach { bundle.putCharSequence(it.resultKey, reply) }
        RemoteInput.addResultsToIntent(action.remoteInputs, intent, bundle)
        action.actionIntent.send(applicationContext, 0, intent)
    }

}
