package resumarble.infrastructure.log.application

import org.springframework.context.event.EventListener
import resumarble.core.domain.gpt.application.UserRequestLogEvent
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.application.UserRequestLogWriter
import resumarble.core.global.annotation.Listener

@Listener
class UserRequestLogListener(
    private val userRequestLogWriter: UserRequestLogWriter
) {

    @EventListener
    fun handle(event: UserRequestLogEvent) {
        val command = UserRequestLogCommand.from(
            userId = event.userId,
            content = event.userContent,
            requestOutcome = event.requestOutcome
        )
        userRequestLogWriter.saveUserRequestLog(command)
    }
}
