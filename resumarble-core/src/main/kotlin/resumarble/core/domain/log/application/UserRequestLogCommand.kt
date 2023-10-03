package resumarble.core.domain.log.application

import java.time.LocalDateTime

data class UserRequestLogCommand(
    private val userId: Long,

    private val userContent: String,

    private val requestOutcome: String,

    private val requestDate: LocalDateTime = LocalDateTime.now()
)
