package com.example.marvelapp.presentation.mapper

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import com.example.marvelapp.presentation.model.UiSearchCharactersState

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



fun MarvelCharacterList.toUiModel(favoriteIds: Set<Int>, loading: Boolean = false, error: String? = null): UiSearchCharactersState {
    val characters = this.characters.map { character ->
        character.toUiModel().copy(isFavorite = favoriteIds.contains(character.id))
    }
    return UiSearchCharactersState(
        total = this.total,
        characters = characters,
        loading = loading,
        error = error
    )
}