package com.example.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.domain.mapper.toUiModel
import com.example.marvelapp.domain.usecase.AddFavoriteCharacterUseCase
import com.example.marvelapp.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<UiMarvelCharacter>>(emptyList())
    val searchResults: StateFlow<List<UiMarvelCharacter>> = _searchResults

    fun searchCharacters(nameStartsWith: String, offset: Int = 0, limit: Int = 10) {
        viewModelScope.launch {
            val characters = getCharactersUseCase(nameStartsWith, offset, limit)
            _searchResults.value = characters.map { it.toUiModel() }
        }
    }

    fun addFavorite(character: UiMarvelCharacter) {
        viewModelScope.launch {
            addFavoriteCharacterUseCase(character)
        }
    }

    fun removeFavorite(character: UiMarvelCharacter) {
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(character)
        }
    }
}