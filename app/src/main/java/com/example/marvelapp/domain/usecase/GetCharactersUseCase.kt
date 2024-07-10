package com.example.marvelapp.domain.usecase

import com.example.marvelapp.presentation.mapper.toUiModel
import com.example.marvelapp.domain.repository.CharacterRepository
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.UiSearchCharactersState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(nameStartsWith: String, offset: Int = 0, limit: Int = 10): Flow<UiSearchCharactersState> {
        val charactersFlow = characterRepository.getCharacters(nameStartsWith, offset, limit)
        val favoritesFlow = flow { emit(favoriteRepository.getFavoriteCharacterIds()) }

        return combine(charactersFlow, favoritesFlow) { characterList, favoriteIds ->
            characterList.toUiModel(favoriteIds.toSet())
        }.catch { e ->
            emit(UiSearchCharactersState(total = 0, characters = emptyList(), loading = false, error = e.message))
        }.onStart {
            emit(UiSearchCharactersState(total = 0, characters = emptyList(), loading = true, error = null))
        }
    }
}
