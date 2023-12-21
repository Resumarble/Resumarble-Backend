package resumarble.reactor.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(e.errorCode.name, e.errorCode.message)
        return Mono.just(
            ResponseEntity
                .status(e.errorCode.httpStatus)
                .body(errorResponse)
        )
    }
}

data class ErrorResponse(
    val code: String,
    val message: String
)
