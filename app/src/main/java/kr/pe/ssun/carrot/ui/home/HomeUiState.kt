package kr.pe.ssun.carrot.ui.home

import kr.pe.ssun.carrot.data.model.Book

sealed interface HomeUiState {
    data class Success(
        val books: List<Book> = listOf()
    ) : HomeUiState
    object Init: HomeUiState
    object Loading : HomeUiState
    object Error: HomeUiState
}