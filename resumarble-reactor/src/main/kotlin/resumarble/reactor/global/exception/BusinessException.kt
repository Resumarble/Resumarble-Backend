package resumarble.reactor.global.exception

data class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
