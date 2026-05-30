package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.data.implementation.remote.service.PokeService
import id.tiooooo.pokedata.data.implementation.remote.service.PokeServiceImpl
import id.tiooooo.pokedata.utils.AppConstants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.dsl.module

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        HttpClient(OkHttp) {
            engine {
                preconfigured = get<OkHttpClient>()
            }

            defaultRequest {
                url(BASE_URL)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor: $message")
                    }
                }
                level = LogLevel.INFO
            }
        }
    }

    single<PokeService> {
        PokeServiceImpl(get())
    }
}
