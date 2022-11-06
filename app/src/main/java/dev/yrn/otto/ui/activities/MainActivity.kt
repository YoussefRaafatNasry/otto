package dev.yrn.otto.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import com.adamratzman.spotify.auth.implicit.startSpotifyImplicitLoginActivity
import dev.yrn.otto.BuildConfig
import dev.yrn.otto.OttoApp
import dev.yrn.otto.R
import dev.yrn.otto.services.AutoReplyService
import dev.yrn.otto.ui.theme.OttoTheme
import dev.yrn.otto.util.NotificationDebugger


class MainActivity : ComponentActivity() {
    companion object {
        var reply: String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OttoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DebuggerPage(this, reply)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        reply = NotificationDebugger.extractReply(this, intent)
        recreate()
    }
}


@Composable
fun DebuggerPage(activity: Activity, reply: String?) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                trailingIcon = {
                    IconButton(
                        onClick = { NotificationDebugger.send(activity, text) }
                    ) {
                        Icon(Icons.Filled.Send, "Send")
                    }
                }
            )

            if (reply != null) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = reply,
                    onValueChange = { },
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DashboardTile(
                name = "Spotify",
                icon = painterResource(R.drawable.ic_spotify),
                active = (activity.application as OttoApp).store.spotifyToken != null,
                onClick = {
                    activity.startSpotifyImplicitLoginActivity<SpotifyLoginActivity>()
                }
            )

            DashboardTile(
                name = "Reply Service",
                icon = painterResource(R.drawable.ic_otto),
                active = AutoReplyService.isRunning(activity),
                onClick = {}
            )

            DashboardTile(
                name = "Notification",
                icon = rememberVectorPainter(Icons.Filled.Notifications),
                active = NotificationManagerCompat.getEnabledListenerPackages(activity)
                    .contains(BuildConfig.APPLICATION_ID),
                onClick = {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)

                    Toast.makeText(
                        activity,
                        "Edit otto notification options",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }
}

@Composable
fun DashboardTile(
    name: String,
    active: Boolean,
    icon: Painter,
    onClick: () -> Unit
) {
    val color = when (active) {
        true -> MaterialTheme.colors.primary
        false -> MaterialTheme.colors.primary.copy(alpha = 0.6F)
    }

    IconButton(onClick = onClick) {
        Icon(
            icon,
            name,
            Modifier.size(32.dp),
            tint = color
        )
    }
}
