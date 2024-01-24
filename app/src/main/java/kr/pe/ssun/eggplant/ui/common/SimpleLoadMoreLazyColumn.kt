package kr.pe.ssun.eggplant.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SimpleLoadMoreLazyColumn(
    modifier: Modifier = Modifier,
    itemCount: Int,
    itemContent: @Composable (Int) -> Unit,
    isLoading: Boolean,
    loadMore: () -> Unit,
    progress: @Composable (Modifier) -> Unit = { CircularProgressIndicator(it) }
) = Box(
    modifier = modifier
) {
    LazyColumn {
        items(
            count = itemCount,
            itemContent = { index ->
                itemContent(index)
                if (index == itemCount - 1 && !isLoading) {
                    loadMore()
                }
            }
        )
    }
    if (isLoading) {
        progress(Modifier.align(Alignment.Center))
    }
}