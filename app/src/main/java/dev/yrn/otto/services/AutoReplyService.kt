package dev.yrn.otto.services

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.RemoteInput

import dev.yrn.otto.models.Template
import dev.yrn.otto.Config

class AutoReplyService : NotificationListenerService() {
    companion object {
        fun isRunning(activity: Activity): Boolean {
            val manager = activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            return manager.getRunningServices(Integer.MAX_VALUE).any {
                it.service.className == AutoReplyService::class.java.name
            }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // Ignore ongoing notifications and excluded packages
        if (sbn.isOngoing || sbn.packageName !in Config.PACKAGES) return

        // A WearableExtender is used to bypass the "Sending reply.." toast
        // when a normal StatusBarNotification is used to invoke actions
        val wearableExtender = NotificationCompat.WearableExtender(sbn.notification)
        val replyAction = wearableExtender.actions.firstOrNull {
            it?.remoteInputs != null && it.title.contains( "reply", true)
        } ?: return

        // Get proper reply from saved rules
        val extras = sbn.notification.extras
        val text = extras.get(NotificationCompat.EXTRA_TEXT).toString()
        val name = extras.get(NotificationCompat.EXTRA_TITLE).toString().substringBefore(" ")
        val rule = Config.RULES.firstOrNull { text matches it.regex } ?: return

        val reply = rule.processReply(
            applicationContext,
            hashMapOf(
                Template.TEXT to text,
                Template.NAME to name
            )
        )

        replyToNotification(sbn.notification.extras, replyAction, reply)
    }

    private fun replyToNotification(bundle: Bundle, action: Action, reply: String) {
        if (action.remoteInputs == null) return;
        val intent = Intent()
        action.remoteInputs!!.forEach { bundle.putCharSequence(it.resultKey, reply) }
        RemoteInput.addResultsToIntent(action.remoteInputs!!, intent, bundle)
        action.actionIntent.send(applicationContext, 0, intent)
    }
}
