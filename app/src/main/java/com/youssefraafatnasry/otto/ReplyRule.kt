package com.youssefraafatnasry.otto

class ReplyRule(pattern: String, reply: String, val exclude: String = "") {

    val regex = Regex(pattern, RegexOption.IGNORE_CASE)
    val fullReply = Config.PREFIX + reply + Config.POSTFIX

    companion object {
        val TEXT = "{{ TEXT }}"
        val NAME = "{{ NAME }}"
    }

}