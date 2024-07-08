package com.example.marvelapp.domain.mapper

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.presentation.model.UiMarvelCharacter

fun MarvelCharacter.toUiModel(): UiMarvelCharacter {
    return UiMarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        isFavorite = this.isFavorite
    )
}

fun UiMarvelCharacter.toDomain(): MarvelCharacter {
    return MarvelCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        isFavorite = this.isFavorite
    )
}