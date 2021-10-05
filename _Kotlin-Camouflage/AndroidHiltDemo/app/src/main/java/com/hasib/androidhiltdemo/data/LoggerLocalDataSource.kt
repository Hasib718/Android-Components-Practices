package com.hasib.androidhiltdemo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) {

    fun addLog(msg: String) {
        GlobalScope.launch(Dispatchers.IO) {
            logDao.insertAll(
                Log(
                    msg, System.currentTimeMillis()
                )
            )
        }
    }

    fun getAllLogs(callback: (List<Log>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val logs = logDao.getAll()
            launch(Dispatchers.Default) {
                callback(logs)
            }
        }
    }

    fun removeLogs() {
        GlobalScope.launch(Dispatchers.IO) {
            logDao.nukeTable()
        }
    }
}