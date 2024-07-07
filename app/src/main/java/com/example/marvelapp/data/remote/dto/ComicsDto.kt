package com.example.marvelapp.data.remote.dto

data class ComicsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<ComicItemDto>,
    val returned: Int
)