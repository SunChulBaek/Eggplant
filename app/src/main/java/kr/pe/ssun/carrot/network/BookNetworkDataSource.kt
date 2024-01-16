package kr.pe.ssun.carrot.network

import kr.pe.ssun.carrot.network.model.NetworkBookDetail
import kr.pe.ssun.carrot.network.model.NetworkWrapper

interface BookNetworkDataSource {
    suspend fun searchBook(
        query: String,
        page: Int? = null
    ): NetworkWrapper

    suspend fun getBook(
        isbn13: String
    ): NetworkBookDetail
}