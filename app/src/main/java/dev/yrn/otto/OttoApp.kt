package dev.yrn.otto

import android.app.Application
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore

class OttoApp : Application() {
    lateinit var store: SpotifyDefaultCredentialStore

    override fun onCreate() {
        super.onCreate()

        val credentialStore by lazy {
            SpotifyDefaultCredentialStore(
                clientId = BuildConfig.SPOTIFY_CLIENT_ID,
                redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI,
                applicationContext = applicationContext
            )
        }

        store = credentialStore
    }
}