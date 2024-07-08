package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoriteCharacterIds(): List<Int>
    suspend fun getFavoriteCharacters(): Flow<List<MarvelCharacter>>
    suspend fun saveFavoriteCharacter(character: MarvelCharacter)
    suspend fun deleteFavoriteCharacter(character: MarvelCharacter)
}