package kr.pe.ssun.carrot.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.domain.SearchBookParam
import kr.pe.ssun.carrot.domain.SearchBookUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    searchBookUseCase: SearchBookUseCase,
) : ViewModel() {

    val param = MutableStateFlow(SearchBookParam(""))

    private var books = mutableListOf<Book>()

    val uiState: StateFlow<HomeUiState> = param
        .debounce(300)
        .map { param ->
            if ((param.page ?: 0) == 0) {
                books.clear()
            }
            searchBookUseCase(param).first().getOrNull()?.let { result ->
                books.addAll(result.books)
                // books를 그대로 올리니까 반영이 안되서 새 리스트를 만듬
                HomeUiState.Success(
                    books = mutableListOf<Book>().apply { addAll(books) },
                    currentPage = result.currentPage
                )
            } ?: run {
                Timber.d("[sunchulbaek] error")
                HomeUiState.Error
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun search(query: String) = viewModelScope.launch {
        param.emit(SearchBookParam(query))
    }

    fun loadMore() = viewModelScope.launch {
        param.value.let { oldParam ->
            param.emit(SearchBookParam(oldParam.query, (oldParam.page ?: 1) + 1))
        }
    }
}