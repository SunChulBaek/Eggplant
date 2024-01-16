package kr.pe.ssun.carrot.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.carrot.domain.GetBookParam
import kr.pe.ssun.carrot.domain.GetBookUseCase
import kr.pe.ssun.carrot.navigation.BookDetailArgs
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBookUseCase: GetBookUseCase
) : ViewModel() {

    private val args = BookDetailArgs(savedStateHandle)

    val uiState = getBookUseCase(GetBookParam(args.isbn13))
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