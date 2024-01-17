package kr.pe.ssun.carrot.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String, Any?) -> Unit
) {
    val books = viewModel.books.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        var search by rememberSaveable { mutableStateOf("") }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, "Search") },
            placeholder = { Text("Search") },
            value = search,
            onValueChange = {
                search = it
                viewModel.search(it)
            },
            trailingIcon = {
                IconButton(onClick = {
                    search = ""
                    viewModel.search("")
                }) {
                    if (search.isNotBlank()) {
                        Icon(Icons.Default.Close, "Search")
                    }
                }
            }
        )
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(state = listState) {
                items(
                    count = books.itemCount,
                    itemContent = { index ->
                        BookItem(modifier = Modifier.padding(top = 10.dp), item = books[index]!!) {
                            navigate(
                                "book_detail",
                                Triple(
                                    books[index]!!.isbn13,
                                    books[index]!!.title,
                                    books[index]!!.image
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}