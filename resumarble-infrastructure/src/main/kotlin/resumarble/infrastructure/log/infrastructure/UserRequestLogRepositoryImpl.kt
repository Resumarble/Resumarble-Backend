package resumarble.infrastructure.log.infrastructure

import org.springframework.stereotype.Repository
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository

@Repository
class UserRequestLogRepositoryImpl(
    private val logRepository: UserRequestLogRepository
) : UserRequestLogRepository
