package resumarble.core.global.error

sealed class BusinessException(
    open val errorCode: ErrorCode
) : RuntimeException()

data class PromptNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_NOT_FOUND
) : BusinessException(ErrorCode.PROMPT_NOT_FOUND)

data class UserNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.USER_NOT_FOUND
) : BusinessException(ErrorCode.USER_NOT_FOUND)

data class UnidentifiedUserException(
    override val errorCode: ErrorCode = ErrorCode.UNIDENTIFIED_USER
) : BusinessException(ErrorCode.UNIDENTIFIED_USER)

data class PromptAlreadyExistsException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_ALREADY_EXISTS
) : BusinessException(ErrorCode.PROMPT_ALREADY_EXISTS)

data class InvalidInputValueException(
    override val errorCode: ErrorCode
) : BusinessException(ErrorCode.INVALID_INPUT_VALUE)

data class CompletionFailedException(
    override val errorCode: ErrorCode = ErrorCode.REQUEST_FAILED
) : BusinessException(ErrorCode.REQUEST_FAILED)
