package resumarble.core.domain.log.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository

@Service
@Transactional
class UserRequestLogService(
    private val userRequestLogRepository: UserRequestLogRepository
) {
    fun saveUserRequestLog(command: UserRequestLogCommand) {
        userRequestLogRepository.saveUserRequestLog(command)
    }
}
