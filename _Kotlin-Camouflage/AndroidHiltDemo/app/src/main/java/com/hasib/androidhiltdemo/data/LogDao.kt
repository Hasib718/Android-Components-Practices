package com.hasib.androidhiltdemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data access object to query the database.
 */
@Dao
interface LogDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    suspend fun getAll(): List<Log>

    @Insert
    suspend fun insertAll(vararg logs: Log)

    @Query("DELETE FROM logs")
    suspend fun nukeTable()
}