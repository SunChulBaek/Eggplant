package kr.pe.ssun.carrot.network.model

import android.util.JsonReader
import com.google.gson.annotations.SerializedName
import kr.pe.ssun.carrot.data.model.BookDetail
import java.io.InputStream
import java.io.InputStreamReader

data class NetworkBookDetail(
    @SerializedName("error") val error: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("authors") val authors: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("isbn10") val isbn10: String,
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("pages") val pages: String,
    @SerializedName("year") val year: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
    @SerializedName("pdf") val pdf: Map<String, String>?
) {
    companion object {
        fun readBookDetail(inputStream: InputStream): NetworkBookDetail {
            val reader = JsonReader(InputStreamReader(inputStream))
            try {
                var error = ""
                var title = ""
                var subtitle = ""
                var authors = ""
                var publisher = ""
                var isbn10 = ""
                var isbn13 = ""
                var pages = ""
                var year = ""
                var rating = ""
                var desc = ""
                var price = ""
                var image = ""
                var url = ""
                var pdf: Map<String, String>? = null

                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        "error" -> error = reader.nextString()
                        "title" -> title = reader.nextString()
                        "subtitle" -> subtitle = reader.nextString()
                        "authors" -> authors = reader.nextString()
                        "publisher" -> publisher = reader.nextString()
                        "isbn10" -> isbn10 = reader.nextString()
                        "isbn13" -> isbn13 = reader.nextString()
                        "pages" -> pages = reader.nextString()
                        "year" -> year = reader.nextString()
                        "rating" -> rating = reader.nextString()
                        "desc" -> desc = reader.nextString()
                        "price" -> price = reader.nextString()
                        "image" -> image = reader.nextString()
                        "url" -> url = reader.nextString()
                        "pdf" -> pdf = readPdf(reader)
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                return NetworkBookDetail(
                    error = error,
                    title = title,
                    subtitle = subtitle,
                    authors = authors,
                    publisher = publisher,
                    isbn10 = isbn10,
                    isbn13=  isbn13,
                    pages = pages,
                    year = year,
                    rating = rating,
                    desc = desc,
                    price = price,
                    image = image,
                    url = url,
                    pdf = pdf
                )
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception()
            } finally {
                reader.close()
            }
        }

        private fun readPdf(reader: JsonReader) = mutableMapOf<String, String>().apply {
            reader.beginObject()
            while (reader.hasNext()) {
                this[reader.nextName()] = reader.nextString()
            }
            reader.endObject()
        }
    }
}

fun NetworkBookDetail.asExternalModel() = BookDetail(
    image = image,
    title = title,
    subtitle = subtitle,
    authors = authors,
    publisher = publisher,
    year = year,
    price = price,
    description = desc,
    pages = pages,
    rating = rating,
    isbn10 = isbn10,
    isbn13 = isbn13,
    pdf = pdf
)