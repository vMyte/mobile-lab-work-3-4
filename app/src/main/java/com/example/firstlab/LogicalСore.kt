package com.example.firstlab

class TextProcessor {
    fun processText(sourceText: String, lengthToRemove: Int): String {
        val result = sourceText
            .split(Regex("[\\s.,!?-]+"))
            .filter { it.isNotEmpty() }
            .filter { it.length != lengthToRemove }
            .distinct()
            .sorted()
            .joinToString(" ")

        return if (result.isEmpty()) {
            "Подходящих слов не найдено."
        } else {
            result
        }
    }
}
