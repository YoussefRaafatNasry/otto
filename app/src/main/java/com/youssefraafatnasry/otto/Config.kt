package com.youssefraafatnasry.otto

class Config {

    companion object {

        var IS_ACTIVE = false

        var PREFIX = "*⦿:* "
        var POSTFIX = ""

        var PACKAGES = arrayOf(
            "com.facebook.orca",
            "com.instagram.android",
            "com.twitter.android",
            "com.whatsapp"
        )

        var RULES = arrayOf(
            ReplyRule(
                "hi|hey|hello",
                "Hi, ${ReplyRule.NAME}! I am *Otto*. Youssef created me to talk to people when he's away."
            ),
            ReplyRule(
                "where('s| is) youssef\\?",
                "He is busy teaching me new stuff."
            ),
            ReplyRule(
                "who (r|are) (u|you)\\?",
                "I'm *Otto*. Who are you?"
            ),
            ReplyRule(
                "I am .*",
                "Nice to meet you, ${ReplyRule.TEXT}!",
                "I am "
            ),
            ReplyRule(
                "say .*",
                "${ReplyRule.TEXT}",
                "say "
            ),
            ReplyRule(
                "otto ",
                "I don't know how to talk much yet but I am learning."
            ),
            ReplyRule(
                "otto",
                "Yes?"
            )
        )

    }

}
