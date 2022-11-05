package dev.yrn.otto.service

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages
import dev.yrn.otto.BuildConfig
import dev.yrn.otto.R

class MainTileService : TileService() {
    companion object {
        fun isListenerEnabled(context: Context): Boolean {
            return getEnabledListenerPackages(context).contains(BuildConfig.APPLICATION_ID)
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        if (isListenerEnabled(this)) {
            updateState(Tile.STATE_ACTIVE)
        } else {
            updateState(Tile.STATE_INACTIVE)
        }
    }

    override fun onClick() {
        super.onClick()
        if (qsTile.state == Tile.STATE_INACTIVE) {
            startSettings("Enable Notification Listener")
            updateState(Tile.STATE_ACTIVE)
        } else {
            startSettings("Disable Notification Listener")
            updateState(Tile.STATE_INACTIVE)
        }
    }

    private fun updateState(newState: Int) {
        val iconId = when (newState) {
            Tile.STATE_ACTIVE -> R.drawable.ic_otto
            else -> R.drawable.ic_otto_disabled
        }
        qsTile.icon = Icon.createWithResource(this, iconId)
        qsTile.state = newState
        qsTile.updateTile()
    }

    private fun startSettings(message: String) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

}
