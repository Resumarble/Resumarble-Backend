package resumarble.infrastructure.log.infrastructure

import org.springframework.stereotype.Repository
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.infrastructure.UserRequestLogRepository
import resumarble.infrastructure.log.entity.UserRequestLogJpaRepository
import resumarble.infrastructure.log.mapper.UserRequestLogMapper

@Repository
class UserRequestLogRepositoryImpl(
    private val logRepository: UserRequestLogJpaRepository,
    private val logMapper: UserRequestLogMapper
) : UserRequestLogRepository {

    override fun saveUserRequestLog(command: UserRequestLogCommand) {
        val entity = logMapper.commandToEntity(command)
        logRepository.save(entity)
    }
}
