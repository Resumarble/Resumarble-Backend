package resumarble.core.global.error

data class UserNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.USER_NOT_FOUND
) : BusinessException(ErrorCode.USER_NOT_FOUND)

data class DuplicateUserException(
    val email: String,
    override val errorCode: ErrorCode = ErrorCode.DUPLICATE_USER
) : BusinessException(ErrorCode.DUPLICATE_USER)

data class UnidentifiedUserException(
    override val errorCode: ErrorCode = ErrorCode.UNIDENTIFIED_USER
) : BusinessException(ErrorCode.UNIDENTIFIED_USER)
