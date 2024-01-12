package kr.pe.ssun.carrot.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kr.pe.ssun.carrot.domain.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.carrot.domain.SearchBookParam
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    searchBookUseCase: SearchBookUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    val uiState = query.debounce(300).map { query ->
        searchBookUseCase(SearchBookParam(query)).first().getOrNull()
    }.map { books ->
        books?.let {
            HomeUiState.Success(books)
        } ?: run {
            HomeUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Init
    )

    fun search(query: String) {
        this@HomeViewModel.query.value = query
    }
}