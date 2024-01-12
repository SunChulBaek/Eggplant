package kr.pe.ssun.carrot.domain

import kr.pe.ssun.carrot.data.repository.BookRepository
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class SearchBookParam(
    val query: String,
)

class SearchBookUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : FlowUseCase<SearchBookParam, List<Book>>(dispatcher) {

    override fun execute(parameters: SearchBookParam): Flow<Result<List<Book>>> =
        repository.searchBook(parameters.query).map { books ->
            books?.let {
                Result.success(books)
            } ?: run {
                Result.failure(Throwable("Error"))
            }
        }
}