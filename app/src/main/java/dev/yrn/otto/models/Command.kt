package dev.yrn.otto.models

import android.media.AudioManager
import android.media.ToneGenerator
import dev.yrn.otto.service.SpotifyService

enum class Command {
    ADD_TRACK_TO_FAV {
        override fun execute(inputs: HashMap<String, String?>): String? {
            val text = inputs[Template.TEXT].toString()
            val trackId = SpotifyService.getTrackId(text)
            SpotifyService.likeTrack(trackId)
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
