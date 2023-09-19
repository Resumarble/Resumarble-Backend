package resumarble.api.resume

import resumarble.core.domain.resume.facade.InterviewQuestionCommand

data class InterviewQuestionRequest(
    val job: String,
    val career: String,
    val resumeInfo: ResumeInfo
) {
    fun toCommand(userId: Long) = InterviewQuestionCommand(
        userId = userId,
        job = job,
        career = career,
        category = resumeInfo.category,
        content = resumeInfo.content
    )
}

data class ResumeInfo(
    val category: String,
    val content: String
)
