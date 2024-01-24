package kr.pe.ssun.eggplant.domain

import kr.pe.ssun.eggplant.data.repository.BookRepository
import kr.pe.ssun.eggplant.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.eggplant.data.model.BookDetail
import javax.inject.Inject

data class GetBookParam(
    val isbn13: String,
)

class GetBookUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: BookRepository
) : FlowUseCase<GetBookParam, BookDetail>(dispatcher) {

    override fun execute(parameters: GetBookParam): Flow<Result<BookDetail>> =
        repository.getBook(parameters.isbn13).map { book ->
            book?.let {
                Result.success(book)
            } ?: run {
                Result.failure(Throwable("Error"))
            }
        }
}