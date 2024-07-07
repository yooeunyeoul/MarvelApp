package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.MarvelCharacter

interface CharacterRepository {
    suspend fun getCharacters(nameStartsWith: String, offset: Int, limit: Int): List<MarvelCharacter>
}