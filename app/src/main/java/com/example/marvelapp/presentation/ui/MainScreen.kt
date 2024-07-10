import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvelapp.presentation.ui.FavoriteScreen
import com.example.marvelapp.presentation.ui.SearchScreen
import com.example.marvelapp.presentation.ui.components.SetupPagination
import com.example.marvelapp.presentation.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.uiSearchResults.collectAsState()
    val uiFavorites by viewModel.uiFavorites.collectAsState()
    val scope = rememberCoroutineScope()

    val tabs = listOf("Search" to Icons.Default.Search, "Favorite" to Icons.Default.Favorite)

    val loadMoreItems = { viewModel.searchBooks(searchQuery) }

    SetupPagination(
        viewModel.searchScrollState,
        searchResults.characters.size,
        searchResults.characters.size < searchResults.total,
        isLoading = searchResults.loading,
        loadMoreItems
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                count = tabs.size,
                state = pagerState,
                modifier = Modifier.weight(1f),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> SearchScreen(
                        searchQuery = searchQuery,
                        searchResults = searchResults.characters.toImmutableList(),
                        onSearchQueryChanged = viewModel::onSearchQueryChanged,
                        onFavoriteClick = { character ->
                            if (character.isFavorite) {
                                viewModel.removeFavorite(character)
                            } else {
                                viewModel.addFavorite(character)
                            }
                        },
                        isLoading = searchResults.loading,
                        error = searchResults.error,
                        scrollState = viewModel.searchScrollState
                    )

                    1 -> FavoriteScreen(
                        favoriteCharacters = uiFavorites.characters.toImmutableList(),
                        onRemoveFavorite = viewModel::removeFavorite,
                        scrollState = viewModel.favoriteScrollState
                    )
                }
            }
        }

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.align(Alignment.BottomCenter),
            indicator = {}
        ) {
            tabs.forEachIndexed { index, tabData ->
                Tab(
                    text = {
                        Text(
                            tabData.first,
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = tabData.second,
                            contentDescription = null,
                            tint = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }
    }
}
