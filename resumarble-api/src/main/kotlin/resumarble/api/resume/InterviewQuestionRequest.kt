package resumarble.api.resume

import resumarble.core.domain.resume.facade.InterviewQuestionCommand

private const val MAXIMUM_REQUEST_COUNT = 3

data class InterviewQuestionRequest(
    val job: String,
    val career: String,
    val resumeInfoList: List<ResumeInfo>
) {
    fun toCommandList(userId: Long): List<InterviewQuestionCommand> {
        require(resumeInfoList.size <= MAXIMUM_REQUEST_COUNT) {
            "요청은 최대 3건까지만 가능합니다."
        }
        return resumeInfoList.map { resumeInfo ->
            InterviewQuestionCommand(
                userId = userId,
                job = job,
                career = career,
                category = resumeInfo.category,
                content = resumeInfo.content
            )
        }
    }
}
