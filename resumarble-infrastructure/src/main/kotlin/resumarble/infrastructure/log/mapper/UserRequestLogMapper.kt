package resumarble.infrastructure.log.mapper

import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.infrastructure.annotation.Mapper
import resumarble.infrastructure.log.entity.UserRequestLog

@Mapper
object UserRequestLogMapper {

    fun commandToEntity(command: UserRequestLogCommand): UserRequestLog {
        return UserRequestLog(
            userId = command.userId,
            userContent = command.userContent,
            requestOutcome = command.requestOutcome,
            requestDate = command.requestDate
        )
    }
}
