package com.example.marvelapp.domain.model

data class MarvelCharacterList(
    val total: Int,
    val characters: List<MarvelCharacter>
)