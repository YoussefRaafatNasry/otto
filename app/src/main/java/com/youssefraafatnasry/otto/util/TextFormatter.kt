package com.youssefraafatnasry.otto.util

import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat

object TextFormatter {

    fun TextView.setTextFormatted(text: String) {
        this.text = formatText(text)
    }

    fun TextView.appendTextFormatted(text: String) {
        this.append(formatText(text))
    }

    private fun formatText(text: String): Spanned {
        val boldMatches = getStyleMatches(text, "*", "b")
        val italicMatches = getStyleMatches(text, "_", "i")
        val stylesMatches = boldMatches + italicMatches + ("\n" to "<br>")
        val htmlText = stylesMatches.entries.fold(text) { acc, (k, v) -> acc.replace(k, v) }
        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun getStyleMatches(
        text: String,
        delimiter: String,
        htmlTag: String
    ): Map<String, String> {
        val regex = Regex("\\$delimiter.+?\\$delimiter")
        val matches = regex.findAll(text).map { it.value }
        return matches.associateWith { "<$htmlTag>${it.substring(1, it.lastIndex)}</$htmlTag>" }
    }

}