package resumarble.core.domain.feedback.application

data class FeedbackResponse(
    val answer: String
)

data class FeedbackResponseList(
    val feedbacks: List<FeedbackResponse>
)
