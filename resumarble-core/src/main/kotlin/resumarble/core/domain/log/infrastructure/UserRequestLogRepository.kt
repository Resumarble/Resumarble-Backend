package resumarble.core.domain.log.infrastructure

import resumarble.core.domain.log.application.UserRequestLogCommand

interface UserRequestLogRepository {

    fun saveUserRequestLog(command: UserRequestLogCommand)
}
