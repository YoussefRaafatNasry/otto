package com.youssefraafatnasry.otto

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class MainTileService : TileService() {

    override fun onClick() {
        super.onClick()
        if (qsTile.state == Tile.STATE_INACTIVE) {
            updateState(Tile.STATE_ACTIVE)
        } else {
            updateState(Tile.STATE_INACTIVE)
        }
    }

    private fun updateState(newState: Int) {
        Config.IS_ACTIVE = newState == Tile.STATE_ACTIVE
        qsTile.state = newState
        qsTile.updateTile()
    }

}
