package resumarble.core.global.error

sealed class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException()

data class InvalidInputValueException(
    val value: String,
    val reason: String
) : BusinessException(ErrorCode.INVALID_INPUT_VALUE)
