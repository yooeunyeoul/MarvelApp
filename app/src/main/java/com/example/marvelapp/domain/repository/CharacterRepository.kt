package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.MarvelCharacterList
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(nameStartsWith: String, offset: Int, limit: Int): Flow<MarvelCharacterList>
}