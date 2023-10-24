package resumarble.core.domain.gpt.application

import resumarble.core.domain.log.constraints.RequestOutcome

data class UserRequestLogEvent(
    val userId: Long,

    val userContent: String,

    val requestOutcome: RequestOutcome
) {
    companion object {
        @JvmStatic
        fun from(userId: Long, content: String, requestOutcome: RequestOutcome): UserRequestLogEvent {
            return UserRequestLogEvent(userId, content, requestOutcome)
        }
    }
}
