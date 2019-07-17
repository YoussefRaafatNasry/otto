package com.youssefraafatnasry.otto

class Template {

    class Options(val exclude: String = "")

    companion object {

        const val TEXT = "{{ TEXT }}"
        const val NAME = "{{ NAME }}"
        const val CMD_RESULT = "{{ CMD_RESULT }}"

        fun replace(reply: String, inputs: HashMap<String, String?>, options: Options?): String {

            var copy = reply

            inputs.forEach { (k, v) ->
                copy = copy.replace(k, v.toString(), true)
            }

            if (options != null) {
                copy =  copy.replace(options.exclude, "", true)
            }

            return copy
        }

    }

}