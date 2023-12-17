package resumarble.reactor.domain.interview.application

import resumarble.reactor.domain.interview.domain.Category
import resumarble.reactor.domain.interview.domain.InterviewQuestion
import java.time.LocalDateTime

data class MyPageInterviewQuestionResponse(
    val interviewQuestions: List<FindInterviewQuestionResponse>,
    val hasNextPage: Boolean
)
data class FindInterviewQuestionResponse(
    val interviewQuestionId: Long,
    val userId: Long,
    val questionAndAnswer: QuestionAndAnswer,
    val job: String,
    val category: Category,
    val createdDate: LocalDateTime
) {

    companion object {
        @JvmStatic
        fun from(interviewQuestion: InterviewQuestion): FindInterviewQuestionResponse {
            return FindInterviewQuestionResponse(
                interviewQuestionId = interviewQuestion.id,
                userId = interviewQuestion.userId,
                questionAndAnswer = QuestionAndAnswer(
                    question = interviewQuestion.question,
                    answer = interviewQuestion.answer
                ),
                job = interviewQuestion.job,
                category = interviewQuestion.category,
                createdDate = interviewQuestion.createdAt
            )
        }
    }
}

data class QuestionAndAnswer(
    val question: String,
    val answer: String
)
