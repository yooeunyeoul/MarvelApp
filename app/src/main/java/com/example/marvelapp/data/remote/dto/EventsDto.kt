package com.example.marvelapp.data.remote.dto

data class EventsDto(
    val available: Int,
    val collectionURI: String,
    val items: List<EventItemDto>,
    val returned: Int
)