package kr.pe.ssun.eggplant.ui.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

@Composable
fun EggplantImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    loading: (@Composable () -> Unit)? = null
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // 캐시된 이미지 사용. 없으면 네트워크에서 가져와서 파일로 저장
    LaunchedEffect(url) {
        bitmap = async(Dispatchers.IO) {
            cache(context, url) ?: get(url, BitmapFactory::decodeStream)?.apply {
                save(context, this, url)
            }
        }.await()?.asImageBitmap()
    }

    Box(modifier = modifier) {
        if (bitmap != null) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = bitmap!!,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
        } else {
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

/**
 * 네트워크에서 가져옴
 */
private fun <R> get(
    url: String,
    mapper: (InputStream) -> R
) = (URL(url).openConnection() as? HttpURLConnection)?.let { conn ->
    mapper(conn.inputStream)
}

/**
 * 해당 url의 캐시를 가져옴
 */
private fun cache(context: Context, url: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val file = File("${context.cacheDir.absolutePath}/${md5(url)}")
        if (!file.exists()) {
            Timber.e("[sunchulbaek] Not Cached!!! url = $url")
            return null
        }
        val options = BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = BitmapFactory.decodeStream(FileInputStream(file), null, options)
        Timber.d("[sunchulbaek] Cache Hit!!! url = $url")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}

/**
 * 해당 url에 대한 캐시 저장
 */
private fun save(context: Context, bitmap: Bitmap, url: String) {
    val file = File("${context.cacheDir.absolutePath}/${md5(url)}")
    var out: OutputStream? = null
    try {
        file.createNewFile()
        out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            out?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

private fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}