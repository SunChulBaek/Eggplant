package kr.pe.ssun.carrot.ui.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

@Composable
fun CarrotImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    loading: (@Composable () -> Unit)? = null
) {
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        bitmap = async(Dispatchers.IO) {
            // TODO : 캐시되어 있는지 확인
            // 네트웍에서 불러오기
            get(url, BitmapFactory::decodeStream)?.asImageBitmap()
            // TODO : 파일로 저장
        }.await()
    }

    Box(modifier = modifier) {
        if (bitmap != null) {
            Timber.i("[sunchulbaek] cache hit!!! ($url)")
            Image(
                bitmap = bitmap!!,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
        } else {
            Timber.i("[sunchulbaek] no cache ($url)")
            loading?.let {
                loading()
            } ?: run {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

fun <R> get(
    url: String,
    mapper: (InputStream) -> R
) = (URL(url).openConnection() as? HttpURLConnection)?.let { conn ->
    mapper(conn.inputStream)
}

private fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}