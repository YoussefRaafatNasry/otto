package com.youssefraafatnasry.otto

import android.media.AudioManager
import android.media.ToneGenerator

enum class Command {

    ADD_TRACK_TO_FAV {
        override fun execute(inputs: HashMap<String, String>) {
            val trackId = SpotifyAPI.getTrackId(inputs[Template.TEXT].toString())
            SpotifyAPI.likeTrack(trackId)
        }
    },

    NOTIFY_OWNER {
        override fun execute(inputs: HashMap<String, String>) {
            ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                .startTone(ToneGenerator.TONE_CDMA_PIP, 1000)
        }
    };

    abstract fun execute(inputs: HashMap<String, String>)

}