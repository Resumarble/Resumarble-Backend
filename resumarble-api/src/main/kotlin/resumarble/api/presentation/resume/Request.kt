package resumarble.api.presentation.resume

data class InterviewQuestionRequest(
    val job: String,
    val career: String,
    val resumeInfo: ResumeInfo
)

data class ResumeInfo(
    val category: String,
    val content: String
)
