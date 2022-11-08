package dev.yrn.otto.services

import android.app.Activity
import android.app.ActivityManager
import android.app.Notification
import android.app.Notification.*
import android.app.RemoteInput
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dev.yrn.otto.Config
import dev.yrn.otto.models.Template

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

        // Ignore un-reply-able notifications
        val notification = sbn.notification
        val replyAction = notification?.actions?.firstOrNull {
            it?.remoteInputs != null && it.title.contains("reply", true)
        } ?: return

        reply(notification, replyAction)
    }

    private fun reply(notification: Notification, action: Action) {
        val extras = notification.extras
        val text = extras.get(EXTRA_TEXT).toString()
        val name = extras.get(EXTRA_TITLE).toString().substringBefore(" ")
        val rule = Config.RULES.firstOrNull { text matches it.regex } ?: return

        // Stop otto from recursively replying to itself
        if (text.contains(Config.PREFIX)) return

        val intent = Intent()
        val reply = rule.processReply(
            text,
            hashMapOf(
                Template.text() to text,
                Template.name() to name
            )
        )

        rule.command?.execute(applicationContext, text)
        action.remoteInputs.forEach { extras.putCharSequence(it.resultKey, reply) }
        RemoteInput.addResultsToIntent(action.remoteInputs, intent, extras)
        action.actionIntent.send(applicationContext, 0, intent)
    }
}
