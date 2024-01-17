package kr.pe.ssun.carrot.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.data.repository.BookRepository
import kr.pe.ssun.carrot.util.IoDispatcher
import javax.inject.Inject

data class SearchBookParam(
    val query: String,
    val page: Int? = null
)

data class SearchBookResult(
    val books: List<Book>,
    val currentPage: Int
)

class SearchBookUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : FlowUseCase<SearchBookParam, SearchBookResult>(dispatcher) {

    override fun execute(parameters: SearchBookParam): Flow<Result<SearchBookResult>> =
        repository.searchBook(parameters.query, parameters.page).map { result ->
            Result.success(SearchBookResult(
                books = result.first ?: listOf(),
                currentPage = result.second
            ))
        }
}