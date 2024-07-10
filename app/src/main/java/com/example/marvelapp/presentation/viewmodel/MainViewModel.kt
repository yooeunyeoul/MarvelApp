package com.example.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.domain.usecase.AddFavoriteCharacterUseCase
import com.example.marvelapp.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.domain.util.ResultState
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

    private val _uiSearchResults = MutableStateFlow(UiSearchCharactersState())
    val uiSearchResults: StateFlow<UiSearchCharactersState> = _uiSearchResults

    private val _uiFavorites = MutableStateFlow(UiFavoriteCharactersState())
    val uiFavorites: StateFlow<UiFavoriteCharactersState> = _uiFavorites

    init {
        viewModelScope.launch {
            handleSearchQuery()
        }

        viewModelScope.launch {
            getFavoriteCharacters()
        }
    }

    private suspend fun handleSearchQuery() {
        _uiSearchResults
            .debounce(300)
            .distinctUntilChanged { old, new -> old.searchQuery == new.searchQuery }
            .collectLatest { state ->
                if (state.searchQuery.length >= 2) {
                    fetchCharacters(state.searchQuery, state.currentOffset, reset = true)
                }
            }
    }

    private suspend fun getFavoriteCharacters() {
        getFavoriteCharactersUseCase().collectLatest { result ->
            handleFavoriteCharactersResult(result)
        }
    }

    private suspend fun fetchCharacters(query: String, offset: Int, reset: Boolean = false) {
        try {
            getCharactersUseCase(query, offset).collect { result ->
                handleSearchCharactersResult(result, reset)
            }
        } catch (e: Exception) {
            if (e !is CancellationException) {
                updateSearchResults { it.copy(error = e.message, loading = false) }
            }
        }
    }

    private fun handleSearchCharactersResult(
        result: ResultState<MarvelCharacterList>,
        reset: Boolean
    ) {
        when (result) {
            is ResultState.Loading -> {
                updateSearchResults { it.copy(loading = true) }
            }

            is ResultState.Success -> {
                updateSearchResults {
                    it.copy(
                        total = result.data.total,
                        characters = if (reset) result.data.characters.map { character -> character.toUiModel() }
                        else it.characters + result.data.characters.map { character -> character.toUiModel() },
                        loading = false,
                        error = null,
                        currentOffset = if (reset) 0 else it.currentOffset + result.data.characters.count()
                    )
                }
            }

            is ResultState.Error -> {
                updateSearchResults { it.copy(error = result.exception.message, loading = false) }
            }
        }
    }

    private fun handleFavoriteCharactersResult(result: ResultState<List<MarvelCharacter>>) {
        when (result) {
            is ResultState.Loading -> {
                _uiFavorites.update { it.copy(loading = true) }
            }

            is ResultState.Success -> {
                _uiFavorites.update {
                    UiFavoriteCharactersState(
                        characters = result.data.map { it.toUiModel() },
                        loading = false,
                        error = null
                    )
                }
                updateFavoriteStatus(result.data.map { it.id })
            }

            is ResultState.Error -> {
                _uiFavorites.update { it.copy(error = result.exception.message, loading = false) }
            }
        }
    }

    private fun updateSearchResults(update: (UiSearchCharactersState) -> UiSearchCharactersState) {
        _uiSearchResults.update(update)
    }

    fun onSearchQueryChanged(query: String) {
        _uiSearchResults.update { it.copy(searchQuery = query, currentOffset = 0) }
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
        updateSearchResults { searchResults ->
            val updatedCharacters = searchResults.characters.map { character ->
                character.copy(isFavorite = favoriteIds.contains(character.id))
            }
            searchResults.copy(characters = updatedCharacters)
        }
    }

    fun loadMoreCharacters(query: String) {
        viewModelScope.launch {
            fetchCharacters(query, _uiSearchResults.value.currentOffset)
        }
    }
}
