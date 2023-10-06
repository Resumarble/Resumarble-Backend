package resumarble.core.domain.log.application

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository
import resumarble.core.global.util.loggingErrorMarking

@Service
class UserRequestLogService(
    private val userRequestLogRepository: UserRequestLogRepository
) {
    val scope = CoroutineScope(Dispatchers.IO)
    fun saveUserRequestLog(command: UserRequestLogCommand) {
        scope.launch(handler) {
            userRequestLogRepository.saveUserRequestLog(command)
        }
    }

    val handler = CoroutineExceptionHandler { _, throwable ->
        loggingErrorMarking {
            SAVE_USER_LOG_ERROR_MESSAGE + throwable.message
        }
    }

    companion object {
        private const val SAVE_USER_LOG_ERROR_MESSAGE = "유저 로그 저장에 실패했습니다."
    }
}
