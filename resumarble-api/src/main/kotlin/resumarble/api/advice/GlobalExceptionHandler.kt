package resumarble.api.advice

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import resumarble.api.response.Response
import resumarble.core.global.error.BusinessException
import resumarble.core.global.error.CompletionFailedException
import resumarble.core.global.error.DuplicateUserException
import resumarble.core.global.error.ErrorCode
import resumarble.core.global.error.PromptNotFoundException
import resumarble.core.global.error.UserNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

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
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): Response<Any?> {
        return Response.fail(e.errorCode)
    }

    @ExceptionHandler(DuplicateUserException::class)
    fun handleDuplicateUserException(e: DuplicateUserException): Response<Any> {
        return Response.fail(e.errorCode, e.email)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Response<Any?> {
        return Response.fail(ErrorCode.INTERNAL_SERVER_ERROR)
    }
}
