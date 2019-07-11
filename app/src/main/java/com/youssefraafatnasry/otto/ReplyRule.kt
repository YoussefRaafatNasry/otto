package com.youssefraafatnasry.otto

class ReplyRule(
    pattern: String,
    private var reply: String,
    private val exclude: String = ""
) {

    val regex = Regex(pattern, RegexOption.IGNORE_CASE)
    lateinit var inputs: HashMap<String, String?>

    fun processReply(): String {

        // Remove excluded text from TEXT input
        inputs[TEXT] = inputs[TEXT]?.replace(exclude, "", true)

        // Replace inputs with their values
        inputs.forEach { (k, v) ->
            reply = reply.replace(k, v.toString())
        }

        // Actions
        when {
            reply.contains(ADD_TRACK_TO_FAV) -> {
                reply = reply.replace(ADD_TRACK_TO_FAV, "")
                SpotifyAPI.likeTrack(SpotifyAPI.getTrackId(inputs[TEXT].toString()))
            }

            // Other actions go here...
        }

        return Config.PREFIX + reply + Config.POSTFIX
    }

    companion object {
        const val TEXT = "{{ TEXT }}"
        const val NAME = "{{ NAME }}"
        const val ADD_TRACK_TO_FAV = "{{ ADD_TRACK_TO_FAV }}"
    }

}