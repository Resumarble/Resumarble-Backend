package resumarble.api.feedback

import resumarble.core.domain.feedback.application.FeedbackCommand

data class FeedbackRequest(
    val question: String,
    val answer: String
) {
    fun toCommand(userId: Long): FeedbackCommand {
        return FeedbackCommand(
            userId = userId,
            question = question,
            answer = answer
        )
    }
}
