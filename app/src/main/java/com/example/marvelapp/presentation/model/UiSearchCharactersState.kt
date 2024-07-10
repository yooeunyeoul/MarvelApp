package com.example.marvelapp.presentation.model

data class UiSearchCharactersState(
    val total: Int = 0,
    val characters: List<UiMarvelCharacter> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)