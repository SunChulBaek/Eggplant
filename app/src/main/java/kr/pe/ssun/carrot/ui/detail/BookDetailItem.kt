package kr.pe.ssun.carrot.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.pe.ssun.carrot.data.model.BookDetail
import kr.pe.ssun.carrot.ui.common.CarrotImage

sealed interface BookDetailItem {

    @Composable
    fun ItemContent()

    data class BookDetailImage(
        val image: String
    ) : BookDetailItem {
        @Composable
        override fun ItemContent() = CarrotImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 1f),
            url = image,
            contentDescription = null
        )
    }

    data class BookDetailTitle(
        val title: String
    ) : BookDetailItem {
        @Composable
        override fun ItemContent() = Text(
            text = title,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.titleMedium
        )
    }

    data class BookDetailSubtitle(
        val subtitle: String
    ) : BookDetailItem {
        @Composable
        override fun ItemContent() = Text(
            text = subtitle,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.titleSmall
        )
    }

    data class BookDetailInfo(
        val book: BookDetail
    ) : BookDetailItem {
        @Composable
        override fun ItemContent() = Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            KeyValueView(key = "Price", value = book.price, color = Color(0xFF4ca066).copy(0.3f))
            KeyValueView(key = "Rating", value = book.rating)
            KeyValueView(key = "Author", value = book.authors, color = Color(0xFF4ca066).copy(0.3f))
            KeyValueView(key = "Publisher", value = book.publisher)
            KeyValueView(key = "Published", value = book.year, color = Color(0xFF4ca066).copy(0.3f))
            KeyValueView(key = "Pages", value = book.pages)
            KeyValueView(key = "ISBN-10", value = book.isbn10, color = Color(0xFF4ca066).copy(0.3f))
            KeyValueView(key = "ISBN-13", value = book.isbn13)
        }
    }

    data class BookDetailDesc(
        val desc: String?
    ) : BookDetailItem {
        @Composable
        override fun ItemContent() = Text(
            text = desc ?: "",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private const val keyWeight = 0.3f

@Composable
private fun KeyValueView(
    key: String,
    value: String?,
    color: Color = Color.Transparent
) = Row(modifier = Modifier.background(color)) {
    Text(text = key, modifier = Modifier.weight(keyWeight))
    Text(text = value ?: "", modifier = Modifier.weight(1 - keyWeight))
}

@Composable
private fun Td(
    modifier: Modifier = Modifier,
    text: String
) = Box(modifier = modifier) {
    Text(text = text)
}