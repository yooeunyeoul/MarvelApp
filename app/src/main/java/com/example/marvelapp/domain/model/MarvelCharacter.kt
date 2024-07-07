package com.example.marvelapp.domain.model

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val isFavorite: Boolean
)