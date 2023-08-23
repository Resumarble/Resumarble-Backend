package resumarble.core.domain.resume.facade

data class InterviewQuestionCommand(
    val job: String,
    val career: String,
    val category: String,
    val content: String
)

data class InterviewQuestionResponse(
    val interviews: List<InterviewQuestion>
)

data class InterviewQuestion(
    val question: String,
    val bestAnswer: String
)
