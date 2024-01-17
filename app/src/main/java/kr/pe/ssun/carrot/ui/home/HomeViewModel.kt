package kr.pe.ssun.carrot.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kr.pe.ssun.carrot.data.SearchBookPagingSource
import kr.pe.ssun.carrot.data.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: BookRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")

    val books = query.debounce(300)
        .map { query ->
            Pager(
                config = PagingConfig(pageSize = 20)
            ) { SearchBookPagingSource(repository, query) }
                .flow.cachedIn(viewModelScope).first()
        }

    fun search(query: String) {
        this@HomeViewModel.query.value = query
    }
}