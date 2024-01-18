package kr.pe.ssun.carrot.navigation

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.pe.ssun.carrot.data.model.Book
import kr.pe.ssun.carrot.ui.detail.BookDetailScreen

const val bookDetailNavigationRoute = "book_detail"

const val bookDetailTitleArgs = "title"
const val bookDetailSubtitleArgs = "subtitle"
const val bookDetailIsbnArgs = "isbn13"
const val bookDetailPriceArgs = "price"
const val bookDetailImageArgs = "image"

internal class BookDetailArgs(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        title = Uri.decode(checkNotNull(savedStateHandle[bookDetailTitleArgs])),
        subtitle = Uri.decode(checkNotNull(savedStateHandle[bookDetailSubtitleArgs])),
        isbn13 = Uri.decode(checkNotNull(savedStateHandle[bookDetailIsbnArgs])),
        price = Uri.decode(checkNotNull(savedStateHandle[bookDetailPriceArgs])),
        image = Uri.decode(checkNotNull(savedStateHandle[bookDetailImageArgs]))
    )
}

fun NavController.navigateToBookDetail(book: Book, navOptions: NavOptions? = null) {
    val isbn13 = Uri.encode(book.isbn13)
    val encodedTitle = Uri.encode(book.title)
    val encodedSubtitle = Uri.encode(book.subtitle)
    val encodedPrice = Uri.encode(book.price)
    val encodedImage = Uri.encode(book.image)
    this.navigate("$bookDetailNavigationRoute/$encodedTitle/$encodedSubtitle/$isbn13/$encodedPrice/$encodedImage", navOptions)
}

fun NavGraphBuilder.bookDetailScreen(
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    popEnterTransition: EnterTransition = EnterTransition.None,
    popExitTransition: ExitTransition = ExitTransition.None,
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit,
) {
    composable(
        route = "$bookDetailNavigationRoute/{$bookDetailTitleArgs}/{$bookDetailSubtitleArgs}/{$bookDetailIsbnArgs}/{$bookDetailPriceArgs}/{$bookDetailImageArgs}",
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) {
        BookDetailScreen(onBack = onBack)
    }
}