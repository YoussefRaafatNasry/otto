package com.youssefraafatnasry.otto.util

import android.app.Activity
import android.content.Intent
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.youssefraafatnasry.otto.BuildConfig
import okhttp3.*
import java.io.IOException

object SpotifyAPI {

    lateinit var ACCESS_TOKEN: String
    private const val AUTH_REQUEST_CODE = 0x10
    private const val CLIENT_ID = "d4fcf53185d04097be9df720025c57c4"

    fun authenticateSpotify(context: Activity) {
        val request = AuthenticationRequest.Builder(
            CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            BuildConfig.WEBSITE
        ).setScopes(arrayOf("user-library-modify")).build()
        AuthenticationClient.openLoginActivity(context, AUTH_REQUEST_CODE, request)
    }

    fun initAccessToken(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            ACCESS_TOKEN = response?.accessToken.toString()
        }
    }

    fun getTrackId(text: String): String {
        // Support Spotify URIs (...track:id)
        // and HTTP Links (...track/id)
        val regex = Regex("track[:/].{22}")
        val match = regex.find(text)!!
        return match.value.substring(6)
    }

    fun likeTrack(id: String) {

        val request = Request.Builder()
            .url("https://api.spotify.com/v1/me/tracks?ids=$id")
            .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
            .method("PUT", RequestBody.create(null, byteArrayOf()))
            .build()

        val client = OkHttpClient()
        val call = client.newCall(request)
        call?.enqueue(EMPTY_CALLBACK)

    }

    private val EMPTY_CALLBACK = object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {}
    }

}