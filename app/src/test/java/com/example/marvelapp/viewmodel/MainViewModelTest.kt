package com.example.marvelapp.viewmodel

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.domain.usecase.AddFavoriteCharacterUseCase
import com.example.marvelapp.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.domain.usecase.RemoveFavoriteCharacterUseCase
import com.example.marvelapp.domain.util.ResultState
import com.example.marvelapp.presentation.mapper.toUiModel
import com.example.marvelapp.presentation.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase = mockk()
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase = mockk()
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        // 모킹 설정 추가
        coEvery { getFavoriteCharactersUseCase() } returns flowOf(ResultState.Success(emptyList()))

        viewModel = MainViewModel(
            getCharactersUseCase,
            addFavoriteCharacterUseCase,
            removeFavoriteCharacterUseCase,
            getFavoriteCharactersUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChanged updates searchQuery`() = runTest {
        // Given
        val query = "Iron Man"

        // When
        viewModel.onSearchQueryChanged(query)

        // Then
        assertEquals(query, viewModel.uiSearchResults.value.searchQuery)
    }

    @Test
    fun `fetchCharacters updates uiSearchResults on success`() = runTest {
        // Given
        val query = "Iron Man"
        val characters =
            listOf(MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", false))
        val characterList = MarvelCharacterList(1, characters)
        val flow = flowOf(ResultState.Success(characterList))

        coEvery { getCharactersUseCase(query, 0) } returns flow

        // When
        viewModel.handleSearchQuery() // 초기화 함수 명시적 호출

        // When
        viewModel.onSearchQueryChanged(query)
        advanceUntilIdle()

        // Then
        coVerify { getCharactersUseCase(query, 0) }
        assert(viewModel.uiSearchResults.value.characters.isNotEmpty())
    }

    @Test
    fun `getFavoriteCharacters updates uiFavorites on success`() = runTest {
        // Given
        val characters =
            listOf(MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", true))
        val flow = flowOf(ResultState.Success(characters))

        coEvery { getFavoriteCharactersUseCase() } returns flow

        // When
        viewModel.getFavoriteCharacters() // 초기화 함수 명시적 호출

        advanceUntilIdle()

        // Then
        coVerify { getFavoriteCharactersUseCase() }

        // Assertion 추가
        val expected = characters.map { it.toUiModel() }
        val actual = viewModel.uiFavorites.value.characters

        assertEquals(expected, actual, "The favorite characters were not updated correctly.")
    }

    @Test
    fun `addFavorite adds character to favorites`() = runTest {
        // Given
        val character = MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", true)
        coEvery { addFavoriteCharacterUseCase(any()) } just Runs

        // When
        viewModel.addFavorite(character.toUiModel())
        advanceUntilIdle()

        // Then
        coVerify { addFavoriteCharacterUseCase(character) }
    }

    @Test
    fun `removeFavorite removes character from favorites`() = runTest {
        // Given
        val character = MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", true)
        coEvery { removeFavoriteCharacterUseCase(any()) } just Runs

        // When
        viewModel.removeFavorite(character.toUiModel())
        advanceUntilIdle()

        // Then
        coVerify { removeFavoriteCharacterUseCase(character) }
    }
}
