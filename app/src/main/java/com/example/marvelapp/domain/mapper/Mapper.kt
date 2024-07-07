package com.example.marvelapp.domain.mapper

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.Thumbnail
import com.example.marvelapp.presentation.model.MarvelUiCharacter
import com.example.marvelapp.presentation.model.UiThumbnail

fun MarvelCharacter.toUiModel(): MarvelUiCharacter {
    return MarvelUiCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = UiThumbnail(this.thumbnail.path, this.thumbnail.extension),
        isFavorite = this.isFavorite
    )
}

fun MarvelUiCharacter.toDomain(): MarvelCharacter {
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = Thumbnail(path = this.thumbnail.path, extension = this.thumbnail.extension),
        isFavorite = this.isFavorite
    )
}