package kr.pe.ssun.carrot.ui.detail

import kr.pe.ssun.carrot.data.model.BookDetail

sealed interface BookDetailUiState {
    data class Success(
        val bookDetail: BookDetail
    ) : BookDetailUiState
    object Loading : BookDetailUiState
    object Error : BookDetailUiState
}