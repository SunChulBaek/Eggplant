package kr.pe.ssun.carrot.ui.detail

import kr.pe.ssun.carrot.data.model.Book

sealed interface BookDetailUiState {
    data class Success(
        val book: Book
    ) : BookDetailUiState
    object Loading : BookDetailUiState
    object Error : BookDetailUiState
}