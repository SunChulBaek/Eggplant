package kr.pe.ssun.carrot.data.repository

import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.network.BookNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.carrot.network.model.NetworkBook
import kr.pe.ssun.carrot.network.model.asExternalModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val network: BookNetworkDataSource
) {

    fun searchBook(query: String): Flow<List<Book>?> = flow {
        val data = network.searchBook(query = query)
        emit(data.data.map(NetworkBook::asExternalModel))
    }
}