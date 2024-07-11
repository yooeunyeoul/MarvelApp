package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.domain.repository.CharacterRepository
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.domain.util.ResultState
import com.example.marvelapp.domain.util.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(
        nameStartsWith: String,
        offset: Int = 0,
        limit: Int = 10
    ): Flow<ResultState<MarvelCharacterList>> {
        val charactersFlow = characterRepository.getCharacters(nameStartsWith, offset, limit)
        val favoritesFlow = flow { emit(favoriteRepository.getFavoriteCharacterIds()) }

        return charactersFlow.zip(favoritesFlow) { characterList, favoriteIds ->
            MarvelCharacterList(
                total = characterList.total,
                characters = characterList.characters.map { character ->
                    character.copy(isFavorite = favoriteIds.contains(character.id))
                }
            )
        }.asResult()
    }
}