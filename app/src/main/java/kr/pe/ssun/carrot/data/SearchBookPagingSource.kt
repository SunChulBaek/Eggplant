package kr.pe.ssun.carrot.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.data.repository.BookRepository
import timber.log.Timber
import javax.inject.Inject

class SearchBookPagingSource @Inject constructor(
    private val repository: BookRepository,
    private val query: String
) : PagingSource<Int, Book>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key
            val books = repository.searchBook(
                query = query,
                page = nextPageNumber
            ).flowOn(Dispatchers.IO).first() ?: listOf()
            LoadResult.Page(
                data = if (query.isNotBlank()) books else listOf(),
                prevKey = null, // Only paging forward
                nextKey = if (query.isNotBlank()) (nextPageNumber ?: 1) + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.message))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        Timber.d("[sunchulbaek] SearchBookPagingSource.getRefreshKey()")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}