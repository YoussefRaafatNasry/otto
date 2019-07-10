package com.youssefraafatnasry.otto

class Config {

    companion object {

        var IS_ACTIVE = false

        var PREFIX = "*⦿:* "

        var PACKAGES = arrayOf(
            "com.facebook.orca",
            "com.instagram.android",
            "com.twitter.android",
            "com.whatsapp"
        )

        var RULES = arrayOf(
            ReplyRule(
                "hi",
                "Hi!"
            ),
            ReplyRule(
                """who (are|r) (you|u)\?""",
                "I'm *Otto*. Who are you?"
            ),
            ReplyRule(
                "I am (.*)",
                "Nice to meet you!"
            ),
            ReplyRule(
                """where is Youssef\?""",
                "He is busy teaching me new stuff."
            ),
            ReplyRule(
                ".*",
                "Hello! I'm *Otto*! Youssef created me. " +
                        "I don't know how to talk much yet but I am learning."
            )
        )

    }

}
