package com.hasib.githubrepo.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.hasib.githubrepo.db.RemoteKeysDao
import com.hasib.githubrepo.db.RepoDao
import com.hasib.githubrepo.db.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase {
        return Room.databaseBuilder(context, RepoDatabase::class.java, "Github.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}