package com.youssefraafatnasry.otto

class Template {

    companion object {

        const val TEXT = "{{ TEXT }}"
        const val NAME = "{{ NAME }}"

        fun replace(reply: String, inputs: HashMap<String, String>): String {
            var copy = reply
            inputs.forEach { (k, v) ->
                copy = copy.replace(k, v, true)
            }
            return copy
        }

    }

}