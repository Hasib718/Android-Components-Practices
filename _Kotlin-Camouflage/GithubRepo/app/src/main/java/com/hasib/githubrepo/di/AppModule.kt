package com.hasib.githubrepo.di

import androidx.lifecycle.SavedStateHandle
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideSavedState(state: SavedStateRegistryOwner) = SavedStateHandle
}