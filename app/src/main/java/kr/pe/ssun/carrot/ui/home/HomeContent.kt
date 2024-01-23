package kr.pe.ssun.carrot.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.ui.common.SimpleLoadMoreLazyColumn

@Composable
fun HomeContent(
    books: List<Book>,
    isLoading: Boolean,
    search: (String) -> Unit,
    loadMore: () -> Unit,
    navigate: (String, Any?) -> Unit
) {
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
                search(it)
            },
            trailingIcon = {
                IconButton(onClick = {
                    search = ""
                    search("")
                }) {
                    if (search.isNotBlank()) {
                        Icon(Icons.Default.Close, "Search")
                    }
                }
            }
        )
        SimpleLoadMoreLazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            itemCount = books.size,
            itemContent = { index ->
                BookItem(
                    modifier = Modifier.padding(top = 10.dp),
                    item = books[index]
                ) {
                    navigate("book_detail", books[index])
                }
            },
            isLoading = isLoading,
            loadMore = loadMore
        )
    }
}