package kr.pe.ssun.carrot.network

import kr.pe.ssun.carrot.network.model.NetworkWrapper
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class CarrotBookNetwork @Inject constructor(

) : BookNetworkDataSource {

    companion object {
        const val BASE_URL = "https://api.itbook.store/1.0/"
    }

    override suspend fun searchBook(query: String): NetworkWrapper {
        Timber.e("CarrotNetwork.searchBook($query)")
        val url = URL(BASE_URL + "search/$query")
        (url.openConnection() as? HttpURLConnection)?.run {
            Timber.d("[sunchulbaek] BookRepository.search()")
            return NetworkWrapper.readWrapper(inputStream)
        }
        throw Exception("searchBook exception!!!")
    }
}