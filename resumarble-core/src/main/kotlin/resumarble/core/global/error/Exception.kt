package resumarble.core.global.error

import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.constraints.RequestOutcome

sealed class BusinessException(
    open val errorCode: ErrorCode
) : RuntimeException()

data class PredictionNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PREDICTION_NOT_FOUND
) : BusinessException(ErrorCode.PREDICTION_NOT_FOUND)

data class PromptNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_NOT_FOUND
) : BusinessException(ErrorCode.PROMPT_NOT_FOUND)

data class PromptAlreadyExistsException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_ALREADY_EXISTS
) : BusinessException(ErrorCode.PROMPT_ALREADY_EXISTS)

data class UnIdentifiedException(
    override val errorCode: ErrorCode = ErrorCode.UNIDENTIFIED_USER
) : BusinessException(ErrorCode.UNIDENTIFIED_USER)

data class CompletionFailedException(
    override val errorCode: ErrorCode = ErrorCode.REQUEST_FAILED,
    val userId: Long,
    val userContent: String
) : BusinessException(ErrorCode.REQUEST_FAILED) {
    fun toFailedLogCommand() = UserRequestLogCommand(userId, userContent, RequestOutcome.FAILED)
}
