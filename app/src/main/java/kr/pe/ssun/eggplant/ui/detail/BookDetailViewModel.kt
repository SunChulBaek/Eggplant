package kr.pe.ssun.eggplant.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.eggplant.data.model.BookDetail
import kr.pe.ssun.eggplant.domain.GetBookParam
import kr.pe.ssun.eggplant.domain.GetBookUseCase
import kr.pe.ssun.eggplant.navigation.BookDetailArgs
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
                BookDetailUiState.Success(makeItems(it))
            } ?: BookDetailUiState.Error
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookDetailUiState.Loading(
                makeItems(BookDetail(
                    title = args.title,
                    subtitle = args.subtitle,
                    price = args.price,
                    image = args.image
                ))
            )
        )

    private fun makeItems(book: BookDetail): List<BookDetailItem> = mutableListOf<BookDetailItem>().apply {
        add(BookDetailItem.BookDetailImage(book.image))
        add(BookDetailItem.BookDetailTitle(book.title))
        if (book.subtitle.isNotBlank()) {
            add(BookDetailItem.BookDetailSubtitle(book.subtitle))
        }
        add(BookDetailItem.BookDetailInfo(book))
        add(BookDetailItem.BookDetailDesc(book.description))
    }
}