package kr.pe.ssun.carrot.ui.home

import kr.pe.ssun.carrot.data.model.Book

sealed interface HomeUiState {
    data class Success(
        val books: List<Book> = listOf(),
        val currentPage: Int = 1
    ) : HomeUiState
    object Loading : HomeUiState
    object Error : HomeUiState
}