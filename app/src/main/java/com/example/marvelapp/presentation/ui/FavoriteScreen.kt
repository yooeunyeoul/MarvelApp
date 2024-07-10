package com.example.marvelapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import com.example.marvelapp.presentation.ui.components.CharacterCard
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FavoriteScreen(
    favoriteCharacters: ImmutableList<UiMarvelCharacter>,
    onRemoveFavorite: (UiMarvelCharacter) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = LazyGridState()
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (favoriteCharacters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "즐겨찾기한 캐릭터가 없습니다.", style = MaterialTheme.typography.headlineSmall)
            }
        } else {
            LazyVerticalGrid(
                state = scrollState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(favoriteCharacters, key = { it.id }) { character ->
                    CharacterCard(
                        character = character,
                        onFavoriteClick = {
                            onRemoveFavorite(character)
                        }
                    )
                }
            }
        }
    }
}
