package com.example.chat

data class UiFavoriteCharactersState(
    val characters: List<UiMarvelCharacter> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)