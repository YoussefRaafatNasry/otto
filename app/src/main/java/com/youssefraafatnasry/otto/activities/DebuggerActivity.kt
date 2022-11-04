package com.youssefraafatnasry.otto.activities

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.RemoteInput
import com.youssefraafatnasry.otto.BuildConfig
import com.youssefraafatnasry.otto.R
import com.youssefraafatnasry.otto.databinding.ActivityDebugBinding
import com.youssefraafatnasry.otto.services.AutoReplyService
import com.youssefraafatnasry.otto.services.MainTileService
import com.youssefraafatnasry.otto.util.SpotifyAPI
import com.youssefraafatnasry.otto.util.TextFormatter.appendTextFormatted
import com.youssefraafatnasry.otto.util.TextFormatter.setTextFormatted


class DebuggerActivity : AppCompatActivity() {
    private val NOTIFICATION_CHANNEL_ID = "DBG_CHNL"
    private val NOTIFICATION_ID = 0x21
    private val REPLY_KEY = "key_reply_text"
    private lateinit var binding: ActivityDebugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener {
            vibrate()
            sendMessage()
        }

        binding.rulesButton.setOnClickListener {
            vibrate()
            val intent = Intent(this@DebuggerActivity, RulesActivity::class.java)
            startActivity(intent)
        }

        binding.spotifyButton.setOnClickListener {
            vibrate()
            verifySpotify()
        }

        binding.notificationsButton.setOnClickListener {
            vibrate()
            verifyService()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return;
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val reply = bundle?.getCharSequence(REPLY_KEY).toString()
        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.cancel(NOTIFICATION_ID)
        binding.resultTextView.setTextFormatted(reply)
    }

    private fun vibrate() {
        val vibe = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(25)
    }

    private fun sendMessage() {

        if (binding.messageEditText.text.isEmpty())
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

        val sender = "Debugger"
        val content = binding.messageEditText.text.toString()
        val notification = NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_otto_black)
            .setContentTitle(sender)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .addAction(action)
            .extend(NotificationCompat.WearableExtender().addAction(action))
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .build()

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Debug Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.createNotificationChannel(notificationChannel)
        nManager.notify(NOTIFICATION_ID, notification)
        binding.messageEditText.text.clear()
        binding.resultTextView.text = getString(R.string.waiting)
    }

    private fun verifySpotify() {
        if (SpotifyAPI.ACCESS_TOKEN.isNotEmpty()) {
            binding.resultTextView.setTextFormatted("*Spotify* Authenticated")
        } else {
            binding.resultTextView.setTextFormatted("*Spotify* Unauthenticated")
        }
    }

    private fun verifyService() {

        if (MainTileService.isListenerEnabled(this@DebuggerActivity)) {
            binding.resultTextView.setTextFormatted("*Listener* Enabled\n")
        } else {
            binding.resultTextView.setTextFormatted("*Listener* Disabled\n")
        }

        if (isServiceRunning()) {
            binding.resultTextView.appendTextFormatted("*Service* Running")
        } else {
            binding.resultTextView.appendTextFormatted("*Service* Stopped")
        }

    }

    private fun isServiceRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return activityManager.getRunningServices(Integer.MAX_VALUE).any {
            it.service.className == AutoReplyService::class.java.name
        }
    }

}
