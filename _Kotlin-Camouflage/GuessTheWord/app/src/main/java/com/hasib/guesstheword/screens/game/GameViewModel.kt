package com.hasib.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    // The current word
    private val _word = MutableLiveData("")
    val word: LiveData<String>
        get() = _word

    // Current word hint
    val wordHint: LiveData<String> = Transformations.map(_word) {
        val randomPosition = (1..it.length).random()
        "Current word has ${it.length} letters\n The letter at position $randomPosition is ${it[randomPosition - 1].uppercaseChar()}"
    }

    // The current score
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData(false)
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()

    // The String version of the current time
    val currentTimeString: LiveData<String> = Transformations.map(_currentTime) {
        DateUtils.formatElapsedTime(it)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val timer: CountDownTimer

    /**
     * Resets the list of words and randomizes the order
     */
    fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    init {
        Timber.i("GameViewModel created!")

        resetList()
        nextWord()

        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }
        }

        timer.start()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        } else {
            //Select and remove a _word from the list
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("GameViewModel Destroyed!")
        timer.cancel()
    }

    /** Method for the game completed event **/
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    /** Method for the game completed event **/
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 30000L

    }
}