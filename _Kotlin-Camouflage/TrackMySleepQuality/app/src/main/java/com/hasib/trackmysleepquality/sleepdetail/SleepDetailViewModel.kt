package com.hasib.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasib.trackmysleepquality.database.SleepDatabaseDao
import com.hasib.trackmysleepquality.database.SleepNight

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class SleepDetailViewModel(
    private val sleepNightKey: Long = 0L,
    dataSource: SleepDatabaseDao
) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource

    val night: LiveData<SleepNight> = database.getNightWithId(sleepNightKey)


    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSleepTracker = MutableLiveData(false)

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean>
        get() = _navigateToSleepTracker


    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = false
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

}