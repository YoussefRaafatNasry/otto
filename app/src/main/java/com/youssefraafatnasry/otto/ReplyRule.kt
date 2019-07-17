package com.youssefraafatnasry.otto

class ReplyRule(
    pattern: String,
    private val reply: String,
    private val options: Template.Options? = null,
    private val command: Command? = null
) {

    val regex = Regex(pattern, RegexOption.IGNORE_CASE)

    fun processReply(inputs: HashMap<String, String?>): String {
        inputs[Template.CMD_RESULT] = command?.execute(inputs)
        return Config.PREFIX + Template.replace(reply, inputs, options) + Config.POSTFIX
    }

}