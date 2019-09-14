package com.youssefraafatnasry.otto

import okhttp3.*
import java.io.IOException

class SpotifyAPI {

    companion object {

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
                .addHeader("Authorization", "Bearer ${Config.SPOTIFY_TOKEN}")
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
}