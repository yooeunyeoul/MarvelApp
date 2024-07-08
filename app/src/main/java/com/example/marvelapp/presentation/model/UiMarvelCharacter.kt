package com.example.marvelapp.presentation.model

data class UiMarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val isFavorite: Boolean,
    val loading: Boolean = false,
    val error: String? = null
)