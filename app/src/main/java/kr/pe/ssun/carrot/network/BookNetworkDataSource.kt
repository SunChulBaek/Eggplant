package kr.pe.ssun.carrot.network

import kr.pe.ssun.carrot.network.model.NetworkWrapper

interface BookNetworkDataSource {
    suspend fun searchBook(
        query: String
    ): NetworkWrapper
}