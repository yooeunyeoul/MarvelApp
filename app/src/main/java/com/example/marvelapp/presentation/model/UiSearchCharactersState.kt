package com.example.marvelapp.presentation.model

data class UiSearchCharactersState(
    val searchQuery: String = "",
    val total: Int = 0,
    val characters: List<UiMarvelCharacter> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val currentOffset: Int = 0
)