package com.youssefraafatnasry.otto.activities

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.RemoteInput
import com.youssefraafatnasry.otto.BuildConfig
import com.youssefraafatnasry.otto.R
import com.youssefraafatnasry.otto.services.AutoReplyService
import com.youssefraafatnasry.otto.services.MainTileService
import com.youssefraafatnasry.otto.util.SpotifyAPI
import com.youssefraafatnasry.otto.util.TextFormatter.appendTextFormatted
import com.youssefraafatnasry.otto.util.TextFormatter.setTextFormatted
import kotlinx.android.synthetic.main.activity_debug.*


class DebuggerActivity : AppCompatActivity() {

    private val NOTIFICATION_ID = 0x21
    private val REPLY_KEY       = "key_reply_text"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        send_button.setOnClickListener {
            sendMessage()
        }

        rules_button.setOnClickListener {
            Toast.makeText(this@DebuggerActivity, "Not Implemented yet!", Toast.LENGTH_SHORT).show()
        }

        spotify_button.setOnClickListener {
            verifySpotify()
        }

        notifications_button.setOnClickListener {
            verifyService()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle   = RemoteInput.getResultsFromIntent(intent)
        val reply    = bundle?.getCharSequence(REPLY_KEY).toString()
        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.cancel(NOTIFICATION_ID)
        result_text_view.setTextFormatted(reply)
    }

    private fun sendMessage() {

        if (message_edit_text.text.isEmpty())
            return

        val resultIntent = Intent(this@DebuggerActivity, DebuggerActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this@DebuggerActivity,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val remoteInput = RemoteInput.Builder(REPLY_KEY)
            .setLabel(getString(R.string.enter_message))
            .build()

        val action = Action.Builder(R.drawable.ic_otto_black, "Reply", pendingIntent)
            .addRemoteInput(remoteInput)
            .build()

        val sender       = "Debugger"
        val content      = message_edit_text.text.toString()
        val notification = NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_otto_black)
            .setContentTitle(sender)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .addAction(action)
            .extend(NotificationCompat.WearableExtender().addAction(action))
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .build()

        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(NOTIFICATION_ID, notification)
        message_edit_text.text.clear()
        result_text_view.text = getString(R.string.waiting)
    }

    private fun verifySpotify() {
        if (SpotifyAPI.ACCESS_TOKEN.isNotEmpty()) {
            result_text_view.setTextFormatted("*Spotify* Authenticated")
        } else {
            result_text_view.setTextFormatted("*Spotify* Unauthenticated")
        }
    }

    private fun verifyService() {

        if (MainTileService.isListenerEnabled(this@DebuggerActivity)) {
            result_text_view.setTextFormatted("*Listener* Enabled\n")
        } else {
            result_text_view.setTextFormatted("*Listener* Disabled\n")
        }

        if (isServiceRunning()) {
            result_text_view.appendTextFormatted("*Service* Running")
        } else {
            result_text_view.appendTextFormatted("*Service* Stopped")
        }

    }

    private fun isServiceRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return activityManager.getRunningServices(Integer.MAX_VALUE).any {
            it.service.className == AutoReplyService::class.java.name
        }
    }

}
