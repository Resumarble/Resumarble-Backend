package resumarble.reactor.domain.interview.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import resumarble.reactor.domain.interview.application.InterviewQuestionCommand

private const val MAXIMUM_REQUEST_COUNT = 3

data class InterviewQuestionRequest(
    val job: String,
    val career: String,
    val resumeInfoList: List<ResumeInfo>
) {
    fun toCommandList(userId: Long): Flow<InterviewQuestionCommand> {
        require(resumeInfoList.size <= MAXIMUM_REQUEST_COUNT) {
            "요청은 최대 3건까지만 가능합니다."
        }
        return resumeInfoList.asFlow().map { resumeInfo ->
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

data class ResumeInfo(
    val category: String,
    val content: String
)
