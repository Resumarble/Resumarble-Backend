package resumarble.api.resume

import resumarble.core.domain.resume.facade.InterviewQuestionCommand

data class InterviewQuestionRequest(
    val job: String,
    val career: String,
    val resumeInfo: ResumeInfo
) {
    fun toCommand() = InterviewQuestionCommand(
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
