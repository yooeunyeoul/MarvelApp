package com.example.marvelapp.data.remote.mapper

import com.example.marvelapp.data.remote.dto.MarvelApiResponseDto
import com.example.marvelapp.data.remote.dto.MarvelCharacterDto
import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.Thumbnail

fun MarvelApiResponseDto.toDomain(): List<MarvelCharacter> {
    return this.data.results.map { it.toDomain() }
}

fun MarvelCharacterDto.toDomain(): MarvelCharacter {
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = Thumbnail(this.thumbnail.path, this.thumbnail.extension),
        isFavorite = false // 기본 값으로 설정
    )
}