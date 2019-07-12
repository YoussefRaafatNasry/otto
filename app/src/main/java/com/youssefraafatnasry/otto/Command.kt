package com.youssefraafatnasry.otto

import android.media.AudioManager
import android.media.ToneGenerator

enum class Command {

    NONE,
    ADD_TRACK_TO_FAV,
    NOTIFY_OWNER;

    fun invokeWith(inputs: HashMap<String, String>) {

        when (this) {

            NONE -> { return }

            ADD_TRACK_TO_FAV -> {
                val trackId =
                    SpotifyAPI.getTrackId(inputs[Template.TEXT].toString())
                SpotifyAPI.likeTrack(trackId)
            }

            NOTIFY_OWNER -> {
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(ToneGenerator.TONE_CDMA_PIP, 1000)
            }

        }

    }

}
