package resumarble.core.global.error

data class RequestPerSecondException(
    override val errorCode: ErrorCode = ErrorCode.REQUEST_PER_SECOND
) : BusinessException(ErrorCode.REQUEST_PER_SECOND)
