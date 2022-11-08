package dev.yrn.otto.models

object Template {
    /// Name from notification title
    fun name() = "{{ NAME }}"

    /// Text from notification body
    fun text() = "{{ TEXT }}"

    /// Group from pattern match
    fun group(index: Int) = "{{ $index }}"
}