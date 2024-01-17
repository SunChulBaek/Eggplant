package kr.pe.ssun.carrot.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.data.model.BookDetail
import kr.pe.ssun.carrot.network.BookNetworkDataSource
import kr.pe.ssun.carrot.network.model.NetworkBook
import kr.pe.ssun.carrot.network.model.asExternalModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val network: BookNetworkDataSource
) {

    fun searchBook(query: String, page: Int? = null): Flow<List<Book>?> = flow {
        val data = network.searchBook(query = query, page = page)
        emit(data.books.map(NetworkBook::asExternalModel))
    }

    fun getBook(isbn13: String): Flow<BookDetail?> = flow {
        val data = network.getBook(isbn13 = isbn13)
        emit(data.asExternalModel())
    }
}