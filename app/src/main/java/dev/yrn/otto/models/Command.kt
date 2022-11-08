package dev.yrn.otto.models

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import com.adamratzman.spotify.endpoints.client.LibraryType
import dev.yrn.otto.OttoApp
import kotlinx.coroutines.*

enum class Command {
    ADD_TRACK_TO_FAV {
        override fun execute(context: Context, text: String) {
            // Support Spotify URIs (...track:id)
            // and HTTP Links (...track/id)
            val regex = Regex("track[:/].{22}")
            val match = regex.find(text)!!
            val trackId = match.value.substring(6)

            runBlocking {
                val app = (context.applicationContext as OttoApp)
                val api = app.store.getSpotifyImplicitGrantApi()
                launch (Dispatchers.IO) {
                    api?.library?.add(LibraryType.TRACK, trackId)
                }
            }
        }
    },

    NOTIFY_OWNER {
        override fun execute(context: Context, text: String) {
            ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                .startTone(ToneGenerator.TONE_CDMA_PIP, 1000)
        }
    };

    abstract fun execute(context: Context, text: String)
}
