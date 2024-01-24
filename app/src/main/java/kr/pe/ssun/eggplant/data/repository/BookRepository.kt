package kr.pe.ssun.eggplant.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.eggplant.data.model.Book
import kr.pe.ssun.eggplant.data.model.BookDetail
import kr.pe.ssun.eggplant.network.BookNetworkDataSource
import kr.pe.ssun.eggplant.network.model.NetworkBook
import kr.pe.ssun.eggplant.network.model.asExternalModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val network: BookNetworkDataSource
) {

    fun searchBook(query: String, page: Int? = null): Flow<Pair<List<Book>?, Int>> = flow {
        Timber.d("[sunchulbaek] BookRepository.searchBook($query, $page)")
        val data = network.searchBook(query = query, page = page)
        emit(Pair(
            data.books.map(NetworkBook::asExternalModel),
            data.page
        ))
    }

    fun getBook(isbn13: String): Flow<BookDetail?> = flow {
        val data = network.getBook(isbn13 = isbn13)
        emit(data.asExternalModel())
    }
}