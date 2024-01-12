package kr.pe.ssun.carrot.network.model

import com.google.gson.annotations.SerializedName

data class NetworkWrapper(
    @SerializedName("total") val total: Int,
    @SerializedName("page") val status: Int,
    @SerializedName("books") val data: List<NetworkBook>,
)
