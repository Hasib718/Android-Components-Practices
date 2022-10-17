package com.hasib.githubrepo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasib.githubrepo.model.Repo

@Database(
    entities = [Repo::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun reposDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}