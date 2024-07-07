package com.example.marvelapp.data.remote.mapper

import com.example.marvelapp.data.remote.dto.MarvelApiResponseDto
import com.example.marvelapp.data.remote.dto.MarvelCharacterDto
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.Thumbnail

fun MarvelApiResponseDto.toDomain(): List<Character> {
    return this.data.results.map { it.toDomain() }
}

fun MarvelCharacterDto.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = Thumbnail(this.thumbnail.path, this.thumbnail.extension),
        isFavorite = false // 기본 값으로 설정
    )
}