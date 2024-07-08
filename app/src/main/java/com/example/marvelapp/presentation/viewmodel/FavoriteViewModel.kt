package com.example.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.presentation.model.UiMarvelCharacter
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

    private val _favoriteCharacters = MutableStateFlow<List<UiMarvelCharacter>>(emptyList())
    val favoriteCharacters: StateFlow<List<UiMarvelCharacter>> = _favoriteCharacters

    init {
        viewModelScope.launch {
            getFavoriteCharactersUseCase().collect { characters ->
                _favoriteCharacters.value = characters
            }
        }
    }

    fun removeFavorite(character: UiMarvelCharacter) {
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(character)
        }
    }
}