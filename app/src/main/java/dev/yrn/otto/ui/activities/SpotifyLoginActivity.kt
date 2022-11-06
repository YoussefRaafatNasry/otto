package dev.yrn.otto.ui.activities

import android.util.Log
import com.adamratzman.spotify.SpotifyImplicitGrantApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.implicit.AbstractSpotifyAppImplicitLoginActivity
import dev.yrn.otto.BuildConfig
import dev.yrn.otto.OttoApp

class SpotifyLoginActivity : AbstractSpotifyAppImplicitLoginActivity() {
    override val state = 1337
    override val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    override val redirectUri = BuildConfig.SPOTIFY_REDIRECT_URI
    override val useDefaultRedirectHandler = false
    override fun getRequestingScopes() = listOf(SpotifyScope.USER_LIBRARY_MODIFY)

    override fun onSuccess(spotifyApi: SpotifyImplicitGrantApi) {
        Log.d(SpotifyLoginActivity::class.simpleName, "Logged In: ${spotifyApi.token}")
        val credentialStore = (application as OttoApp).store
        credentialStore.setSpotifyApi(spotifyApi)
        finish()
    }

    override fun onFailure(errorMessage: String) {
        Log.e(SpotifyLoginActivity::class.simpleName, errorMessage)
    }
}
