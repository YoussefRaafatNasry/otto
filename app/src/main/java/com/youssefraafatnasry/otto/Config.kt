package com.youssefraafatnasry.otto

class Config {

    companion object {

        var IS_ACTIVE = false
        var SPOTIFY_TOKEN: String? = null
        var OWNER = "Youssef"

        var PACKAGES = arrayOf(
            "com.facebook.orca",
            "com.instagram.android",
            "com.twitter.android",
            "com.whatsapp"
        )

        var PREFIX = "*⦿:* "
        var POSTFIX = ""

        var RULES = arrayOf(
            ReplyRule(
                "(hi|hey|hello)(.*)",
                "Hi, ${Template.NAME}! I am *Otto*. " +
                        "$OWNER created me to talk to people when he's away."
            ),
            ReplyRule(
                "where('s| is) $OWNER( ?)\\?",
                "He is busy teaching me new stuff."
            ),
            ReplyRule(
                "who (r|are) (u|you)( ?)\\?",
                "I'm *Otto*. A piece of code on $OWNER's phone. Who are you?"
            ),
            ReplyRule(
                "^I am (\\w+){1}\$",
                "Nice to meet you, ${Template.TEXT}!",
                "I am "
            ),
            ReplyRule(
                "say .*",
                "${Template.TEXT}",
                "say "
            ),
            ReplyRule(
                "otto",
                "Yes?"
            ),
            ReplyRule(
                "(.*)otto(.*)",
                "I don't know how to talk much yet but I am learning."
            ),
            ReplyRule(
                "Here’s a song for you|.*spotify.+track.+",
                "I will add this to $OWNER's favourites. " +
                        "I am sure he will like it!",
                command = Command(Command.ADD_TRACK_TO_FAV)
            )
        )

    }

}
