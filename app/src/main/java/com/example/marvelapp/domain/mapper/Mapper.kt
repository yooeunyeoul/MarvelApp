package com.example.marvelapp.domain.mapper

import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.Thumbnail
import com.example.marvelapp.presentation.model.UiCharacter
import com.example.marvelapp.presentation.model.UiThumbnail

fun Character.toUiModel(): UiCharacter {
    return UiCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = UiThumbnail(this.thumbnail.path, this.thumbnail.extension),
        isFavorite = this.isFavorite
    )
}

fun UiCharacter.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = Thumbnail(path = this.thumbnail.path, extension = this.thumbnail.extension),
        isFavorite = this.isFavorite
    )
}