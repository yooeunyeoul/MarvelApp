package com.example.marvelapp.presentation.ui.components

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun SetupPagination(
    lazyGridState: LazyGridState,
    currentItemCount: Int,
    hasMoreItems: Boolean,
    isLoading:Boolean,
    loadMoreItems: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(lazyGridState,currentItemCount) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filter { lastVisibleItemIndex -> lastVisibleItemIndex == currentItemCount - 1 }
            .distinctUntilChanged()
            .collectLatest {
                if (hasMoreItems && !isLoading) {
                    scope.launch {
                        loadMoreItems()
                    }
                }
            }
    }
}
