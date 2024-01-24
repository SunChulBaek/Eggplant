package kr.pe.ssun.eggplant.ui.home

import kr.pe.ssun.eggplant.data.model.Book

sealed interface HomeUiState {
    data class Success(
        val books: List<Book> = listOf(),
        val currentPage: Int = 1
    ) : HomeUiState
    object Loading : HomeUiState
    object Error : HomeUiState
}