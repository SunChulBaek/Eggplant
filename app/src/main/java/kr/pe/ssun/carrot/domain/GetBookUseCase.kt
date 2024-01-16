package kr.pe.ssun.carrot.domain

import kr.pe.ssun.carrot.data.repository.BookRepository
import kr.pe.ssun.carrot.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.carrot.data.model.BookDetail
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