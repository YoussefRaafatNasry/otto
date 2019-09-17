package com.youssefraafatnasry.otto.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.youssefraafatnasry.otto.BuildConfig
import com.youssefraafatnasry.otto.util.Config
import com.youssefraafatnasry.otto.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val SPOTIFY_CLIENT_ID = "d4fcf53185d04097be9df720025c57c4"
    private val SPOTIFY_AUTH_REQUEST_CODE = 0x10

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grantNotificationAccess()
        authenticateSpotify()

        main_linear_layout.setOnLongClickListener {
            val intent = Intent(this@MainActivity, DebuggerActivity::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

    }

    private fun grantNotificationAccess() {
        val isListenerAllowed = NotificationManagerCompat
            .getEnabledListenerPackages(this)
            .contains(BuildConfig.APPLICATION_ID)
        if (!isListenerAllowed) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }

    private fun authenticateSpotify() {
        val request = AuthenticationRequest.Builder(
            SPOTIFY_CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            BuildConfig.WEBSITE
        ).setScopes(arrayOf("user-library-modify")).build()
        AuthenticationClient.openLoginActivity(this, SPOTIFY_AUTH_REQUEST_CODE, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == SPOTIFY_AUTH_REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            Config.SPOTIFY_TOKEN = response?.accessToken
        }
    }

}
