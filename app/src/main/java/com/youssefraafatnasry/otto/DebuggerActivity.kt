package com.youssefraafatnasry.otto

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_debug.*

class DebuggerActivity : AppCompatActivity() {

    private val notificationId = 0x21
    private val replyKey = "key_reply_text"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        send_button.setOnClickListener {

            val resultIntent = Intent(this@DebuggerActivity, DebuggerActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                this@DebuggerActivity,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val remoteInput = RemoteInput.Builder(replyKey)
                .setLabel(getString(R.string.enter_message))
                .build()

            val action = Action.Builder(R.drawable.ic_otto, "Reply", pendingIntent)
                .addRemoteInput(remoteInput)
                .build()

            val sender = "Debugger"
            val content = message_edit_text.text.toString()

            val notification = NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_otto)
                .setContentTitle(sender)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .addAction(action)
                .extend(NotificationCompat.WearableExtender().addAction(action))
                .build()

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, notification)
            message_edit_text.text.clear()
            result_text_view.text = getString(R.string.waiting)

        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val reply = bundle?.getCharSequence(replyKey).toString()
        result_text_view.text = reply
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

}
