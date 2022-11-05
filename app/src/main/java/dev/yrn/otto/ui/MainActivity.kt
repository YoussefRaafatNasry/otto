package dev.yrn.otto.ui

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import dev.yrn.otto.BuildConfig
import dev.yrn.otto.R
import dev.yrn.otto.service.AutoReplyService
import dev.yrn.otto.service.MainTileService
import dev.yrn.otto.service.SpotifyService
import dev.yrn.otto.ui.theme.OttoTheme


val NOTIFICATION_CHANNEL_ID = "DBG_CHNL"
val NOTIFICATION_ID = 0x21
val REPLY_KEY = "key_reply_text"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OttoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DebuggerPage(this)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        SpotifyService.initAccessToken(requestCode, resultCode, intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return;
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val reply = bundle?.getCharSequence(REPLY_KEY).toString()
        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.cancel(NOTIFICATION_ID)
        Toast.makeText(this, reply, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun DebuggerPage(activity: Activity) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { it ->
                    text = it
                },
                trailingIcon = {
                    IconButton(onClick = {
                        sendMessage(activity, text)
                    }) {
                        Icon(Icons.Filled.Send, "Send")
                    }
                }
            )
        }
        Row {
            IconButton(onClick = {
                SpotifyService.authenticateSpotify(activity)
            }) {
                Icon(
                    painterResource(R.drawable.ic_spotify),
                    "Spotify"
                )
            }
            IconButton(onClick = {
                Toast.makeText(
                    activity,
                    "${MainTileService::class.simpleName} ${
                        MainTileService.isListenerEnabled(
                            activity
                        )
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(
                    painterResource(R.drawable.ic_otto),
                    "otto",
                    Modifier.size(24.dp)
                )
            }
            IconButton(onClick = {
                Toast.makeText(
                    activity,
                    "${AutoReplyService::class.simpleName} ${AutoReplyService.isRunning(activity)}",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(Icons.Filled.Notifications, "Notifications")
            }
        }
    }
}

fun sendMessage(context: Context, text: String) {
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

    val sender = "Debugger"
    val notification = NotificationCompat.Builder(context, BuildConfig.APPLICATION_ID)
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.ic_otto)
        .setContentTitle(sender)
        .setContentText(text)
        .setContentIntent(pendingIntent)
        .setChannelId(NOTIFICATION_CHANNEL_ID)
        .addAction(action)
        .extend(NotificationCompat.WearableExtender().addAction(action))
        .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        .build()

    val notificationChannel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        "Debug Notification",
        NotificationManager.IMPORTANCE_HIGH
    )

    val nManager =
        context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager
    nManager.createNotificationChannel(notificationChannel)
    nManager.notify(NOTIFICATION_ID, notification)
}



