package resumarble.api.global.jwt

import com.nimbusds.jose.shaded.gson.JsonObject
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import java.io.IOException

enum class JwtException(
    private val httpStatus: HttpStatus,
    private val detail: String
) {
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "기간 만료"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰"),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰"),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "빈 토큰"),
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "토큰 에러");

    fun getHttpStatus(): HttpStatus {
        return httpStatus
    }

    fun getDetail(): String {
        return detail
    }
}

fun setResponse(response: HttpServletResponse, jwtException: JwtException) {
    response.contentType = "application/json;charset=UTF-8"

    val responseJson = JsonObject().apply {
        addProperty("status", jwtException.getHttpStatus().value())
        addProperty("error", jwtException.getHttpStatus().name)
        addProperty("code", jwtException.name)
        addProperty("message", jwtException.getDetail())
    }

    try {
        response.status = 401
        response.writer.print(responseJson)
    } catch (_: IOException) {
    }
}
