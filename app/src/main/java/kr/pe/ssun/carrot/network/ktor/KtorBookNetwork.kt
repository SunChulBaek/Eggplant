package kr.pe.ssun.carrot.network.ktor

import kr.pe.ssun.carrot.network.BookNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import kr.pe.ssun.carrot.network.model.NetworkWrapper
import timber.log.Timber
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.X509TrustManager

@Singleton
class KtorBookNetwork @Inject constructor() : BookNetworkDataSource {

    private val client = HttpClient(CIO) {
        // TODO : remove this
        engine {
            https {
                trustManager = object: X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) { }

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) { }

                    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                }
            }
        }
        install(ContentNegotiation) {
            gson()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d("ktor : $message")
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url("https://api.itbook.store/1.0/")
        }
    }

    override suspend fun searchBook(
        query: String
    ): NetworkWrapper = client.get("search/$query").body()
}