package com.example.app_comedor.di

import android.content.Context
import com.example.app_comedor.data.network.client.ApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber


@OptIn(ExperimentalSerializationApi::class)
val ApiModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = true
        }
        HttpClient(Android) {
            engine {
                connectTimeout = 60_000
                socketTimeout = 60_000
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.v("Logger Ktor => $message")
                    }
                }
            }
            install(DefaultRequest){
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }
            install(HttpCookies)
            install(ContentNegotiation) {
                json(json)
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Timber.v("HTTP status: =>${response.status.value}")
                }
            }
        }
    }
    single {
        ApiServiceImpl(get<HttpClient>(), get<Context>())
    }
}