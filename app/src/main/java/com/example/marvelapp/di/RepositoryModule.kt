package com.example.marvelapp.di

import com.example.marvelapp.data.local.dao.FavoriteDao
import com.example.marvelapp.data.remote.api.MarvelApiService
import com.example.marvelapp.data.repositoryimpl.CharacterRepositoryImpl
import com.example.marvelapp.data.repositoryimpl.FavoriteRepositoryImpl
import com.example.marvelapp.domain.repository.CharacterRepository
import com.example.marvelapp.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        apiService: MarvelApiService,
        @Named("apiKey") apiKey: String,
        @Named("privateKey") privateKey: String,
    ): CharacterRepository {
        return CharacterRepositoryImpl(apiService, apiKey, privateKey)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao)
    }
}