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
                "Here’s a song for you|.*spotify.+track.+",
                "I will add this to $OWNER's favourites. " +
                        "I am sure he will like it!",
                command = Command.ADD_TRACK_TO_FAV
            ),
            ReplyRule(
                "otto@help",
                "I cannot do much yet, but here are some stuff I can do for now: ${BuildConfig.WEBSITE}"
            ),
            ReplyRule(
                "otto@beep",
                "This gonna be annoying but I will do it, " +
                        "I hope $OWNER doesn't shut me down.",
                command = Command.NOTIFY_OWNER
            ),
            ReplyRule(
                "(.*)otto(.*)",
                "I don't know how to talk much yet but I am learning."
            )
        )

    }

}
