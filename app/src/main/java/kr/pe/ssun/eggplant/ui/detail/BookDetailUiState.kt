package kr.pe.ssun.eggplant.ui.detail

sealed interface BookDetailUiState {
    data class Success(
        val items: List<BookDetailItem>
    ) : BookDetailUiState
    data class Loading(
        val items: List<BookDetailItem>
    ) : BookDetailUiState
    object Error : BookDetailUiState
}