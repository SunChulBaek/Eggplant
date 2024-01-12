package kr.pe.ssun.carrot.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.carrot.data.model.Book

data class NetworkBook(
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("isbn13") val isbn: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
)

fun NetworkBook.asExternalModel() = Book(
    id = 0,
    name = title,
    description = subtitle,
    thumbnail = image
)