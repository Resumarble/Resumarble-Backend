package resumarble.api.global.advice

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import resumarble.api.global.response.Response
import resumarble.core.domain.log.application.UserRequestLogWriter
import resumarble.core.global.error.BusinessException
import resumarble.core.global.error.CompletionFailedException
import resumarble.core.global.error.DuplicateUserException
import resumarble.core.global.error.ErrorCode
import resumarble.core.global.error.PromptNotFoundException
import resumarble.core.global.error.TokenVerifyException
import resumarble.core.global.error.UnidentifiedUserException
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.util.logger

@RestControllerAdvice
class GlobalExceptionHandler(
    private val userRequestLogWriter: UserRequestLogWriter
) {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(PromptNotFoundException::class)
    fun handlePromptNotFoundException(e: PromptNotFoundException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(CompletionFailedException::class)
    fun handleCompletionFailedException(e: CompletionFailedException): Response<Any?> {
        userRequestLogWriter.saveUserRequestLog(e.toFailedLogCommand())
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(DuplicateUserException::class)
    fun handleDuplicateUserException(e: DuplicateUserException): Response<Any> {
        return Response.fail(e.errorCode, e.account)
    }

    @ExceptionHandler(UnidentifiedUserException::class)
    fun handleUnidentifiedUserException(e: UnidentifiedUserException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(TokenVerifyException::class)
    fun handleTokenVerifyException(e: TokenVerifyException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Response<Any?> {
        logger.error(e.message, e)
        return Response.fail(ErrorCode.INTERNAL_SERVER_ERROR)
    }
}
