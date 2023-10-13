package resumarble.core.domain.log.application

import resumarble.core.domain.log.constraints.RequestOutcome
import java.time.LocalDateTime

data class UserRequestLogCommand(
    val userId: Long,

    val userContent: String,

    val requestOutcome: RequestOutcome,

    val requestDate: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        @JvmStatic
        fun from(userId: Long, content: String, requestOutcome: RequestOutcome): UserRequestLogCommand {
            return UserRequestLogCommand(userId, content, requestOutcome)
        }
    }
}
