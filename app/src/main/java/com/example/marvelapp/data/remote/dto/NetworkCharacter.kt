package com.example.marvelapp.data.remote.dto

data class NetworkCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: NetworkThumbnail
)