package resumarble.core.domain.prediction.application.port.`in`

import resumarble.core.domain.prediction.domain.InterviewQuestion
import java.time.LocalDateTime

data class FindInterviewQuestionResponse(
    val interviewQuestionId: Long,
    val job: String,
    val category: String,
    val question: String,
    val answer: String,
    val createdDate: LocalDateTime
) {
    companion object {
        fun of(interviewQuestionList: List<InterviewQuestion>): List<FindInterviewQuestionResponse> {
            return interviewQuestionList.map {
                FindInterviewQuestionResponse(
                    it.interviewQuestionId,
                    it.job.jobTitleKr,
                    it.category.value,
                    it.question.value,
                    it.answer.value,
                    it.createdDate
                )
            }
        }
    }
}

data class QuestionAndAnswerResponse(
    val question: String,
    val answer: String
)
