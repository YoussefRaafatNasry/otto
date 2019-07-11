package com.youssefraafatnasry.otto

import okhttp3.*
import java.io.IOException

class SpotifyAPI {

    companion object {

        private val mOkHttpClient = OkHttpClient()
        private var mCall: Call? = null

        fun getTrackId(text: String): String {

            // Support Spotify URIs (...track:id)
            // and track http links (...track/id)
            val trackKeyword = "track"
            val startIndex = text.indexOf(trackKeyword) + trackKeyword.length + 1
            val endIndex = text.indexOfAny(" ?".toCharArray(), startIndex)

            return if (endIndex == -1) {
                text.substring(startIndex)
            } else {
                text.substring(startIndex, endIndex)
            }

        }

        fun likeTrack(id: String) {

            val request = Request.Builder()
                .url("https://api.spotify.com/v1/me/tracks?ids=$id")
                .addHeader("Authorization", "Bearer ${Config.SPOTIFY_TOKEN}")
                .method("PUT", RequestBody.create(null, byteArrayOf()))
                .build()

            mCall = mOkHttpClient.newCall(request)

            mCall?.enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {}
                override fun onFailure(call: Call, e: IOException) {}
            })

        }

    }
}