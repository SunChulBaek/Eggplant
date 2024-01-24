package kr.pe.ssun.eggplant.network

import kr.pe.ssun.eggplant.network.model.NetworkBookDetail
import kr.pe.ssun.eggplant.network.model.NetworkWrapper

interface BookNetworkDataSource {
    suspend fun searchBook(
        query: String,
        page: Int? = null
    ): NetworkWrapper

    suspend fun getBook(
        isbn13: String
    ): NetworkBookDetail
}