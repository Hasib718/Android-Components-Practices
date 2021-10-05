package com.hasib.androidhiltdemo

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.hasib.androidhiltdemo.data.AppDatabase
import com.hasib.androidhiltdemo.data.LoggerLocalDataSource
import com.hasib.androidhiltdemo.navigator.AppNavigator
import com.hasib.androidhiltdemo.navigator.AppNavigatorImpl
import com.hasib.androidhiltdemo.util.DateFormatter

class ServiceLocator(applicationContext: Context) {

    private val logsDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "logging.db"
    ).build()

    val loggerLocalDataSource = LoggerLocalDataSource(logsDatabase.logDao())

    fun provideDataFormatter() = DateFormatter()

    fun provideNavigator(activity: FragmentActivity): AppNavigator {
        return AppNavigatorImpl(activity)
    }
}