package com.youssefraafatnasry.otto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Allow Notification Listener
        val isListenerAllowed = NotificationManagerCompat
            .getEnabledListenerPackages(this)
            .contains(BuildConfig.APPLICATION_ID)

        if (!isListenerAllowed) {
            startActivity(Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        // Application active status
        Config.IS_ACTIVE = switch_activate.isChecked
        switch_activate.setOnCheckedChangeListener { _, b -> Config.IS_ACTIVE = b }

        // Spotify Authentication
        val request = AuthenticationRequest.Builder(
            BuildConfig.SPOTIFY_CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            BuildConfig.WEBSITE
        ).setScopes(arrayOf("user-library-modify")).build()
        AuthenticationClient.openLoginActivity(this, BuildConfig.SPOTIFY_AUTH_REQUEST_CODE, request)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == BuildConfig.SPOTIFY_AUTH_REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                // Response was successful and contains auth token
                Config.SPOTIFY_TOKEN = response.accessToken
            }
        }

    }
}
