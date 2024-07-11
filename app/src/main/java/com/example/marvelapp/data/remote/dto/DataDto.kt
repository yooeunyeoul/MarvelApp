package com.example.marvelapp.data.remote.dto

data class DataDto(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<MarvelCharacterDto>
)
