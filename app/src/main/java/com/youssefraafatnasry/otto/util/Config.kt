package com.youssefraafatnasry.otto.util

import com.youssefraafatnasry.otto.BuildConfig
import com.youssefraafatnasry.otto.models.Command
import com.youssefraafatnasry.otto.models.ReplyRule
import com.youssefraafatnasry.otto.models.Template

object Config {

    var OWNER   = "Youssef"
    var PREFIX  = "*⦿:* "
    var POSTFIX = ""

    var PACKAGES = arrayOf(
        BuildConfig.APPLICATION_ID, // For debugging mode
        "com.facebook.orca",
        "com.instagram.android",
        "com.twitter.android",
        "com.whatsapp"
    )

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
            Template.Options("I am ")
        ),
        ReplyRule(
            "say .*",
            "${Template.TEXT}",
            Template.Options("say ")
        ),
        ReplyRule(
            "otto",
            "Yes?"
        ),
        ReplyRule(
            ".*spotify.+track.+",
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
