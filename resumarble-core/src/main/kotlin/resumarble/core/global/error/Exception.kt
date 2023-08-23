package resumarble.core.global.error

sealed class BusinessException(
    open val errorCode: ErrorCode
) : RuntimeException()

data class PromptNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_NOT_FOUND
) : BusinessException(ErrorCode.PROMPT_NOT_FOUND)

data class PromptAlreadyExistsException(
    override val errorCode: ErrorCode = ErrorCode.PROMPT_ALREADY_EXISTS
) : BusinessException(ErrorCode.PROMPT_ALREADY_EXISTS)

data class InvalidInputValueException(
    override val errorCode: ErrorCode
) : BusinessException(ErrorCode.INVALID_INPUT_VALUE)
