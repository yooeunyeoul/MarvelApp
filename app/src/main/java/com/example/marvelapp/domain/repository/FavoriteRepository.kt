package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.MarvelCharacter

interface FavoriteRepository {
    suspend fun getFavoriteCharacterIds(): List<Int>
    suspend fun getFavoriteCharacters(): List<MarvelCharacter>
    suspend fun saveFavoriteCharacter(character: MarvelCharacter)
    suspend fun deleteFavoriteCharacter(character: MarvelCharacter)
}