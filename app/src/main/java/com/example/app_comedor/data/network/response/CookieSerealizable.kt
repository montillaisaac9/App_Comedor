package com.example.app_comedor.data.network.response

import kotlinx.serialization.Serializable
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.util.date.GMTDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
enum class SameSite {
    STRICT,
    LAX,
    NONE
}
@Serializable
data class CookieSerializable(
    val name: String,
    val value: String,
    val encoding: CookieEncoding = CookieEncoding.URI_ENCODING,
    val maxAge: Int = 0,
    val expires: Long? = null,
    val domain: String? = null,
    val path: String? = null,
    val secure: Boolean = false,
    val httpOnly: Boolean = false,
    val sameSite: SameSite? = null // Nuevo campo agregado
) {

    @Serializable
    data class CookieSerializable(
        val name: String,
        val value: String,
        val encoding: CookieEncoding = CookieEncoding.URI_ENCODING,
        val maxAge: Int = 0,
        val expires: Long? = null,
        val domain: String? = null,
        val path: String? = null,
        val secure: Boolean = false,
        val httpOnly: Boolean = false,
        val extensions: Map<String, String?> = emptyMap() // Incluye extensions para manejar sameSite
    ) {
        companion object {
            fun serializeCookie(cookie: Cookie): String {
                val nowTimestamp = GMTDate().timestamp + (1000 * 20 * 60) // Ejemplo: 20 minutos
                val temp = CookieSerializable(
                    name = cookie.name,
                    value = cookie.value,
                    encoding = cookie.encoding,
                    maxAge = cookie.maxAge,
                    expires = cookie.expires?.timestamp ?: nowTimestamp,
                    domain = cookie.domain,
                    path = cookie.path,
                    secure = cookie.secure,
                    httpOnly = cookie.httpOnly,
                    extensions = cookie.extensions // Copia extensions, incluyendo sameSite si está presente
                )
                return Json.encodeToString(temp)
            }

            fun deserializeCookie(cookieString: String?): Cookie? {
                if (cookieString == null) return null
                val temp = Json.decodeFromString<CookieSerializable>(cookieString)
                return Cookie(
                    name = temp.name,
                    value = temp.value,
                    encoding = temp.encoding,
                    maxAge = temp.maxAge,
                    expires = temp.expires?.let { GMTDate(it) },
                    domain = temp.domain,
                    path = temp.path,
                    secure = temp.secure,
                    httpOnly = temp.httpOnly,
                    extensions = temp.extensions // Restaura extensions, incluyendo sameSite si estaba
                )
            }
        }
    }
}