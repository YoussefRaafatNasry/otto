package com.youssefraafatnasry.otto.models

import android.media.AudioManager
import android.media.ToneGenerator
import com.youssefraafatnasry.otto.util.SpotifyAPI

enum class Command {

    ADD_TRACK_TO_FAV {
        override fun execute(inputs: HashMap<String, String?>): String? {
            val trackId =
                SpotifyAPI.getTrackId(inputs[Template.TEXT].toString())
            SpotifyAPI.likeTrack(trackId)
            return null
        }
    },

    NOTIFY_OWNER {
        override fun execute(inputs: HashMap<String, String?>): String? {
            ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                .startTone(ToneGenerator.TONE_CDMA_PIP, 1000)
            return null
        }
    };

    abstract fun execute(inputs: HashMap<String, String?>): String?

}
