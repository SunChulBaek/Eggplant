package kr.pe.ssun.carrot.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.carrot.domain.SearchBookParam
import kr.pe.ssun.carrot.domain.SearchBookUseCase
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCharacterUseCase: SearchBookUseCase
) : ViewModel() {

    val id = savedStateHandle.get<Int>("id") ?: 0

    val uiState = getCharacterUseCase(SearchBookParam(""))
        .map { result ->
            result.getOrNull()?.let {
                BookDetailUiState.Error
            } ?: BookDetailUiState.Error
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookDetailUiState.Loading
        )
}