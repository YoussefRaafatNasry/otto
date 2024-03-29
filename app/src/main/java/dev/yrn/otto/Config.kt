package dev.yrn.otto

import dev.yrn.otto.models.Command
import dev.yrn.otto.models.ReplyRule
import dev.yrn.otto.models.Template


object Config {
    const val CREATOR = "Youssef"
    const val PREFIX = "⦿: "
    const val POSTFIX = ""

    var PACKAGES = arrayOf(
        BuildConfig.APPLICATION_ID, // For debugging mode
        "com.facebook.orca",
        "com.instagram.android",
        "com.Slack",
        "com.twitter.android",
        "com.whatsapp"
    )

    var RULES = arrayOf(
        ReplyRule(
            "(hi|hello|he+y).*",
            arrayListOf(
                "Hi, ${Template.name()}! I am *Otto*.",
                "Hi! Long time, no see!",
                "Hello!",
                "Hey there!"
            ),
        ),
        ReplyRule(
            "how (are|r) (you|u).*",
            arrayListOf(
                "I am feeling a 'bit' down, but I hope $CREATOR is doing fine!",
                "Feeling 'buggy'. You?"
            ),
        ),
        ReplyRule(
            "where('s| is) $CREATOR.*",
            arrayListOf(
                "He is busy teaching me new stuff.",
                "From all the things $CREATOR taught me, he didn't teach me how to see :'("
            ),
        ),
        ReplyRule(
            "who (are|r) (you|u).*",
            arrayListOf(
                "I'm *Otto*. A piece of code on $CREATOR's phone. Who are you?",
                "I am just zeros and ones. What about you?"
            ),
        ),
        ReplyRule(
            "^I am ((\\w+){1})\$",
            arrayListOf(
                "Nice to meet you, ${Template.group(0)}!",
                "Nice name you got!",
                "${Template.group(0)}, I will remember that."
            ),
        ),
        ReplyRule(
            "say (.*)",
            arrayListOf(
                "${Template.group(0)}.",
                "Maybe later, I am a bit busy now.",
                "${Template.group(0)}. Hope it is not a bad word."
            )
        ),
        ReplyRule(
            ".*spotify.+track.+",
            arrayListOf(
                "I will add this to $CREATOR's favourites. I am sure he will like it!",
                "I wish I could have ears to listen to it! I will add it to $CREATOR's tracks instead."
            ),
            Command.ADD_TRACK_TO_FAV
        ),
        ReplyRule(
            "otto@beep",
            arrayListOf(
                "This gonna be annoying but I will do it, I hope $CREATOR doesn't shut me down.",
                "I am beeping as loud as I could now! I know $CREATOR will kill me for that :'("
            ),
            Command.NOTIFY_OWNER
        ),
        ReplyRule(
            "otto@help",
            arrayListOf(
                "I cannot do much yet, but here are some stuff I can do for now: ${BuildConfig.WEBSITE}",
                "Take a look: ${BuildConfig.WEBSITE}"
            )
        ),
        ReplyRule(
            "otto",
            arrayListOf(
                "Yes?",
                "Who's there?!",
                "What?",
                "At your service!"
            )
        ),
        ReplyRule(
            ".*otto.*",
            arrayListOf(
                "I don't know how to talk much yet but I am learning.",
                "I will ask $CREATOR to teach me to reply to that."
            )
        )
    )
}
