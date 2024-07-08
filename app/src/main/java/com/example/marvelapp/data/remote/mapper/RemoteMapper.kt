package com.example.marvelapp.data.remote.mapper

import com.example.marvelapp.data.remote.dto.MarvelApiResponseDto
import com.example.marvelapp.data.remote.dto.MarvelCharacterDto
import com.example.marvelapp.domain.model.MarvelCharacter

fun MarvelApiResponseDto.toDomain(): List<MarvelCharacter> {
    return this.data.results.map { it.toDomain() }
}

fun MarvelCharacterDto.toDomain(): MarvelCharacter {
    val thumbnailUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = false // 기본 값으로 설정
    )
}