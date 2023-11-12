package resumarble.core.domain.log.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository
import resumarble.core.global.util.loggingErrorMarking

@Service
class UserRequestLogWriter(
    private val userRequestLogRepository: UserRequestLogRepository
) {
    fun save(command: UserRequestLogCommand) {
        CoroutineScope(Dispatchers.IO).launch {
            loggingErrorMarking {
                userRequestLogRepository.saveUserRequestLog(command)
            }
        }
    }
}
