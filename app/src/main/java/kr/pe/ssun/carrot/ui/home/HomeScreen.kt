package kr.pe.ssun.carrot.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kr.pe.ssun.carrot.R

// 백 키 관련
const val BACK_PRESS_DELAY_TIME: Long = 2000
var backKeyPressedTime: Long = 0
var toast: Toast? = null

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val books = (uiState as? HomeUiState.Success)?.books ?: listOf()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    // 백키 2회에 종료 처리
    BackCloseHandler(navController, showToast, onBack)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeContent(
                books = books,
                isLoading = isLoading,
                search = viewModel::search,
                loadMore = viewModel::loadMore,
                navigate = navigate
            )
        }
    }
}

/**
 * 백키 2회에 종료 처리
 */
@Composable
fun BackCloseHandler(
    navController: NavHostController,
    showToast: (String) -> Toast,
    onBack: () -> Unit
) = BackHandler {
    if (!navController.popBackStack()) {
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_PRESS_DELAY_TIME) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = showToast("\'뒤로\' 버튼 한번 더 누르시면 종료됩니다.")
            return@BackHandler
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_PRESS_DELAY_TIME) {
            toast?.cancel()
            onBack.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() = TopAppBar(
    title = { Text(stringResource(R.string.app_name))},
    navigationIcon = {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Menu, "Menu")
        }
    }
)