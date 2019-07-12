package com.youssefraafatnasry.otto

class ReplyRule(
    pattern: String,
    private val reply: String,
    private val exclude: String = "",
    private val command: Command = Command.NONE
) {

    val regex = Regex(pattern, RegexOption.IGNORE_CASE)

    fun processReply(inputs: HashMap<String, String>): String {
        command.invokeWith(inputs)
        inputs[exclude] = ""
        return Config.PREFIX + Template.replace(reply, inputs) + Config.POSTFIX
    }

}