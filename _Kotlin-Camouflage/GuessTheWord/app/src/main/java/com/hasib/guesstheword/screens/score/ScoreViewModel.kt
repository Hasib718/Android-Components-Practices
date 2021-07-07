package com.hasib.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score
    private val _score = MutableLiveData(finalScore)
    val score: LiveData<Int>
        get() = _score

    private val _eventPlayAgain = MutableLiveData(false)
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    init {
        Timber.i("Final score is $finalScore")
    }
}