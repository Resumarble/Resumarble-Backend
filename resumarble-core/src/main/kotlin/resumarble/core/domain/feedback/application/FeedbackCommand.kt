package resumarble.core.domain.feedback.application

data class FeedbackCommand(
    val userId: Long,
    val question: String,
    val answer: String
)
