package kr.pe.ssun.carrot.ui.detail

import kr.pe.ssun.carrot.data.model.BookDetail

sealed interface BookDetailUiState {
    data class Success(
        val items: List<BookDetailItem>
    ) : BookDetailUiState
    data class Loading(
        val items: List<BookDetailItem>
    ) : BookDetailUiState
    object Error : BookDetailUiState
}