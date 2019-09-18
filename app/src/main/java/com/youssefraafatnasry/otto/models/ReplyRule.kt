package com.youssefraafatnasry.otto.models

import com.youssefraafatnasry.otto.util.Config

data class ReplyRule(
    val pattern: String,
    val replies: ArrayList<String>,
    val options: Template.Options? = null,
    val command: Command? = null
    ) {

    val regex = Regex(pattern, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))

    fun processReply(inputs: HashMap<String, String?>): String {
        inputs[Template.CMD_RESULT] = command?.execute(inputs)
        val reply = Template.replace(replies.random(), inputs, options)
        return Config.PREFIX + reply + Config.POSTFIX
    }

}