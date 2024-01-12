package kr.pe.ssun.carrot.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.pe.ssun.carrot.ui.common.ErrorScreen
import kr.pe.ssun.carrot.ui.common.LoadingScreen

@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String, Any?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        var search by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, "Search") },
            value = search,
            onValueChange = {
                search = it
                viewModel.search(it)
            }
        )
        Box(modifier = Modifier.weight(1f)) {
            when (uiState) {
                HomeUiState.Init -> Box { }
                HomeUiState.Loading -> LoadingScreen()
                HomeUiState.Error -> ErrorScreen()
                is HomeUiState.Success -> {
                    val books = (uiState as HomeUiState.Success).books
                    LazyColumn(state = listState) {
                    items(
                        count = books.size,
                        itemContent = { index ->
                            BookItem(modifier = Modifier.padding(top = 10.dp), item = books[index]) {
//                                navigate(
//                                    "book_detail",
//                                    Triple(
//                                        books[index]!!.id,
//                                        books[index]!!.name,
//                                        books[index]!!.thumbnail
//                                    )
//                                )
                            }
                        }
                    )
                    }
                }
            }
        }
    }
}