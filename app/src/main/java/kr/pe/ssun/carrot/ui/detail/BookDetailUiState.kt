package kr.pe.ssun.carrot.ui.detail

import kr.pe.ssun.carrot.data.model.BookDetail

sealed interface BookDetailUiState {
    data class Success(
        val bookDetail: BookDetail
    ) : BookDetailUiState
    data class Loading(
        val bookDetail: BookDetail
    ) : BookDetailUiState
    object Error : BookDetailUiState
}