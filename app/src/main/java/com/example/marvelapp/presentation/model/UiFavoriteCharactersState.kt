package com.example.marvelapp.presentation.model

data class UiFavoriteCharactersState(
    val characters: List<UiMarvelCharacter> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)