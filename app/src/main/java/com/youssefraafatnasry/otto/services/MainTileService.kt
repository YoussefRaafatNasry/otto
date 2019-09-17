package com.youssefraafatnasry.otto.services

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.youssefraafatnasry.otto.util.Config

class MainTileService : TileService() {

    override fun onClick() {
        super.onClick()
        if (qsTile.state == Tile.STATE_INACTIVE) {
            updateState(Tile.STATE_ACTIVE)
            startService(Intent(this, AutoReplyService::class.java))
        } else {
            updateState(Tile.STATE_INACTIVE)
            stopService(Intent(this, AutoReplyService::class.java))
        }
    }

    private fun updateState(newState: Int) {
        qsTile.state = newState
        qsTile.updateTile()
    }

}
