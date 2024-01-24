package kr.pe.ssun.eggplant.network

import kr.pe.ssun.eggplant.network.model.NetworkBookDetail
import kr.pe.ssun.eggplant.network.model.NetworkWrapper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class EggplantBookNetwork @Inject constructor(

) : BookNetworkDataSource {

    companion object {
        private const val BASE_URL = "https://api.itbook.store/1.0/"
    }

    override suspend fun searchBook(
        query: String,
        page: Int?
    ): NetworkWrapper = get(
        "search/$query${if (page != null) "/$page" else ""}",
        NetworkWrapper::readWrapper
    ) ?: run {
        throw Exception("searchBook Exception!!!")
    }

    override suspend fun getBook(
        isbn13: String
    ): NetworkBookDetail = get(
        "books/$isbn13",
        NetworkBookDetail::readBookDetail
    ) ?: run {
        throw Exception("getBook Exception!!!")
    }

    private fun <R> get(
        url: String,
        mapper: (InputStream) -> R
    ) = (URL("${BASE_URL}$url").openConnection() as? HttpURLConnection)?.let { conn ->
        mapper(conn.inputStream)
    }
}