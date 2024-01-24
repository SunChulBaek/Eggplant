package kr.pe.ssun.eggplant.network.model

import android.util.JsonReader
import com.google.gson.annotations.SerializedName
import java.io.InputStream
import java.io.InputStreamReader

data class NetworkWrapper(
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("books") val books: List<NetworkBook>,
) {
    companion object {
        fun readWrapper(inputStream: InputStream): NetworkWrapper {
            val reader = JsonReader(InputStreamReader(inputStream))
            try {
                var total = 0
                var page = 0
                var books = listOf<NetworkBook>()
                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        "total" -> total = reader.nextInt()
                        "page" -> page = reader.nextInt()
                        "books" -> books = readBooks(reader)
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                return NetworkWrapper(
                    total = total,
                    page = page,
                    books = books
                )
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception()
            } finally {
                reader.close()
            }
        }

        private fun readBooks(reader: JsonReader): List<NetworkBook> = mutableListOf<NetworkBook>().apply {
            reader.beginArray()
            while (reader.hasNext()) {
                add(NetworkBook.readBook(reader))
            }
            reader.endArray()
        }
    }
}
