package kr.pe.ssun.carrot.navigation

import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.pe.ssun.carrot.ui.detail.BookDetailScreen

const val bookDetailNavigationRoute = "book_detail"

const val bookDetailIdArgs = "id"
const val bookDetailNameArgs = "name"
const val bookDetailUrlArgs = "url"

fun NavController.navigateToBookDetail(id: Int, name: String, thumbnail: String, navOptions: NavOptions? = null) {
    val encoded = Base64.encodeToString(thumbnail.toByteArray(), Base64.DEFAULT)
    this.navigate("$bookDetailNavigationRoute/$id/$name/$encoded", navOptions)
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
        route = "$bookDetailNavigationRoute/{$bookDetailIdArgs}/{$bookDetailNameArgs}/{$bookDetailUrlArgs}",
        arguments = listOf(
            navArgument(bookDetailIdArgs) {
                type = NavType.IntType
            }
        ),
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString(bookDetailNameArgs)
        val encodedUrl = backStackEntry.arguments?.getString(bookDetailUrlArgs)
        val decodedUrl = String(Base64.decode(encodedUrl, 0))
        BookDetailScreen(
            name = name,
            thumbnail = decodedUrl,
        )
    }
}