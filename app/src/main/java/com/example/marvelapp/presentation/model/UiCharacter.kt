package com.example.marvelapp.presentation.model

data class UiCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: UiThumbnail,
    val isFavorite: Boolean,
    val loading: Boolean = false,
    val error: String? = null
)