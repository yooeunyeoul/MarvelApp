package com.example.marvelapp.data.remote.dto

data class StoriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<StoryItemDto>,
    val returned: Int
)