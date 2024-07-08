package com.example.marvelapp.data.local.mapper

import com.example.marvelapp.data.local.entity.CharacterEntity
import com.example.marvelapp.domain.model.MarvelCharacter

fun CharacterEntity.toDomain(): MarvelCharacter {
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = this.isFavorite
    )
}

fun MarvelCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        isFavorite = this.isFavorite
    )
}