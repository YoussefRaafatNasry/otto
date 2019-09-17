package com.youssefraafatnasry.otto.services

import android.content.Intent
import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages
import com.youssefraafatnasry.otto.BuildConfig
import com.youssefraafatnasry.otto.R
import com.youssefraafatnasry.otto.util.CustomToast

class MainTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        if (isListenerEnabled()) {
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
        startActivityAndCollapse(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        CustomToast.showToast(
            this,
            message,
            "otto needs the following modifications to complete the requested action."
        )
    }

    private fun isListenerEnabled(): Boolean {
        return getEnabledListenerPackages(this).contains(BuildConfig.APPLICATION_ID)
    }

}
