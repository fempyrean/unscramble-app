package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private val _currentScrambledWord = MutableLiveData<String>()
    private val _wordList = mutableListOf<String>()
    private lateinit var currentWord: String

    public val score: Int get() = _score
    public val currentWordCount: Int get() = _currentWordCount
    public val currentScrambledWord: LiveData<String> get() = _currentScrambledWord

    init {
        getNextWord()
    }

    /*
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (_wordList.contains(currentWord)) {
            getNextWord()
        } else {
            _wordList.add(currentWord)
            _currentScrambledWord.value = String(tempWord)
            ++_currentWordCount
        }
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        _wordList.clear()
        getNextWord()
    }
}