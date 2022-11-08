package dev.yrn.otto.models

import dev.yrn.otto.Config

data class ReplyRule(
    val pattern: String,
    val replies: ArrayList<String>,
    val command: Command? = null
) {
    val regex = Regex(pattern, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))

    fun processReply(text: String, templates: HashMap<String, String?>): String {
        var reply = replies.random()
        val match = regex.find(text)!!
        val groups = match.destructured.toList()

        val groupsTemplates = groups.withIndex().associate { Template.group(it.index) to it.value }
        val mergedTemplates = templates + groupsTemplates

        mergedTemplates.forEach { (k, v) ->
            reply = reply.replace(k, v.toString(), true)
        }

        return Config.PREFIX + reply + Config.POSTFIX
    }
}