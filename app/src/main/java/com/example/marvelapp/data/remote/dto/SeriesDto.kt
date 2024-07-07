package com.example.marvelapp.data.remote.dto

data class SeriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<SeriesItemDto>,
    val returned: Int
)