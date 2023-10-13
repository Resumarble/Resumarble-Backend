package resumarble.core.global.error

data class TokenVerifyException(
    override val errorCode: ErrorCode = ErrorCode.TOKEN_VERIFY_FAIL
) : BusinessException(errorCode)

data class RefreshTokenNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND
) : BusinessException(errorCode)
