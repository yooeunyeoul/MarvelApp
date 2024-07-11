package com.example.marvelapp.usecase

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.domain.repository.CharacterRepository
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.domain.util.ResultState
import com.example.marvelapp.util.assertResultStateListEquals
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        favoriteRepository = mockk()
        getCharactersUseCase = GetCharactersUseCase(characterRepository, favoriteRepository)
    }

    @Test
    fun `invoke should return characters with updated favorite status`() = runTest {
        // Given
        val characters = listOf(
            MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", false),
            MarvelCharacter(2, "Spider-Man", "A young superhero", "url2", false)
        )
        val favoriteIds = listOf(1)
        val characterList = MarvelCharacterList(2, characters)

        coEvery { characterRepository.getCharacters("I", 0, 10) } returns flowOf(characterList)
        coEvery { favoriteRepository.getFavoriteCharacterIds() } returns favoriteIds

        // When
        val result = getCharactersUseCase.invoke("I", 0, 10).toList()

        // Then
        val expectedCharacters = listOf(
            MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", true),
            MarvelCharacter(2, "Spider-Man", "A young superhero", "url2", false)
        )
        val expected = ResultState.Success(MarvelCharacterList(2, expectedCharacters))

        assertResultStateListEquals(listOf(ResultState.Loading, expected), result)
        coVerify { characterRepository.getCharacters("I", 0, 10) }
        coVerify { favoriteRepository.getFavoriteCharacterIds() }
    }

    @Test
    fun `invoke should return error when characterRepository throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { characterRepository.getCharacters("I", 0, 10) } returns flow { throw exception }
        coEvery { favoriteRepository.getFavoriteCharacterIds() } returns emptyList()

        // When
        val result = getCharactersUseCase.invoke("I", 0, 10).toList()

        // Then
        val expected = ResultState.Error(exception)

        assertResultStateListEquals(listOf(ResultState.Loading, expected), result)
        coVerify { characterRepository.getCharacters("I", 0, 10) }
        coVerify(exactly = 0) { favoriteRepository.getFavoriteCharacterIds() }
    }

    @Test
    fun `invoke should return error when favoriteRepository throws exception`() = runTest {
        // Given
        val characters = listOf(
            MarvelCharacter(1, "Iron Man", "A billionaire inventor", "url1", false),
            MarvelCharacter(2, "Spider-Man", "A young superhero", "url2", false)
        )
        val characterList = MarvelCharacterList(2, characters)
        val exception = RuntimeException("Database error")

        coEvery { characterRepository.getCharacters("I", 0, 10) } returns flowOf(characterList)
        coEvery { favoriteRepository.getFavoriteCharacterIds() } throws exception

        // When
        val result = getCharactersUseCase.invoke("I", 0, 10).toList()

        // Then
        val expected = ResultState.Error(exception)

        assertResultStateListEquals(listOf(ResultState.Loading, expected), result)
        coVerify { characterRepository.getCharacters("I", 0, 10) }
        coVerify { favoriteRepository.getFavoriteCharacterIds() }
    }
}
