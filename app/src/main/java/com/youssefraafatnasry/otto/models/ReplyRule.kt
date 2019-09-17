package com.youssefraafatnasry.otto.models

import com.youssefraafatnasry.otto.util.Config

class ReplyRule(
    pattern: String,
    private val reply: String,
    private val options: Template.Options? = null,
    private val command: Command? = null
) {

    val regex = Regex(pattern, setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))

    fun processReply(inputs: HashMap<String, String?>): String {
        inputs[Template.CMD_RESULT] = command?.execute(inputs)
        return Config.PREFIX + Template.replace(
            reply,
            inputs,
            options
        ) + Config.POSTFIX
    }

}