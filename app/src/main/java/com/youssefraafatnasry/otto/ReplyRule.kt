package com.youssefraafatnasry.otto

class ReplyRule(pattern: String, reply: String) {

    val regex = Regex(pattern, RegexOption.IGNORE_CASE)
    val fullReply = Config.PREFIX + reply

}