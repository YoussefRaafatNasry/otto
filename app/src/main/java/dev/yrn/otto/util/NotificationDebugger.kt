package dev.yrn.otto.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import dev.yrn.otto.BuildConfig
import dev.yrn.otto.R
import dev.yrn.otto.ui.activities.MainActivity

object NotificationDebugger {
    private const val NOTIFICATION_ID = 0x21
    private const val CHANNEL_ID = "debug_channel"
    private const val REPLY_KEY = "key_reply_text"

    private fun getManager(context: Context): NotificationManager {
        return context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun extractReply(context: Context, intent: Intent): String? {
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val manager = getManager(context)
        manager.cancel(NOTIFICATION_ID)

        return bundle?.getCharSequence(REPLY_KEY)?.toString()
    }

    fun send(context: Context, text: String) {
        val resultIntent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val remoteInput = RemoteInput.Builder(REPLY_KEY)
            .setLabel("Enter Message...")
            .build()

        val action = NotificationCompat.Action.Builder(R.drawable.ic_otto, "Reply", pendingIntent)
            .addRemoteInput(remoteInput)
            .build()

        val notification = NotificationCompat.Builder(context, BuildConfig.APPLICATION_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_otto)
            .setContentTitle("Debugger")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)
            .addAction(action)
            .extend(NotificationCompat.WearableExtender().addAction(action))
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .build()

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Debug Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager = getManager(context)
        manager.createNotificationChannel(notificationChannel)
        manager.notify(NOTIFICATION_ID, notification)
    }
}