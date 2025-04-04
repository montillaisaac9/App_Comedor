package com.example.app_comedor.data.network.client

import android.content.Context
import com.example.app_comedor.BuildConfig
import com.example.app_comedor.data.network.response.CookieSerializable.CookieSerializable
import com.example.app_comedor.data.network.response.ResponseBase
import com.example.app_comedor.data.pref.CookiesPref
import com.example.app_comedor.data.pref.SessionPref
import com.example.app_comedor.utils.ApiResult
import com.example.app_comedor.utils.code
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonElement
import io.ktor.http.*
import io.ktor.client.request.forms.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ApiServiceImpl constructor(
    val httpClient: HttpClient,
    val context: Context
) {

    suspend inline fun <reified T> get(
        url: String,
    ): Flow<ApiResult<T>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get(urlString = BuildConfig.HOST + url){
                contentType(ContentType.Application.Any)
            }.body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }


    suspend inline fun <reified T> post(
        url: String,
        bodyJson: JsonElement,
    ): Flow<ApiResult<ResponseBase<T>?>> = flow {
        emit(ApiResult.Loading())
        try {
            val cookiesSave = CookieSerializable.deserializeCookie(CookiesPref.instanceValueFlow(context).value)
            val response = httpClient.post(urlString = BuildConfig.HOST + url) {
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
                cookiesSave?.let {
                    cookie(it.name, it.value,it.maxAge,it.expires, it.domain, it.path, it.secure, it.httpOnly, it.extensions)
                }
            }.body<ResponseBase<T>>()
            val cookies = httpClient.cookies(BuildConfig.HOST.orEmpty()).first()
            CookiesPref.setInstancesPref(context = context, CookieSerializable.serializeCookie(cookies))
            emit(
                if (response.error == null) ApiResult.Success(response)
                else {
                    if (response.error.message == "Session expired"){
                        SessionPref.setSesion(context, true)
                    }
                    ApiResult.Error(response.error.message)
                }
            )
        } catch (e: Exception) {
            emit(ApiResult.Error(code(0, context)))
        }
    }
    suspend fun postMultipart(
        url: String,
        bodyObject: Any,
        imageFile: File? = null
    ): Flow<ApiResult<ResponseBase<String>?>> = flow {
        emit(ApiResult.Loading())
        try {
            val cookiesSave = CookieSerializable.deserializeCookie(CookiesPref.instanceValueFlow(context).value)

            // Serializar el objeto a JSON
            val jsonData = Json.encodeToString(bodyObject)

            // Construir el formulario multipart
            val formData = formData {
                append("createAuthenticationDto", jsonData, Headers.build {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                })

                imageFile?.let { file ->
                    append("image", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                    })
                }
            }

            // Enviar la petición y recibir un ResponseBase<String>
            val response: ResponseBase<String> = httpClient.post(urlString = BuildConfig.HOST + url) {
                contentType(ContentType.MultiPart.FormData)
                setBody(MultiPartFormDataContent(formData))
                cookiesSave?.let {
                    cookie(it.name, it.value, it.maxAge, it.expires, it.domain, it.path, it.secure, it.httpOnly, it.extensions)
                }
            }.body()

            // Guardar cookies después de la petición
            val cookies = httpClient.cookies(BuildConfig.HOST.orEmpty()).first()
            CookiesPref.setInstancesPref(context, CookieSerializable.serializeCookie(cookies))

            emit(
                if (response.error == null) ApiResult.Success(response)
                else {
                    if (response.error.message == "Session expired") {
                        SessionPref.setSesion(context, true)
                    }
                    ApiResult.Error(response.error.message)
                }
            )
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message ?: "Error en la solicitud"))
        }
    }


}