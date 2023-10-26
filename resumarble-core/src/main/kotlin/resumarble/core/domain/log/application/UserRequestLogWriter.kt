package resumarble.core.domain.log.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository
import resumarble.core.global.util.loggingErrorMarking

@Service
class UserRequestLogWriter(
    private val userRequestLogRepository: UserRequestLogRepository
) {
    @Transactional
    fun save(command: UserRequestLogCommand) {
        loggingErrorMarking {
            userRequestLogRepository.saveUserRequestLog(command)
        }
    }

    companion object {
        private const val SAVE_USER_LOG_ERROR_MESSAGE = "유저 로그 저장에 실패했습니다."
    }
}
