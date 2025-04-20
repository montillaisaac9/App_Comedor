package com.example.app_comedor.data.network.client

import android.content.Context
import com.example.app_comedor.BuildConfig
import com.example.app_comedor.data.network.models.auth.CreateUser
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
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.File

class ApiServiceImpl constructor(
    val httpClient: HttpClient,
    val context: Context
) {

    val HOST =  "http://192.168.1.117:3000/"
    
    suspend inline fun <reified T> get(
        url: String,
    ): Flow<ApiResult<ResponseBase<T>?>> = flow {
        emit(ApiResult.Loading())
        try {
            val cookiesSave = CookieSerializable.deserializeCookie(CookiesPref.instanceValueFlow(context).value)
            val response = httpClient.get(urlString = HOST + url) {
                contentType(ContentType.Application.Any)
                cookiesSave?.let {
                    cookie(it.name, it.value, it.maxAge, it.expires, it.domain, it.path, it.secure, it.httpOnly, it.extensions)
                }
            }.body<ResponseBase<T>>()

            val cookies = httpClient.cookies(HOST.orEmpty()).firstOrNull()
            cookies?.let {
                CookiesPref.setInstancesPref(context = context, CookieSerializable.serializeCookie(it))
            }
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
            Timber.e("EXEPTION: ${e}")
            emit(ApiResult.Error(code(0, context)))
        }
    }



    suspend inline fun <reified T> post(
        url: String,
        bodyJson: JsonElement,
    ): Flow<ApiResult<ResponseBase<T>?>> = flow {
        emit(ApiResult.Loading())
        try {
            val cookiesSave = CookieSerializable.deserializeCookie(CookiesPref.instanceValueFlow(context).value)
            val response = httpClient.post(urlString = HOST + url) {
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
                cookiesSave?.let {
                    cookie(it.name, it.value,it.maxAge,it.expires, it.domain, it.path, it.secure, it.httpOnly, it.extensions)
                }
            }.body<ResponseBase<T>>()
            val cookies = httpClient.cookies(HOST.orEmpty()).firstOrNull()
            cookies?.let {
                CookiesPref.setInstancesPref(context = context, CookieSerializable.serializeCookie(it))
            }
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

    @OptIn(InternalAPI::class)
    suspend fun postMultipart(
        url: String,
        user: CreateUser,
        imageFile: File? = null
    ): Flow<ApiResult<ResponseBase<String>?>> = flow {
        emit(ApiResult.Loading())
        try {
            val cookiesSave = CookieSerializable.deserializeCookie(CookiesPref.instanceValueFlow(context).value)

            val formData = formData {
                // Add each field from the user object individually
                append("email", user.email)
                append("identification", user.identification)
                append("name", user.name)
                append("password", user.password)
                append("securityWord", user.securityWord)
                append("role", user.role.toString())

                // Add career IDs as individual form fields with the same name
                user.careerIds.forEach { careerId ->
                    append("careerIds", careerId.toString())
                }

                // Add position if not null
                user.position?.let { append("position", it) }

                // Add isActive if not null
                user.isActive?.let { append("isActive", it.toString()) }

                // Add image file if provided
                imageFile?.let { file ->
                    append("image", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                    })
                }
            }

            val response: ResponseBase<String> = httpClient.post(urlString = HOST + url) {
                contentType(ContentType.MultiPart.FormData)
                setBody(MultiPartFormDataContent(formData))
                cookiesSave?.let {
                    cookie(it.name, it.value, it.maxAge, it.expires, it.domain, it.path, it.secure, it.httpOnly, it.extensions)
                }
            }.body()



            val cookies = httpClient.cookies(HOST.orEmpty()).firstOrNull()
            cookies?.let {
                CookiesPref.setInstancesPref(context = context, CookieSerializable.serializeCookie(it))
            }


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
            Timber.e("ERROR ${e}")
            emit(ApiResult.Error(e.message ?: "Error en la solicitud"))
        }
    }


}