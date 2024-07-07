package com.example.marvelapp.data.local.mapper

import com.example.marvelapp.data.local.entity.CharacterEntity
import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.Thumbnail

fun CharacterEntity.toDomain(): MarvelCharacter {
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = Thumbnail(this.thumbnailPath, this.thumbnailExtension),
        isFavorite = this.isFavorite
    )
}

fun MarvelCharacter.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailPath = this.thumbnail.path,
        thumbnailExtension = this.thumbnail.extension,
        isFavorite = this.isFavorite
    )
}