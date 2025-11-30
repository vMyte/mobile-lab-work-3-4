package com.example.firstlab.data

object DataRepository {
    private val wordLists = mutableListOf<WordList>()
    private val words = mutableListOf<Word>()

    private var listIdCounter = 1
    private var wordIdCounter = 1

    fun getLists(): List<WordList> = wordLists.toList()

    fun getListById(id: Int): WordList? = wordLists.find { it.id == id }

    fun createList(name: String): WordList {
        val list = WordList(id = listIdCounter++, name = name)
        wordLists.add(list)
        return list
    }

    fun getWords(listId: Int): List<Word> = words.filter { it.listId == listId }

    fun createWord(listId: Int, englishWord: String, russianTranslation: String): Word {
        val word = Word(
            id = wordIdCounter++,
            listId = listId,
            englishWord = englishWord,
            russianTranslation = russianTranslation
        )
        words.add(word)
        return word
    }

    fun deleteList(listId: Int) {
        wordLists.removeAll { it.id == listId }
        words.removeAll { it.listId == listId }
    }

    fun deleteWord(wordId: Int) {
        words.removeAll { it.id == wordId }
    }
}
