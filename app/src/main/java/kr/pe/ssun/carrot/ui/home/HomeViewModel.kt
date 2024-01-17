package kr.pe.ssun.carrot.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.pe.ssun.carrot.data.SearchBookPagingSource
import kr.pe.ssun.carrot.data.repository.BookRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BookRepository,
) : ViewModel() {

    private var _books = Pager(
        config = PagingConfig(pageSize = 20)
    ) { SearchBookPagingSource(repository, "") }
    .flow.cachedIn(viewModelScope)
    val books get() = _books

    fun search(query: String) = viewModelScope.launch {
        // TODO : debounce 걸기
        Timber.d("[sunchulbaek] search = $query xxx")
        // query를 StateFlow로 만들어서 상세화면 진입 후 돌아왔을 때, 다시 Pager가 생성되는 이슈가 있어서
        // search에 Pager 생성하도록 변경 (상세 진입 후 스크롤 포지션 깨짐 vs 매번 api 호출)
        _books = Pager(
            config = PagingConfig(pageSize = 20)
        ) { SearchBookPagingSource(repository, query) }
            .flow.cachedIn(viewModelScope)
    }
}