package kr.pe.ssun.carrot.network.model

import android.util.JsonReader
import com.google.gson.annotations.SerializedName
import kr.pe.ssun.carrot.data.model.Book
import timber.log.Timber

data class NetworkBook(
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("isbn13") val isbn: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
) {
    companion object {
        fun readBook(reader: JsonReader): NetworkBook {
            var title = ""
            var subtitle = ""
            var isbn = ""
            var price = ""
            var image = ""
            var url = ""

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "title" -> title = reader.nextString()
                    "subtitle" -> subtitle = reader.nextString()
                    "isbn" -> isbn = reader.nextString()
                    "price" -> price = reader.nextString()
                    "image" -> image = reader.nextString()
                    "url" -> url = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return NetworkBook(title, subtitle, isbn, price, image, url)
        }
    }
}

fun NetworkBook.asExternalModel() = Book(
    id = 0,
    name = title,
    description = subtitle,
    thumbnail = image
)