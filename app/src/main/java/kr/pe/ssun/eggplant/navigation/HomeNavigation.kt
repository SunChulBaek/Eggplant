package kr.pe.ssun.eggplant.navigation

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.pe.ssun.eggplant.ui.home.HomeScreen

const val homeNavigationRoute = "home"

fun NavGraphBuilder.homeScreen(
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    popEnterTransition: EnterTransition = EnterTransition.None,
    popExitTransition: ExitTransition = ExitTransition.None,
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit,
) {
    composable(
        route = homeNavigationRoute,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition }
    ) {
        HomeScreen(navigate = navigate, showToast = showToast, onBack = onBack)
    }
}