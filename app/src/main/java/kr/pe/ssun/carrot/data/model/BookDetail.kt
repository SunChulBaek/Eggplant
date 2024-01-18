package kr.pe.ssun.carrot.data.model

data class BookDetail(
    val image: String,
    val title: String,
    val subtitle: String,
    val authors: String? = null,
    val publisher: String? = null,
    val year: String? = null,
    val price: String,
    val description: String? = null,
)