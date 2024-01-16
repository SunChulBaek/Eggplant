package kr.pe.ssun.carrot.navigation

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.pe.ssun.carrot.ui.detail.BookDetailScreen

const val bookDetailNavigationRoute = "book_detail"

const val bookDetailIsbnArgs = "isbn13"
const val bookDetailTitleArgs = "title"
const val bookDetailImageArgs = "image"

internal class BookDetailArgs(val isbn13: String, val title: String, val image: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        isbn13 = Uri.decode(checkNotNull(savedStateHandle[bookDetailIsbnArgs])),
        title = Uri.decode(checkNotNull(savedStateHandle[bookDetailTitleArgs])),
        image = Uri.decode(checkNotNull(savedStateHandle[bookDetailImageArgs]))
    )
}

fun NavController.navigateToBookDetail(isbn13: String, title: String, image: String, navOptions: NavOptions? = null) {
    val encodedTitle = Base64.encodeToString(title.toByteArray(), Base64.DEFAULT)
    val encodedImage = Base64.encodeToString(image.toByteArray(), Base64.DEFAULT)
    this.navigate("$bookDetailNavigationRoute/$isbn13/$encodedTitle/$encodedImage", navOptions)
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
        route = "$bookDetailNavigationRoute/{$bookDetailIsbnArgs}/{$bookDetailTitleArgs}/{$bookDetailImageArgs}",
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) { backStackEntry ->
        val encodedTitle = backStackEntry.arguments?.getString(bookDetailTitleArgs)
        val decodedTitle = String(Base64.decode(encodedTitle, 0))
        val encodedImage = backStackEntry.arguments?.getString(bookDetailImageArgs)
        val decodedImage = String(Base64.decode(encodedImage, 0))
        BookDetailScreen(
            title = decodedTitle,
            image = decodedImage,
        )
    }
}