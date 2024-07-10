package com.example.marvelapp.presentation.viewmodel

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.domain.usecase.AddFavoriteCharacterUseCase
import com.example.marvelapp.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.presentation.mapper.toDomain
import com.example.marvelapp.presentation.mapper.toUiModel
import com.example.marvelapp.presentation.model.UiFavoriteCharactersState
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import com.example.marvelapp.presentation.model.UiSearchCharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiSearchResults = MutableStateFlow(UiSearchCharactersState())
    val uiSearchResults: StateFlow<UiSearchCharactersState> = _uiSearchResults

    private val _uiFavorites = MutableStateFlow(UiFavoriteCharactersState())
    val uiFavorites: StateFlow<UiFavoriteCharactersState> = _uiFavorites

    val searchScrollState = LazyGridState()
    val favoriteScrollState = LazyGridState()

    private var currentOffset = 0

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.length >= 2) {
                        currentOffset = 0
                        _uiSearchResults.update { it.copy(loading = true, error = null) }
                        try {
                            getCharactersUseCase(query, currentOffset).collect { result ->
                                _uiSearchResults.update { result }
                            }
                        } catch (e: Exception) {
                            if (e !is CancellationException) {
                                _uiSearchResults.update { it.copy(error = e.message) }
                            }
                        } finally {
                            _uiSearchResults.update { it.copy(loading = false) }
                        }
                    }
                }
        }

        viewModelScope.launch {
            getFavoriteCharactersUseCase().collectLatest { favoriteCharacters ->
                _uiFavorites.update {
                    UiFavoriteCharactersState(
                        characters = favoriteCharacters.map { it.toUiModel() },
                        loading = false,
                        error = null
                    )
                }
                updateFavoriteStatus(favoriteCharacters.map { it.id })
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun addFavorite(character: UiMarvelCharacter) {
        viewModelScope.launch {
            addFavoriteCharacterUseCase(character.toDomain())
        }
    }

    fun removeFavorite(character: UiMarvelCharacter) {
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(character.toDomain())
        }
    }

    private fun updateFavoriteStatus(favoriteIds: List<Int>) {
        _uiSearchResults.update { searchResults ->
            val updatedCharacters = searchResults.characters.map { character ->
                character.copy(isFavorite = favoriteIds.contains(character.id))
            }
            searchResults.copy(characters = updatedCharacters)
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _uiSearchResults.update { it.copy(loading = true) }
            currentOffset += 10
            getCharactersUseCase(query, offset = currentOffset)
                .collect { result ->
                    _uiSearchResults.update {
                        it.copy(
                            characters = it.characters + result.characters,
                            loading = false,
                            total = result.total,
                            error = result.error
                        )
                    }
                }
        }
    }
}
