package com.hasib.trackmysleepquality.sleeptracker

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.*
import com.hasib.trackmysleepquality.database.SleepDatabaseDao
import com.hasib.trackmysleepquality.database.SleepNight
import com.hasib.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var tonight = MutableLiveData<SleepNight?>()

    private val nights = database.getAllNights()
    val nightsString: LiveData<Spanned> = Transformations.map(nights) {
        formatNights(it, application.resources)
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    val startButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it == null
    }
    val stopButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it != null
    }
    val clearButtonVisible: LiveData<Boolean?> = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData(false)
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)

            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        database.insert(night)
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)

            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night: SleepNight) {
        database.update(night)
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null

            _showSnackbarEvent.value = true
        }
    }

    private suspend fun clear() {
        database.clear()
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
}

