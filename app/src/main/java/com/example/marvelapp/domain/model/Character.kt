package com.example.marvelapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val isFavorite: Boolean
)