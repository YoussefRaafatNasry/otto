package com.youssefraafatnasry.otto

class Command(private val c: String) {

    companion object {
        const val ADD_TRACK_TO_FAV = "{{ ADD_TRACK_TO_FAV }}"
    }

    fun invoke(inputs: HashMap<String, String>) {
        when (c) {
            ADD_TRACK_TO_FAV -> {
                val trackId =
                    SpotifyAPI.getTrackId(inputs[Template.TEXT].toString())
                SpotifyAPI.likeTrack(trackId)
            }
        }
    }

}