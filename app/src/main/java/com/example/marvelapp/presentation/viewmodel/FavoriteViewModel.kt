package com.example.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.presentation.model.MarvelUiCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : ViewModel() {

    private val _favoriteCharacters = MutableStateFlow<List<MarvelUiCharacter>>(emptyList())
    val favoriteCharacters: StateFlow<List<MarvelUiCharacter>> = _favoriteCharacters

    init {
        viewModelScope.launch {
            getFavoriteCharactersUseCase().collect { characters ->
                _favoriteCharacters.value = characters
            }
        }
    }

    fun removeFavorite(character: MarvelUiCharacter) {
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(character)
        }
    }
}