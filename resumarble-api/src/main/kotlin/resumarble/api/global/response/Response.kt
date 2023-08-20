package resumarble.api.global.response

import org.springframework.http.HttpStatus
import resumarble.core.global.error.ErrorCode

data class Response<T>(
    val code: Int,
    val status: HttpStatus,
    val message: String,
    var data: T? = null
) {

    companion object {

        @JvmStatic
        fun <T> of(httpStatus: HttpStatus, message: String, data: T): Response<T> {
            return Response(httpStatus.value(), httpStatus, message, data)
        }

        @JvmStatic
        fun <T> of(httpStatus: HttpStatus, data: T): Response<T> {
            return of(httpStatus, httpStatus.name, data)
        }

        @JvmStatic
        fun <T> ok(data: T): Response<T> {
            return of(HttpStatus.OK, HttpStatus.OK.name, data)
        }

        @JvmStatic
        fun ok(): Response<Unit> {
            return of(HttpStatus.OK, HttpStatus.OK.name, Unit)
        }

        @JvmStatic
        fun <T> fail(errorCode: ErrorCode): Response<T?> {
            return of(errorCode.status, errorCode.message, null)
        }
    }
}
