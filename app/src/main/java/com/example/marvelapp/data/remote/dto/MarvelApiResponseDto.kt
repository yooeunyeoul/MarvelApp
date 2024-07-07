package com.example.marvelapp.data.remote.dto

data class MarvelApiResponseDto(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: DataDto
)